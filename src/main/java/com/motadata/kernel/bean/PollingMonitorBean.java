package com.motadata.kernel.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PollingMonitorBean
{
    private String id;

    private String name;

    private String ip;

    private String type;

    private String tag;

    private String status;

    private String availability;

    private String email;

    private List<PollingMonitorBean> pollingMonitorBeanList = new ArrayList<>();

    private HashMap<String, Object> pingStatistic = new HashMap<>();

    private HashMap<String, Object> sshStatistic = new HashMap<>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getAvailability()
    {
        return availability;
    }

    public void setAvailability(String availability)
    {
        this.availability = availability;
    }

    public List<PollingMonitorBean> getPollingMonitorBeanList()
    {
        return pollingMonitorBeanList;
    }

    public void setPollingMonitorBeanList(List<PollingMonitorBean> pollingMonitorBeanList) {
        this.pollingMonitorBeanList = pollingMonitorBeanList;
    }

    public HashMap<String, Object> getPingStatistic()
    {
        return pingStatistic;
    }

    public void setPingStatistic(HashMap<String, Object> pingStatistic)
    {
        this.pingStatistic = pingStatistic;
    }

    public HashMap<String, Object> getSshStatistic()
    {
        return sshStatistic;
    }

    public void setSshStatistic(HashMap<String, Object> sshStatistic)
    {
        this.sshStatistic = sshStatistic;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
