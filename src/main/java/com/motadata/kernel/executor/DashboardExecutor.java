package com.motadata.kernel.executor;

import com.motadata.kernel.bean.DashboardBean;
import com.motadata.kernel.helper.GetData;

public class DashboardExecutor
{
    public void load(DashboardBean dashboardBean)
    {
        try
        {
            GetData getData = new GetData();

            dashboardBean.setAvailability(getData.getDashboardData());

        } catch (Exception e)
        {

            e.printStackTrace();
        }
    }
}
