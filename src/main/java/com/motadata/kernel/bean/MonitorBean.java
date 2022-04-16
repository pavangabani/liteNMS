package com.motadata.kernel.bean;

import java.util.ArrayList;
import java.util.List;

public class MonitorBean
{
    private String id;

    private String name;

    private String ip;

    private String type;

    private String tag;

    private String username;

    private String password;

    private String status;

    private String ipWithCider;

    List<MonitorBean> monitorList = new ArrayList<>();

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

    public String getIpWithCider()
    {
        return ipWithCider;
    }

    public void setIpWithCider(String ipWithCider)
    {
        this.ipWithCider = ipWithCider;
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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<MonitorBean> getMonitorList()
    {
        return monitorList;
    }

    public void setMonitorList(List<MonitorBean> monitorList)
    {
        this.monitorList = monitorList;
    }
}
