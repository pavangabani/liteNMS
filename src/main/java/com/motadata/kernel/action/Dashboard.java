package com.motadata.kernel.action;

import com.motadata.kernel.bean.DashboardBean;
import com.motadata.kernel.dao.DashboardDao;
import com.opensymphony.xwork2.ModelDriven;

public class Dashboard implements ModelDriven {

    DashboardBean dashboardBean=new DashboardBean();

    DashboardDao dashboardDao=new DashboardDao();

    public String load(){

        dashboardDao.load(dashboardBean);

        return "LOADED";
    }

    @Override
    public Object getModel() {

        return dashboardBean;

    }

}
