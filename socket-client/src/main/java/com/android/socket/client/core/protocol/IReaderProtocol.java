package com.android.socket.client.core.protocol;

import java.nio.ByteOrder;

public interface IReaderProtocol {

    int getHeaderLength();

    int getBodyLength(byte[] header, ByteOrder byteOrder);
}
