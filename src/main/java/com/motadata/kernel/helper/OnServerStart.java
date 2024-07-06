package com.motadata.kernel.helper;

import com.motadata.kernel.dao.ConnectionPool;
import com.motadata.kernel.helper.discovery.Consumer;
import com.motadata.kernel.helper.discovery.Producer;
import com.motadata.kernel.helper.polling.PollingScheduler;
import org.h2.tools.Server;

import javax.servlet.http.HttpServlet;

public class OnServerStart extends HttpServlet
{
    public void init()
    {
        //CreateConnectionPool

        try
        {
            Server server = Server.createTcpServer("-baseDir", "/home/pavan/Desktop/Motadata/pavan/Project/" , "-tcpAllowOthers").start();

            Server m_h2WebServer = Server.createWebServer("-baseDir",  "/home/pavan/Desktop/Motadata/pavan/Project/" , "-webAllowOthers","-webExternalNames", "localhost").start();

            int size = ConnectionPool.createFixedSizePool(10);

            if (size != 10) System.exit(-1);

            //polling

            PollingScheduler pollingScheduler = new PollingScheduler();

            pollingScheduler.createScheduler();

            pollingScheduler.startScheduler();

            //nsq

            Producer.startProducer();

            Consumer.startConsumer();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
