package com.motadata.kernel.helper.polling;

import com.motadata.kernel.bean.PollingSshBean;
import com.motadata.kernel.dao.Database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.RecursiveAction;

public class SshTread extends RecursiveAction {

    String id;

    String ip;

    SshTread(String id,String ip){

        this.id=id;

        this.ip=ip;
    }


    @Override
    protected void compute() {

        PollingSshBean pollingSshBean=new PollingDump().getSshData(ip);

        ArrayList values=new ArrayList(Arrays.asList(id,pollingSshBean.getCpu(),pollingSshBean.getMemory(),pollingSshBean.getDisk(),pollingSshBean.getUpTime(),new Timestamp(new Date().getTime())));

        String query="insert into sshdump (id,cpu,memory,disk,uptime,pollingtime) values(?,?,?,?,?,?)";

        Database.update(query,values);

    }
}
