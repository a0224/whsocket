package com.android.socket.client.common.interfaces.common_interfacies.server;


import com.android.socket.client.core.iocore.interfaces.ISendable;
import com.android.socket.client.core.pojo.OriginalData;

public interface IClientIOCallback {

    void onClientRead(OriginalData originalData, IClient client, IClientPool<IClient, String> clientPool);

    void onClientWrite(ISendable sendable, IClient client, IClientPool<IClient, String> clientPool);

}
