package com.hao.socket.common.interfaces.common_interfacies.server;


import com.hao.core.iocore.interfaces.ISendable;
import com.hao.core.pojo.OriginalData;

public interface IClientIOCallback {

    void onClientRead(OriginalData originalData, IClient client, com.hao.socket.common.interfaces.common_interfacies.server.IClientPool<IClient, String> clientPool);

    void onClientWrite(ISendable sendable, IClient client, IClientPool<IClient, String> clientPool);

}
