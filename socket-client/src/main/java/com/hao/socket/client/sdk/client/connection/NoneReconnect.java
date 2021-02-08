package com.hao.socket.client.sdk.client.connection;


import com.hao.socket.client.sdk.client.ConnectionInfo;

/**
 * 不进行重新连接的重连管理器
 */
public class NoneReconnect extends AbsReconnectionManager {
    @Override
    public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {

    }

    @Override
    public void onSocketConnectionSuccess(ConnectionInfo info, String action) {

    }

    @Override
    public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {

    }
}
