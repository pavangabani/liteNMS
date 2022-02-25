package com.motadata.kernel.dao;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.helper.GetData;

public class PollingMonitorDao {

    public void load(PollingMonitorBean pollingMonitorBean){

        GetData getData=new GetData();

        pollingMonitorBean.setPollingMonitorBeanList(getData.getAllPollingMonitor());

    }
}
