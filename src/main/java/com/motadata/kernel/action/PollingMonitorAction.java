package com.motadata.kernel.action;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.executor.PollingMonitorExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class PollingMonitorAction implements ModelDriven<PollingMonitorBean>
{
    PollingMonitorBean pollingMonitorBean = new PollingMonitorBean();

    public String load()
    {
        PollingMonitorExecutor.load(pollingMonitorBean);

        return "LOADED";
    }

    public String show()
    {
        PollingMonitorExecutor.show(pollingMonitorBean);

        return "SHOW";
    }

    public String delete()
    {
        PollingMonitorExecutor.delete(pollingMonitorBean);

        return "DELETED";
    }

    @Override
    public PollingMonitorBean getModel()
    {
        return pollingMonitorBean;
    }
}
