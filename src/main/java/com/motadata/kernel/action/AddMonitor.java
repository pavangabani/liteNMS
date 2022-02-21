package com.motadata.kernel.action;

import com.motadata.kernel.bean.AddMonitorBean;
import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.dao.Database;
import com.opensymphony.xwork2.ModelDriven;

import java.util.ArrayList;
import java.util.List;

public class AddMonitor implements ModelDriven {

    AddMonitorBean addMonitorBean=new AddMonitorBean();

    public String add() {

        Database database = new Database(addMonitorBean.getName(), addMonitorBean.getIp(), addMonitorBean.getType(), addMonitorBean.getTag(),addMonitorBean.getUsername(), addMonitorBean.getPassword());

        if (addMonitorBean.getType().equals("ping")) {

            if (database.addPingMonitor()) {

                addMonitorBean.setStatus("Added Ping");
            }
        } else {

            if (database.addSshMonitor()) {

                addMonitorBean.setStatus("Added SSH");
            }
        }

        addMonitorBean.setMonitorList(database.getAllMonitor());

        return "ADDED";
    }

    public String load(){

        Database database=new Database();

       addMonitorBean.setMonitorList(database.getAllMonitor());

        return "LOADED";
    }

    public String delete(){

        Database database=new Database(addMonitorBean.getId());

        if(database.deleteMonitor()){

            addMonitorBean.setStatus("Monitor Deleted");

        }
        else {

            addMonitorBean.setStatus("Could not Delete");

        }

        return "DELETED";
    }

    @Override
    public Object getModel() {
        return addMonitorBean;
    }
}
