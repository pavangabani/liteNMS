package com.motadata.kernel.helper.polling;

import com.motadata.kernel.bean.PollingSshBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.GetData;

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

        PollingSshBean pollingSshBean=new GetData().getSshData(ip);

        //QueryStart

        Database database=new Database();

        String query="insert into sshdump (id,cpu,memory,disk,uptime,pollingtime,totaldisk,totalmemory) values(?,?,?,?,?,?,?,?)";

        ArrayList<Object> values=new ArrayList(Arrays.asList(id,pollingSshBean.getCpu(),pollingSshBean.getMemory(),pollingSshBean.getDisk(),pollingSshBean.getUpTime(),new Timestamp(new Date().getTime()),pollingSshBean.getTotalDisk(),pollingSshBean.getTotalMemory()));

        database.update(query,values);

        database.releaseConnection();

        //QueryEnd
    }
}
