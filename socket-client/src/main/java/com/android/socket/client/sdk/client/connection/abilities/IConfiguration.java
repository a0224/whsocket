package com.android.socket.client.sdk.client.connection.abilities;


import com.android.socket.client.sdk.client.WhSocketOptions;
import com.android.socket.client.sdk.client.connection.IConnectionManager;

public interface IConfiguration {

    IConnectionManager option(WhSocketOptions okOptions);

    WhSocketOptions getOption();
}
