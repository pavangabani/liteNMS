package com.motadata.kernel.helper.polling;

import com.motadata.kernel.bean.PollingSshBean;
import com.motadata.kernel.dao.Database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SshTread implements Runnable{

    String id;

    String ip;

    SshTread(String id,String ip){

        this.id=id;

        this.ip=ip;
    }

    @Override
    public void run() {

        Database database=new Database();

        PollingSshBean pollingSshBean=new PollingDump().getSshData(ip);

        ArrayList attributes=new ArrayList(Arrays.asList("id","cpu","memory","disk","uptime","pollingtime"));

        ArrayList values=new ArrayList(Arrays.asList(id,pollingSshBean.getCpu(),pollingSshBean.getMemory(),pollingSshBean.getDisk(),pollingSshBean.getUpTime(),new Timestamp(new Date().getTime())));

        database.insert("sshdump",attributes,values);

    }
}
