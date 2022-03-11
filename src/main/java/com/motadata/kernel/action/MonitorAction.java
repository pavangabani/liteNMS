package com.motadata.kernel.action;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.executor.MonitorExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class MonitorAction implements ModelDriven {

    MonitorBean monitorBean=new MonitorBean();

    MonitorExecutor monitorExecutor =new MonitorExecutor();

    public String load(){

        monitorExecutor.load(monitorBean);

        return "LOADED";
    }

    public String add() {

        monitorExecutor.add(monitorBean);

        return "ADDED";
    }

    public String addPolling(){

        monitorExecutor.addPolling(monitorBean);

        return "ADDED";
    }

    public String edit(){

        monitorExecutor.edit(monitorBean);

        return "EDITED";
    }

    public String delete(){

        monitorExecutor.delete(monitorBean);

        return "DELETED";
    }

    @Override
    public Object getModel() {

        return monitorBean;
    }
}
