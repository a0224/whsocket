package com.hao.socket.client.impl.client.abilities;


import com.hao.socket.client.sdk.client.ConnectionInfo;
import com.hao.socket.client.sdk.client.connection.IConnectionManager;

public interface IConnectionSwitchListener {
    void onSwitchConnectionInfo(IConnectionManager manager, ConnectionInfo oldInfo, ConnectionInfo newInfo);
}
