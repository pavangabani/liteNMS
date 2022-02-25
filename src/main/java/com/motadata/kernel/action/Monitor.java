package com.motadata.kernel.action;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.dao.MonitorDao;
import com.opensymphony.xwork2.ModelDriven;

public class Monitor implements ModelDriven {

    MonitorBean monitorBean=new MonitorBean();

    MonitorDao monitorDao=new MonitorDao();

    public String load(){

        monitorDao.load(monitorBean);

        return "LOADED";
    }

    public String add() {

        monitorDao.add(monitorBean);

        return "ADDED";
    }

    public String run(){

        monitorDao.run(monitorBean);

        return "RUN";

    }

    public String addPolling(){

        monitorDao.addPolling(monitorBean);

        return "ADDED";

    }

    public String delete(){

        monitorDao.delete(monitorBean);

        return "DELETED";
    }


    @Override
    public Object getModel() {
        return monitorBean;
    }
}
