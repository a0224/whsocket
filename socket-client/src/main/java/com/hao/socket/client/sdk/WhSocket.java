package com.hao.socket.client.sdk;


import com.hao.socket.client.impl.client.ManagerHolder;
import com.hao.socket.client.sdk.client.WhSocketOptions;
import com.hao.socket.client.sdk.client.ConnectionInfo;
import com.hao.socket.client.sdk.client.connection.IConnectionManager;
import com.hao.socket.common.interfaces.common_interfacies.dispatcher.IRegister;
import com.hao.socket.common.interfaces.common_interfacies.server.IServerActionListener;
import com.hao.socket.common.interfaces.common_interfacies.server.IServerManager;

public class WhSocket {

    private static com.hao.socket.client.impl.client.ManagerHolder holder = ManagerHolder.getInstance();

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
