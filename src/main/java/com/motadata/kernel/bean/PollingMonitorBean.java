package com.motadata.kernel.bean;

public class PollingMonitorBean {

    private String name;

    private String ip;

    private String type;

    private String tag;

    private String availability;

    private String health;

    public PollingMonitorBean(String name, String ip, String type, String tag,String health,String availability){

        this.name=name;

        this.ip=ip;

        this.type=type;

        this.tag=tag;

        this.health=health;

        this.availability=availability;

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

    public void display(){
        System.out.println(this.name+" "+this.ip+" "+this.type+" "+this.tag);
    }

}
