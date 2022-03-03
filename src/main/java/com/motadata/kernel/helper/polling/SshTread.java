package com.motadata.kernel.helper.polling;

public class SshTread implements Runnable{

    String id;

    String ip;

    SshTread(String id,String ip){

        this.id=id;

        this.ip=ip;
    }

    @Override
    public void run() {

    }
}
