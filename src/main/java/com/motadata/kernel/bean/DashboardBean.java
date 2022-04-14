package com.motadata.kernel.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardBean {

    List<Integer> availability=new ArrayList<>(4);

    List<Integer> health=new ArrayList<>(4);

    List<HashMap<String,String>> topRtt =new ArrayList<>();

    List<HashMap<String,String>> topCpu =new ArrayList<>();

    List<HashMap<String,String>> topMemory =new ArrayList<>();

    List<HashMap<String,String>> topDisk =new ArrayList<>();

    List<HashMap<String,String>> monitorGroup =new ArrayList<>();

    public List<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Integer> availability) {
        this.availability = availability;
    }

    public List<HashMap<String, String>> getTopRtt()
    {
        return topRtt;
    }

    public void setTopRtt(List<HashMap<String, String>> topRtt)
    {
        this.topRtt = topRtt;
    }

    public List<HashMap<String, String>> getTopCpu()
    {
        return topCpu;
    }

    public void setTopCpu(List<HashMap<String, String>> topCpu)
    {
        this.topCpu = topCpu;
    }

    public List<HashMap<String, String>> getTopMemory()
    {
        return topMemory;
    }

    public void setTopMemory(List<HashMap<String, String>> topMemory)
    {
        this.topMemory = topMemory;
    }

    public List<HashMap<String, String>> getTopDisk()
    {
        return topDisk;
    }

    public void setTopDisk(List<HashMap<String, String>> topDisk)
    {
        this.topDisk = topDisk;
    }

    public List<HashMap<String, String>> getMonitorGroup()
    {
        return monitorGroup;
    }

    public void setMonitorGroup(List<HashMap<String, String>> monitorGroup)
    {
        this.monitorGroup = monitorGroup;
    }

    public List<Integer> getHealth()
    {
        return health;
    }

    public void setHealth(List<Integer> health)
    {
        this.health = health;
    }
}
