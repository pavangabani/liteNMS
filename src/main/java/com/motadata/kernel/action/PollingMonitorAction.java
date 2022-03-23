package com.motadata.kernel.action;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.executor.PollingMonitorExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class PollingMonitorAction implements ModelDriven
{
    PollingMonitorBean pollingMonitorBean = new PollingMonitorBean();

    PollingMonitorExecutor pollingMonitorExecutor = new PollingMonitorExecutor();

    public String load()
    {
        pollingMonitorExecutor.load(pollingMonitorBean);

        return "LOADED";
    }

    public String show()
    {
        pollingMonitorExecutor.show(pollingMonitorBean);

        return "SHOW";
    }

    public String delete()
    {
        pollingMonitorExecutor.delete(pollingMonitorBean);

        return "DELETED";
    }

    @Override
    public Object getModel()
    {
        return pollingMonitorBean;
    }
}
