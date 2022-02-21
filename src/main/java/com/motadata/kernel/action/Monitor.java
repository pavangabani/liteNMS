package com.motadata.kernel.action;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.dao.Discover;
import com.opensymphony.xwork2.ModelDriven;

public class Monitor implements ModelDriven {

    MonitorBean monitorBean=new MonitorBean();

    public String load(){

        Database database=new Database();

        monitorBean.setMonitorList(database.getAllMonitor());

        return "LOADED";
    }

    public String add() {

        Database database = new Database();

        database.setName(monitorBean.getName());

        database.setIp(monitorBean.getIp());

        database.setType(monitorBean.getType());

        database.setTag(monitorBean.getTag());

        database.setUsername(monitorBean.getUsername());

        database.setPassword(monitorBean.getPassword());

        if (monitorBean.getType().equals("ping")) {

            if (database.addPingMonitor()) {

                monitorBean.setStatus("Added Ping");

            }
        } else {

            if (database.addSshMonitor()) {

                monitorBean.setStatus("Added SSH");

            }
        }

        monitorBean.setMonitorList(database.getAllMonitor());

        return "ADDED";
    }

    public String run(){

        Discover discover=new Discover();

        if(monitorBean.getType().equals("ping")){

            monitorBean.setStatus(discover.ping(monitorBean.getIp()));

        }
        else {

            monitorBean.setStatus(discover.ssh());

        }

        return "RUN";

    }

    public String addPolling(){

        Database database=new Database();

        database.setId(monitorBean.getId());

        database.setName(monitorBean.getName());

        database.setIp(monitorBean.getIp());

        database.setType(monitorBean.getType());

        database.setTag(monitorBean.getTag());

        if(database.addPollingMonitor()) {

            monitorBean.setStatus("Monitor added for polling");

        }else {

            monitorBean.setStatus("Fails to add");

        }
        return "ADDED";

    }

    public String delete(){

        Database database=new Database();

        database.setId(monitorBean.getId());

        if(database.deleteMonitor()){

            monitorBean.setStatus("Monitor Deleted");

        }
        else {

            monitorBean.setStatus("Could not Delete");

        }

        return "DELETED";
    }


    @Override
    public Object getModel() {
        return monitorBean;
    }
}
