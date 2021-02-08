package com.hao.socket.client.impl.client.iothreads;


import com.hao.socket.client.impl.exceptions.ManuallyDisconnectException;
import com.hao.core.iocore.interfaces.IReader;
import com.hao.core.iocore.interfaces.IStateSender;
import com.hao.core.utils.SLog;
import com.hao.socket.client.sdk.client.action.IAction;
import com.hao.socket.common.interfaces.basic.AbsLoopThread;

import java.io.IOException;

public class DuplexReadThread extends AbsLoopThread {
    private IStateSender mStateSender;

    private IReader mReader;

    public DuplexReadThread(IReader reader, IStateSender stateSender) {
        super("client_duplex_read_thread");
        this.mStateSender = stateSender;
        this.mReader = reader;
    }

    @Override
    protected void beforeLoop() {
        mStateSender.sendBroadcast(IAction.ACTION_READ_THREAD_START);
    }

    @Override
    protected void runInLoopThread() throws IOException {
        mReader.read();
    }

    @Override
    public synchronized void shutdown(Exception e) {
        mReader.close();
        super.shutdown(e);
    }

    @Override
    protected void loopFinish(Exception e) {
        e = e instanceof ManuallyDisconnectException ? null : e;
        if (e != null) {
            SLog.e("duplex read error,thread is dead with exception:" + e.getMessage());
        }
        mStateSender.sendBroadcast(IAction.ACTION_READ_THREAD_SHUTDOWN, e);
    }
}
