package com.motadata.kernel.helper.polling;

public class PingTread implements Runnable{

    String id;

    String ip;

    PingTread(String id,String ip){

        this.id=id;

        this.ip=ip;
    }

    @Override
    public void run() {

    }
}
