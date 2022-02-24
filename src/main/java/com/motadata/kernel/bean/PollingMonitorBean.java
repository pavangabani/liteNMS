package com.motadata.kernel.bean;

import java.util.ArrayList;
import java.util.List;

public class PollingMonitorBean {

    private String id;

    private String name;

    private String ip;

    private String type;

    private String tag;

    private String status;

    private String availability;

    private String health;

    private List<PollingMonitorBean> pollingMonitorBeanList=new ArrayList<>();

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public List<PollingMonitorBean> getPollingMonitorBeanList() {
        return pollingMonitorBeanList;
    }

    public void setPollingMonitorBeanList(List<PollingMonitorBean> pollingMonitorBeanList) {
        this.pollingMonitorBeanList = pollingMonitorBeanList;
    }

}
