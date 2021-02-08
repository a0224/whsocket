package com.hao.core.iocore;


import com.hao.core.iocore.interfaces.IIOCoreOptions;
import com.hao.core.iocore.interfaces.IReader;
import com.hao.core.iocore.interfaces.IStateSender;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tony on 2017/12/26.
 */

public abstract class AbsReader implements IReader<com.hao.core.iocore.interfaces.IIOCoreOptions> {

    protected volatile com.hao.core.iocore.interfaces.IIOCoreOptions mOkOptions;

    protected com.hao.core.iocore.interfaces.IStateSender mStateSender;

    protected InputStream mInputStream;

    public AbsReader() {
    }

    @Override
    public void initialize(InputStream inputStream, IStateSender stateSender) {
        mStateSender = stateSender;
        mInputStream = inputStream;
    }

    @Override
    public void setOption(IIOCoreOptions option) {
        mOkOptions = option;
    }


    @Override
    public void close() {
        if (mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }
}
