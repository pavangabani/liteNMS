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

    public void display(){
        System.out.println(this.name+" "+this.ip+" "+this.type+" "+this.tag);
    }
}
