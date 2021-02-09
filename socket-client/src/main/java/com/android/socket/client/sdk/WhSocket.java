package com.android.socket.client.sdk;


import com.android.socket.client.impl.ManagerHolder;
import com.android.socket.client.sdk.client.WhSocketOptions;
import com.android.socket.client.sdk.client.ConnectionInfo;
import com.android.socket.client.sdk.client.connection.IConnectionManager;
import com.android.socket.client.common.interfaces.common_interfacies.dispatcher.IRegister;
import com.android.socket.client.common.interfaces.common_interfacies.server.IServerActionListener;
import com.android.socket.client.common.interfaces.common_interfacies.server.IServerManager;

public class WhSocket {

    private static ManagerHolder holder = ManagerHolder.getInstance();

    public static IRegister<IServerActionListener, IServerManager> server(int serverPort) {
        return (IRegister<IServerActionListener, IServerManager>) holder.getServer(serverPort);
    }

    public static IConnectionManager open(ConnectionInfo connectInfo) {
        return holder.getConnection(connectInfo);
    }

    public static IConnectionManager open(String ip, int port) {
        ConnectionInfo info = new ConnectionInfo(ip, port);
        return holder.getConnection(info);
    }

    public static IConnectionManager open(ConnectionInfo connectInfo, WhSocketOptions okOptions) {
        return holder.getConnection(connectInfo, okOptions);
    }

    public static IConnectionManager open(String ip, int port, WhSocketOptions okOptions) {
        ConnectionInfo info = new ConnectionInfo(ip, port);
        return holder.getConnection(info, okOptions);
    }
}
