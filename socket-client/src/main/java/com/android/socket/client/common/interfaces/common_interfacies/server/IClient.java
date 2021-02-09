package com.android.socket.client.common.interfaces.common_interfacies.server;

import com.android.socket.client.core.protocol.IReaderProtocol;
import com.android.socket.client.common.interfaces.common_interfacies.client.IDisConnectable;
import com.android.socket.client.common.interfaces.common_interfacies.client.ISender;

import java.io.Serializable;

public interface IClient extends IDisConnectable, ISender<IClient>, Serializable {

    String getHostIp();

    String getHostName();

    String getUniqueTag();

    void setReaderProtocol(IReaderProtocol protocol);

    void addIOCallback(IClientIOCallback clientIOCallback);

    void removeIOCallback(IClientIOCallback clientIOCallback);

    void removeAllIOCallback();

}
