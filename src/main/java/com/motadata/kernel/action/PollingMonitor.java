package com.motadata.kernel.action;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.PollingMonitorDao;
import com.opensymphony.xwork2.ModelDriven;

public class PollingMonitor implements ModelDriven {

    PollingMonitorBean pollingMonitorBean=new PollingMonitorBean();

    PollingMonitorDao pollingMonitorDao=new PollingMonitorDao();

    public String load(){

        pollingMonitorDao.load(pollingMonitorBean);

        return "LOADED";

    }

    public String show(){

        pollingMonitorDao.show(pollingMonitorBean);

        return "SHOW";
    }

    public String delete() {

        pollingMonitorDao.delete(pollingMonitorBean);

        return "DELETED";
    }

    @Override
    public Object getModel() {

        return pollingMonitorBean;

    }

}
