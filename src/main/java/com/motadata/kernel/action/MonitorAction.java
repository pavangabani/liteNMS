package com.motadata.kernel.action;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.executor.MonitorExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class MonitorAction implements ModelDriven<MonitorBean>
{
    MonitorBean monitorBean = new MonitorBean();

    public String load()
    {
        try
        {
            MonitorExecutor.load(monitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "LOADED";
    }

    public String add()
    {
        try
        {
            MonitorExecutor.add(monitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "ADDED";
    }

    public String autoDiscover()
    {

        MonitorExecutor.autoDiscover(monitorBean);

        return "DISCOVERED";
    }

    public String addDiscovery()
    {
        try
        {
            MonitorExecutor.addDiscovery(monitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "ADDED";
    }

    public String editData()
    {
        try
        {
            MonitorExecutor.editData(monitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "EDITDATA";
    }

    public String edit()
    {
        try
        {
            MonitorExecutor.edit(monitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "EDITED";
    }

    public String delete()
    {
        try
        {
            MonitorExecutor.delete(monitorBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "DELETED";
    }

    @Override
    public MonitorBean getModel()
    {
        return monitorBean;
    }
}
