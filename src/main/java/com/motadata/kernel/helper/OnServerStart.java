package com.motadata.kernel.helper;

import com.motadata.kernel.helper.polling.PollingScheduler;

import javax.servlet.http.HttpServlet;

public class OnServerStart extends HttpServlet {

    public void init()
    {
        PollingScheduler pollingScheduler=new PollingScheduler();

        pollingScheduler.createScheduler();

        pollingScheduler.startScheduler();
    }
}
