package com.hao.socket.common.interfaces.common_interfacies.server;


import com.hao.socket.core.iocore.interfaces.ISendable;
import com.hao.socket.core.pojo.OriginalData;

public interface IClientIOCallback {

    void onClientRead(OriginalData originalData, IClient client, IClientPool<IClient, String> clientPool);

    void onClientWrite(ISendable sendable, IClient client, IClientPool<IClient, String> clientPool);

}
