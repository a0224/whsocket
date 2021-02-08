package com.hao.socket.common.interfaces.common_interfacies.server;


import com.hao.socket.core.iocore.interfaces.IIOCoreOptions;


public interface IServerManagerPrivate<E extends IIOCoreOptions> extends IServerManager<E> {
    void initServerPrivate(int serverPort);
}
