package com.motadata.kernel.dao;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.helper.GetData;

import java.util.ArrayList;
import java.util.Arrays;

public class PollingMonitorDao {

    public void load(PollingMonitorBean pollingMonitorBean){

        GetData getData=new GetData();

        pollingMonitorBean.setPollingMonitorBeanList(getData.getAllPollingMonitor());

    }

    public void delete(PollingMonitorBean pollingMonitorBean){

        Database database = new Database();

        ArrayList attributes = new ArrayList(Arrays.asList("id"));

        ArrayList values = new ArrayList(Arrays.asList(pollingMonitorBean.getId()));

        int affectedRaw = database.delete("pollingmonitor", attributes, values);

        if (affectedRaw > 0) {

            pollingMonitorBean.setStatus("Monitor Deleted");

        } else {

            pollingMonitorBean.setStatus("Could not Delete");

        }

    }
}