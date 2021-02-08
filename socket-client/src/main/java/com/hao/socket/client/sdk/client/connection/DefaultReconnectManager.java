package com.hao.socket.client.sdk.client.connection;


import com.hao.socket.client.impl.exceptions.ManuallyDisconnectException;
import com.hao.socket.client.sdk.client.ConnectionInfo;
import com.hao.socket.core.utils.SLog;
import com.hao.socket.common.interfaces.basic.AbsLoopThread;
import com.hao.socket.common.interfaces.utils.ThreadUtils;

import java.util.Iterator;

public class DefaultReconnectManager extends AbsReconnectionManager {

    /**
     * 最大连接失败次数,不包括断开异常
     */
    private static final int MAX_CONNECTION_FAILED_TIMES = 12;
    /**
     * 连接失败次数,不包括断开异常
     */
    private int mConnectionFailedTimes = 0;

    private volatile ReconnectTestingThread mReconnectTestingThread;

    public DefaultReconnectManager() {
        mReconnectTestingThread = new ReconnectTestingThread();
    }

    @Override
    public void onSocketDisconnection(com.hao.socket.client.sdk.client.ConnectionInfo info, String action, Exception e) {
        if (isNeedReconnect(e)) {//break with exception
            reconnectDelay();
        } else {
            resetThread();
        }
    }

    @Override
    public void onSocketConnectionSuccess(com.hao.socket.client.sdk.client.ConnectionInfo info, String action) {
        resetThread();
    }

    @Override
    public void onSocketConnectionFailed(com.hao.socket.client.sdk.client.ConnectionInfo info, String action, Exception e) {
        if (e != null) {
            mConnectionFailedTimes++;
            if (mConnectionFailedTimes > MAX_CONNECTION_FAILED_TIMES) {
                resetThread();
                //连接失败达到阈值,需要切换备用线路.
                com.hao.socket.client.sdk.client.ConnectionInfo originInfo = mConnectionManager.getRemoteConnectionInfo();
                com.hao.socket.client.sdk.client.ConnectionInfo backupInfo = originInfo.getBackupInfo();
                if (backupInfo != null) {
                    com.hao.socket.client.sdk.client.ConnectionInfo bbInfo = new com.hao.socket.client.sdk.client.ConnectionInfo(originInfo.getIp(), originInfo.getPort());
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

    /**
     * 是否需要重连
     *
     * @param e
     * @return
     */
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


    /**
     * 重置重连线程,关闭线程
     */
    private synchronized void resetThread() {
        if (mReconnectTestingThread != null) {
            mReconnectTestingThread.shutdown();
        }
    }

    /**
     * 开始延迟重连
     */
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
        /**
         * 延时连接时间
         */
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
