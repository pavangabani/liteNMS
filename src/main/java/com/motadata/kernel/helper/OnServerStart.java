package com.motadata.kernel.helper;

import com.motadata.kernel.dao.ConnectionPool;
import com.motadata.kernel.helper.polling.PollingScheduler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class OnServerStart extends HttpServlet
{
    public void init()
    {
        //CreateConnectionPool

        try
        {
            int size = ConnectionPool.createFixedSizePool(10);

            if (size != 10) throw new Exception("Connectionpool is not created");

            PollingScheduler pollingScheduler = new PollingScheduler();

            pollingScheduler.createScheduler();

            pollingScheduler.startScheduler();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
