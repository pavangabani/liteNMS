package com.motadata.kernel.executor;

import com.motadata.kernel.bean.DashboardBean;
import com.motadata.kernel.helper.GetData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DashboardExecutor
{
    public static void load(DashboardBean dashboardBean)
    {
        try
        {
            GetData getData = new GetData();

            ArrayList<Object> data=getData.getDashboardData();

            dashboardBean.setAvailability((ArrayList<Integer>) data.get(0));

            dashboardBean.setMonitorGroup((List<HashMap<String, String>>) data.get(1));

            dashboardBean.setTopRtt((List<HashMap<String, String>>) data.get(2));

            dashboardBean.setTopCpu((List<HashMap<String, String>>) data.get(3));

            dashboardBean.setTopMemory((List<HashMap<String, String>>) data.get(4));

            dashboardBean.setTopDisk((List<HashMap<String, String>>) data.get(5));

            dashboardBean.setHealth((List<Integer>) data.get(6));

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
