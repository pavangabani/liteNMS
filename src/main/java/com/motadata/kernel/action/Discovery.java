package com.motadata.kernel.action;

import com.motadata.kernel.dao.Discover;

public class Discovery {

    private String ip;

    private String type;

    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String discovery(){

        Discover discover=new Discover();

        if(type.equals("ping")){

            status=discover.ping(ip);

        }
        else {

            status=discover.ssh();

        }

        return "DISCOVER";

    }

}
