package com.motadata.kernel.action;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.Database;
import com.opensymphony.xwork2.ModelDriven;


public class PollingMonitor implements ModelDriven {

    PollingMonitorBean pollingMonitorBean=new PollingMonitorBean();

    public String load(){

        Database database=new Database();

        pollingMonitorBean.setPollingMonitorBeanList(database.getAllPollingMonitor());

        return "LOADED";
    }

    @Override
    public Object getModel() {
        return pollingMonitorBean;
    }
}
