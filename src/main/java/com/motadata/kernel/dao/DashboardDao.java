package com.motadata.kernel.dao;

import com.motadata.kernel.bean.DashboardBean;
import com.motadata.kernel.helper.GetData;

public class DashboardDao {

    public void load(DashboardBean dashboardBean){

        GetData getData=new GetData();

        dashboardBean.setAvailability(getData.getDashboardData());

    }
}
