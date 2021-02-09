package com.android.socket.client.common.interfaces.common_interfacies.server;


import com.android.socket.client.core.iocore.interfaces.IIOCoreOptions;


public interface IServerManagerPrivate<E extends IIOCoreOptions> extends IServerManager<E> {
    void initServerPrivate(int serverPort);
}
