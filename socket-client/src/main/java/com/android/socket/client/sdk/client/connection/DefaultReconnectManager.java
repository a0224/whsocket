package com.android.socket.client.sdk.client.connection;


import com.android.socket.client.impl.exceptions.ManuallyDisconnectException;
import com.android.socket.client.sdk.client.ConnectionInfo;
import com.android.socket.client.core.utils.SLog;
import com.android.socket.client.common.interfaces.basic.AbsLoopThread;
import com.android.socket.client.common.interfaces.utils.ThreadUtils;

import java.util.Iterator;

public class DefaultReconnectManager extends AbsReconnectionManager {

    private static final int MAX_CONNECTION_FAILED_TIMES = 12;

    private int mConnectionFailedTimes = 0;

    private volatile ReconnectTestingThread mReconnectTestingThread;

    public DefaultReconnectManager() {
        mReconnectTestingThread = new ReconnectTestingThread();
    }

    @Override
    public void onSocketDisconnection(com.android.socket.client.sdk.client.ConnectionInfo info, String action, Exception e) {
        if (isNeedReconnect(e)) {
            reconnectDelay();
        } else {
            resetThread();
        }
    }

    @Override
    public void onSocketConnectionSuccess(com.android.socket.client.sdk.client.ConnectionInfo info, String action) {
        resetThread();
    }

    @Override
    public void onSocketConnectionFailed(com.android.socket.client.sdk.client.ConnectionInfo info, String action, Exception e) {
        if (e != null) {
            mConnectionFailedTimes++;
            if (mConnectionFailedTimes > MAX_CONNECTION_FAILED_TIMES) {
                resetThread();
                //连接失败达到阈值,需要切换备用线路.
                com.android.socket.client.sdk.client.ConnectionInfo originInfo = mConnectionManager.getRemoteConnectionInfo();
                com.android.socket.client.sdk.client.ConnectionInfo backupInfo = originInfo.getBackupInfo();
                if (backupInfo != null) {
                    com.android.socket.client.sdk.client.ConnectionInfo bbInfo = new com.android.socket.client.sdk.client.ConnectionInfo(originInfo.getIp(), originInfo.getPort());
                    backupInfo.setBackupInfo(bbInfo);
                    if (!mConnectionManager.isConnect()) {
                        SLog.i("Prepare switch to the backup line " + backupInfo.getIp() + ":" + backupInfo.getPort() + " ...");
                        synchronized (mConnectionManager) {
                            mConnectionManager.switchConnectionInfo(backupInfo);
                        }
                        reconnectDelay();
                    }
                } else {
                    reconnectDelay();
                }
            } else {
                reconnectDelay();
            }
        }
    }

    private boolean isNeedReconnect(Exception e) {
        synchronized (mIgnoreDisconnectExceptionList) {
            if (e != null && !(e instanceof ManuallyDisconnectException)) {//break with exception
                Iterator<Class<? extends Exception>> it = mIgnoreDisconnectExceptionList.iterator();
                while (it.hasNext()) {
                    Class<? extends Exception> classException = it.next();
                    if (classException.isAssignableFrom(e.getClass())) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }

    private synchronized void resetThread() {
        if (mReconnectTestingThread != null) {
            mReconnectTestingThread.shutdown();
        }
    }

    private void reconnectDelay() {
        synchronized (mReconnectTestingThread) {
            if (mReconnectTestingThread.isShutdown()) {
                mReconnectTestingThread.start();
            }
        }
    }

    @Override
    public void detach() {
        super.detach();
    }

    private class ReconnectTestingThread extends AbsLoopThread {

        private long mReconnectTimeDelay = 10 * 1000;

        @Override

        protected void beforeLoop() throws Exception {
            super.beforeLoop();
            if (mReconnectTimeDelay < mConnectionManager.getOption().getConnectTimeoutSecond() * 1000) {
                mReconnectTimeDelay = mConnectionManager.getOption().getConnectTimeoutSecond() * 1000;
            }
        }

        @Override
        protected void runInLoopThread() throws Exception {
            if (mDetach) {
                SLog.i("ReconnectionManager already detached by framework.We decide gave up this reconnection mission!");
                shutdown();
                return;
            }

            //延迟执行
            SLog.i("Reconnect after " + mReconnectTimeDelay + " mills ...");
            ThreadUtils.sleep(mReconnectTimeDelay);

            if (mDetach) {
                SLog.i("ReconnectionManager already detached by framework.We decide gave up this reconnection mission!");
                shutdown();
                return;
            }

            if (mConnectionManager.isConnect()) {
                shutdown();
                return;
            }
            boolean isHolden = mConnectionManager.getOption().isConnectionHolden();

            if (!isHolden) {
                detach();
                shutdown();
                return;
            }
            ConnectionInfo info = mConnectionManager.getRemoteConnectionInfo();
            SLog.i("Reconnect the server " + info.getIp() + ":" + info.getPort() + " ...");
            synchronized (mConnectionManager) {
                if (!mConnectionManager.isConnect()) {
                    mConnectionManager.connect();
                } else {
                    shutdown();
                }
            }
        }


        @Override
        protected void loopFinish(Exception e) {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

}
