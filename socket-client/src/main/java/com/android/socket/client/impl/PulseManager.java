package com.android.socket.client.impl;

import com.android.socket.client.core.iocore.interfaces.IPulseSendable;
import com.android.socket.client.impl.exceptions.DogDeadException;
import com.android.socket.client.sdk.client.WhSocketOptions;
import com.android.socket.client.sdk.client.bean.IPulse;
import com.android.socket.client.sdk.client.connection.IConnectionManager;
import com.android.socket.client.common.interfaces.basic.AbsLoopThread;

import java.util.concurrent.atomic.AtomicInteger;

public class PulseManager implements IPulse {

    private volatile IConnectionManager mManager;

    private IPulseSendable mSendable;

    private volatile WhSocketOptions mOkOptions;

    private volatile long mCurrentFrequency;

    private volatile WhSocketOptions.IOThreadMode mCurrentThreadMode;

    private volatile boolean isDead = false;

    private volatile AtomicInteger mLoseTimes = new AtomicInteger(-1);

    private PulseThread mPulseThread = new PulseThread();

    PulseManager(IConnectionManager manager, WhSocketOptions okOptions) {
        mManager = manager;
        mOkOptions = okOptions;
        mCurrentThreadMode = mOkOptions.getIOThreadMode();
    }

    public synchronized IPulse setPulseSendable(IPulseSendable sendable) {
        if (sendable != null) {
            mSendable = sendable;
        }
        return this;
    }

    public IPulseSendable getPulseSendable() {
        return mSendable;
    }

    @Override
    public synchronized void pulse() {
        privateDead();
        updateFrequency();
        if (mCurrentThreadMode != WhSocketOptions.IOThreadMode.SIMPLEX) {
            if (mPulseThread.isShutdown()) {
                mPulseThread.start();
            }
        }
    }

    @Override
    public synchronized void trigger() {
        if (isDead) {
            return;
        }
        if (mCurrentThreadMode != WhSocketOptions.IOThreadMode.SIMPLEX && mManager != null && mSendable != null) {
            mManager.send(mSendable);
        }
    }

    public synchronized void dead() {
        mLoseTimes.set(0);
        isDead = true;
        privateDead();
    }

    private synchronized void updateFrequency() {
        if (mCurrentThreadMode != WhSocketOptions.IOThreadMode.SIMPLEX) {
            mCurrentFrequency = mOkOptions.getPulseFrequency();
            mCurrentFrequency = mCurrentFrequency < 1000 ? 1000 : mCurrentFrequency;//间隔最小为一秒
        } else {
            privateDead();
        }
    }

    @Override
    public synchronized void feed() {
        mLoseTimes.set(-1);
    }

    private void privateDead() {
        if (mPulseThread != null) {
            mPulseThread.shutdown();
        }
    }

    public int getLoseTimes() {
        return mLoseTimes.get();
    }

    protected synchronized void setOkOptions(WhSocketOptions okOptions) {
        mOkOptions = okOptions;
        mCurrentThreadMode = mOkOptions.getIOThreadMode();
        updateFrequency();
    }

    private class PulseThread extends AbsLoopThread {

        @Override
        protected void runInLoopThread() throws Exception {
            if (isDead) {
                shutdown();
                return;
            }
            if (mManager != null && mSendable != null) {
                if (mOkOptions.getPulseFeedLoseTimes() != -1 && mLoseTimes.incrementAndGet() >= mOkOptions.getPulseFeedLoseTimes()) {
                    mManager.disconnect(new DogDeadException("you need feed dog on time,otherwise he will die"));
                } else {
                    mManager.send(mSendable);
                }
            }

            //not safety sleep.
            Thread.sleep(mCurrentFrequency);
        }

        @Override
        protected void loopFinish(Exception e) {
        }
    }


}
