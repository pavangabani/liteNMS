package com.motadata.kernel.bean;

public class MonitorBean {

    private String name;

    private String ip;

    private String type;

    private String tag;

    public MonitorBean(String name, String ip, String type, String tag){

        this.name=name;

        this.ip=ip;

        this.type=type;

        this.tag=tag;
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

    public void display(){
        System.out.println(this.name+" "+this.ip+" "+this.type+" "+this.tag);
    }

}
