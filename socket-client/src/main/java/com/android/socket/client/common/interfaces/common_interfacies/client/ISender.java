package com.android.socket.client.common.interfaces.common_interfacies.client;

import com.android.socket.client.core.iocore.interfaces.ISendable;

public interface ISender<T> {

    T send(ISendable sendable);
}
