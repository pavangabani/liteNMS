package com.motadata.kernel.action;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.Database;

import java.util.ArrayList;
import java.util.List;

public class AddPolling {

    private String name;

    private String ip;

    private String type;

    private String tag;

    private String status;

    private List<PollingMonitorBean> pollingMonitorBeanList=new ArrayList<>();

    public List<PollingMonitorBean> getPollingMonitorBeanList() {
        return pollingMonitorBeanList;
    }

    public void setPollingMonitorBeanList(List<PollingMonitorBean> pollingMonitorBeanList) {
        this.pollingMonitorBeanList = pollingMonitorBeanList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String addPolling(){

        Database database=new Database(name,ip,type,tag);

        if(database.addPollingMonitor()) {

            status = "monitor added for polling";

        }else {

            status = "fails to add";

        }
        return "ADDED";

    }
    public String load(){

        Database database=new Database();

        this.setPollingMonitorBeanList(database.getAllPollingMonitor());

        return "LOADED";
    }

}
