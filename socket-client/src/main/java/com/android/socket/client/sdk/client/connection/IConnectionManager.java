package com.android.socket.client.sdk.client.connection;

import com.android.socket.client.impl.PulseManager;
import com.android.socket.client.sdk.client.ConnectionInfo;
import com.android.socket.client.sdk.client.action.ISocketActionListener;
import com.android.socket.client.sdk.client.connection.abilities.IConfiguration;
import com.android.socket.client.sdk.client.connection.abilities.IConnectable;
import com.android.socket.client.common.interfaces.common_interfacies.client.IDisConnectable;
import com.android.socket.client.common.interfaces.common_interfacies.client.ISender;
import com.android.socket.client.common.interfaces.common_interfacies.dispatcher.IRegister;

public interface IConnectionManager extends
        IConfiguration,
        IConnectable,
        IDisConnectable,
        ISender<IConnectionManager>,
        IRegister<ISocketActionListener, IConnectionManager> {

    boolean isConnect();

    boolean isDisconnecting();

    PulseManager getPulseManager();

    void setIsConnectionHolder(boolean isHold);

    ConnectionInfo getRemoteConnectionInfo();

    ConnectionInfo getLocalConnectionInfo();

    void setLocalConnectionInfo(ConnectionInfo localConnectionInfo);

    void switchConnectionInfo(ConnectionInfo info);

    AbsReconnectionManager getReconnectionManager();

}

