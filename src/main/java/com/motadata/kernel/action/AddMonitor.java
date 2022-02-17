package com.motadata.kernel.action;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.dao.Database;
import com.opensymphony.xwork2.ModelDriven;

import java.util.ArrayList;
import java.util.List;

public class AddMonitor {

    private String name;

    private String ip;

    private String type;

    private String tag;

    private String username;

    private String password;

    List<MonitorBean> monitorList = new ArrayList<>();

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<MonitorBean> getMonitorList() {
        return monitorList;
    }

    public void setMonitorList(List<MonitorBean> monitorList) {

        this.monitorList = monitorList;
    }


    public String add() {

        Database database = new Database(name, ip, type, tag, username, password);

        if (type.equals("ping")) {

            if (database.addPingMonitor()) {

                status = "Added Ping";
            }
        } else {

            if (database.addSshMonitor()) {

                status = "Added SSH";
            }
        }

        this.setMonitorList(database.getAllMonitor());

        return "ADDED";
    }

    public String load(){

        Database database=new Database();

        this.setMonitorList(database.getAllMonitor());

        return "LOADED";
    }

}
