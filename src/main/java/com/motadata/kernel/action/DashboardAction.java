package com.motadata.kernel.action;

import com.motadata.kernel.bean.DashboardBean;
import com.motadata.kernel.executor.DashboardExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class DashboardAction implements ModelDriven<DashboardBean>
{
    DashboardBean dashboardBean = new DashboardBean();

    public String load()
    {
        try
        {
            DashboardExecutor.load(dashboardBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "LOADED";
    }

    @Override
    public DashboardBean getModel()
    {
        return dashboardBean;
    }
}
