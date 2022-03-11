package com.motadata.kernel.executor;

import com.motadata.kernel.bean.DashboardBean;
import com.motadata.kernel.helper.GetData;

public class DashboardExecutor {

    public void load(DashboardBean dashboardBean){

        GetData getData=new GetData();

        dashboardBean.setAvailability(getData.getDashboardData());

    }

}
