package com.android.socket.client.sdk.client.connection.abilities;


import com.android.socket.client.sdk.client.WhSocketOptions;
import com.android.socket.client.sdk.client.connection.IConnectionManager;

public interface IConfiguration {
    /**
     * 修改参数配置
     *
     * @param okOptions 新的参数配置
     * @return 当前的链接管理器
     */
    IConnectionManager option(WhSocketOptions okOptions);

    /**
     * 获得当前连接管理器的参数配置
     *
     * @return 参数配置
     */
    WhSocketOptions getOption();
}
