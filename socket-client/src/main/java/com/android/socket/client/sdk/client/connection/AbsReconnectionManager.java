package com.android.socket.client.sdk.client.connection;

import com.android.socket.client.impl.PulseManager;
import com.android.socket.client.sdk.client.ConnectionInfo;
import com.android.socket.client.sdk.client.action.ISocketActionListener;
import com.android.socket.client.core.iocore.interfaces.ISendable;
import com.android.socket.client.core.pojo.OriginalData;
import com.android.socket.client.core.iocore.interfaces.IPulseSendable;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbsReconnectionManager implements ISocketActionListener {

    protected volatile IConnectionManager mConnectionManager;

    protected PulseManager mPulseManager;

    protected volatile boolean mDetach;

    protected volatile Set<Class<? extends Exception>> mIgnoreDisconnectExceptionList = new LinkedHashSet<>();

    public AbsReconnectionManager() {

    }

    public synchronized void attach(IConnectionManager manager) {
        if (mDetach) {
            detach();
        }
        mDetach = false;
        mConnectionManager = manager;
        mPulseManager = manager.getPulseManager();
        mConnectionManager.registerReceiver(this);
    }

    public synchronized void detach() {
        mDetach = true;
        if (mConnectionManager != null) {
            mConnectionManager.unRegisterReceiver(this);
        }
    }

    public final void addIgnoreException(Class<? extends Exception> e) {
        synchronized (mIgnoreDisconnectExceptionList) {
            mIgnoreDisconnectExceptionList.add(e);
        }
    }

    public final void removeIgnoreException(Exception e) {
        synchronized (mIgnoreDisconnectExceptionList) {
            mIgnoreDisconnectExceptionList.remove(e.getClass());
        }
    }

    public final void removeIgnoreException(Class<? extends Exception> e) {
        synchronized (mIgnoreDisconnectExceptionList) {
            mIgnoreDisconnectExceptionList.remove(e);
        }
    }

    public final void removeAll() {
        synchronized (mIgnoreDisconnectExceptionList) {
            mIgnoreDisconnectExceptionList.clear();
        }
    }

    @Override
    public void onSocketIOThreadStart(String action) {
        //do nothing;
    }

    @Override
    public void onSocketIOThreadShutdown(String action, Exception e) {
        //do nothing;
    }

    @Override
    public void onSocketReadResponse(com.android.socket.client.sdk.client.ConnectionInfo info, String action, OriginalData data) {
        //do nothing;
    }

    @Override
    public void onSocketWriteResponse(com.android.socket.client.sdk.client.ConnectionInfo info, String action, ISendable data) {
        //do nothing;
    }

    @Override
    public void onPulseSend(ConnectionInfo info, IPulseSendable data) {
        //do nothing;
    }

}
