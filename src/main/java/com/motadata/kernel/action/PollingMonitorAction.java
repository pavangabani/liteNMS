package com.motadata.kernel.action;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.executor.PollingMonitorExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class PollingMonitorAction implements ModelDriven<PollingMonitorBean>
{
    PollingMonitorBean pollingMonitorBean = new PollingMonitorBean();

    public String load()
    {
        try
        {
            PollingMonitorExecutor.load(pollingMonitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "LOADED";
    }

    public String show()
    {
        try
        {
            PollingMonitorExecutor.show(pollingMonitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "SHOW";
    }
    public String emailAlerts()
    {
        try
        {
            PollingMonitorExecutor.emailAlerts(pollingMonitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "EMAILUPDATED";
    }

    public String delete()
    {
        try
        {
            PollingMonitorExecutor.delete(pollingMonitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "DELETED";
    }

    @Override
    public PollingMonitorBean getModel()
    {
        return pollingMonitorBean;
    }
}
