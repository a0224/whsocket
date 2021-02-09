package com.android.socket.client.sdk.client.connection;


import com.android.socket.client.sdk.client.ConnectionInfo;

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
