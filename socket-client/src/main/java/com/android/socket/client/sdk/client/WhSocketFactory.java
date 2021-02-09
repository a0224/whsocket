package com.android.socket.client.sdk.client;


import java.net.Socket;

public abstract class WhSocketFactory {

    public abstract Socket createSocket(ConnectionInfo info, WhSocketOptions options) throws Exception;

}
