package com.motadata.kernel.helper.polling;

import java.util.concurrent.RecursiveAction;

public class PollingThread extends RecursiveAction
{
    String ip;

    String id;

    public PollingThread(String id, String ip, String type)
    {
        this.ip = ip;

        this.id = id;

        this.type = type;
    }

    String type;

    @Override
    protected void compute()
    {
        PingThread task = new PingThread(id, ip);

        task.fork();

        Boolean ping = task.join();

        if (ping && type.equals("ssh"))
        {
           new SshThread(id, ip).fork();
        }
    }
}
