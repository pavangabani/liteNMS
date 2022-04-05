package com.motadata.kernel.action;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.executor.MonitorExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class MonitorAction implements ModelDriven<MonitorBean>
{
    MonitorBean monitorBean = new MonitorBean();

    MonitorExecutor monitorExecutor = new MonitorExecutor();

    public String load()
    {
        monitorExecutor.load(monitorBean);

        return "LOADED";
    }

    public String add()
    {
        monitorExecutor.add(monitorBean);

        return "ADDED";
    }

    public String addDiscovery()
    {
        monitorExecutor.addDiscovery(monitorBean);

        return "ADDED";
    }

    public String editData()
    {
        monitorExecutor.editData(monitorBean);

        return "EDITDATA";
    }

    public String edit()
    {
        monitorExecutor.edit(monitorBean);

        return "EDITED";
    }

    public String delete()
    {
        monitorExecutor.delete(monitorBean);

        return "DELETED";
    }

    @Override
    public MonitorBean getModel()
    {
        return monitorBean;
    }
}
