package com.motadata.kernel.action;

import com.motadata.kernel.bean.DashboardBean;
import com.motadata.kernel.executor.DashboardExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class DashboardAction implements ModelDriven
{
    DashboardBean dashboardBean = new DashboardBean();

    DashboardExecutor dashboardExecutor = new DashboardExecutor();

    public String load()
    {
        dashboardExecutor.load(dashboardBean);

        return "LOADED";
    }

    @Override
    public Object getModel()
    {
        return dashboardBean;
    }
}
