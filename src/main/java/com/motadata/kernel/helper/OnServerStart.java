package com.motadata.kernel.helper;

import com.motadata.kernel.dao.ConnectionPool;
import com.motadata.kernel.helper.polling.PollingScheduler;

import javax.servlet.http.HttpServlet;

public class OnServerStart extends HttpServlet
{
    public void init()
    {
        //StartPolling

        PollingScheduler pollingScheduler = new PollingScheduler();

        pollingScheduler.createScheduler();

        pollingScheduler.startScheduler();

        //CreateConnectionPool

        ConnectionPool.createFixedSizePool(10);
    }
}
