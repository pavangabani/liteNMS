package com.motadata.kernel.bean;

public class PollingSshBean {

    Integer totalMemory;

    Integer cpu;

    Integer disk;

    Integer memory;

    String upTime;

    String totalDisk;

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public Integer getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Integer totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getTotalDisk() {
        return totalDisk;
    }

    public void setTotalDisk(String totalDisk) {
        this.totalDisk = totalDisk;
    }

}
