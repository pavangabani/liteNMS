package com.motadata.kernel.action;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.executor.MonitorExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class MonitorAction implements ModelDriven<MonitorBean>
{
    MonitorBean monitorBean = new MonitorBean();

    public String load()
    {
        MonitorExecutor.load(monitorBean);

        return "LOADED";
    }

    public String add()
    {
        MonitorExecutor.add(monitorBean);

        return "ADDED";
    }

    public String addDiscovery()
    {
        MonitorExecutor.addDiscovery(monitorBean);

        return "ADDED";
    }

    public String editData()
    {
        MonitorExecutor.editData(monitorBean);

        return "EDITDATA";
    }

    public String edit()
    {
        MonitorExecutor.edit(monitorBean);

        return "EDITED";
    }

    public String delete()
    {
        MonitorExecutor.delete(monitorBean);

        return "DELETED";
    }

    @Override
    public MonitorBean getModel()
    {
        return monitorBean;
    }
}
