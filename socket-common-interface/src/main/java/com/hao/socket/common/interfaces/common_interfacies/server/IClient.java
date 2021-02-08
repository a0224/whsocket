package com.hao.socket.common.interfaces.common_interfacies.server;

import com.hao.core.protocol.IReaderProtocol;
import com.hao.socket.common.interfaces.common_interfacies.client.IDisConnectable;
import com.hao.socket.common.interfaces.common_interfacies.client.ISender;

import java.io.Serializable;

public interface IClient extends IDisConnectable, ISender<IClient>, Serializable {

    String getHostIp();

    String getHostName();

    String getUniqueTag();

    void setReaderProtocol(IReaderProtocol protocol);

    void addIOCallback(com.hao.socket.common.interfaces.common_interfacies.server.IClientIOCallback clientIOCallback);

    void removeIOCallback(IClientIOCallback clientIOCallback);

    void removeAllIOCallback();

}
