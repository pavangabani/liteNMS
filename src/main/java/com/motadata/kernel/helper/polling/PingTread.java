package com.motadata.kernel.helper.polling;

import com.motadata.kernel.bean.PollingPingBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.GetData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.RecursiveTask;

public class PingTread extends RecursiveTask<Boolean> {

    String id;

    String ip;

    PingTread(String id,String ip){

        this.id=id;

        this.ip=ip;
    }

    @Override
    protected Boolean compute() {

        Database database=new Database();

        GetData getData=new GetData();

        PollingPingBean pollingPingBean=getData.getPingData(ip);

        //QueryStart

        String query="insert into pingdump (id,sentpackets,receivepackets,packetloss,rtt,pollingtime) values(?,?,?,?,?,?)";

        ArrayList values=new ArrayList(Arrays.asList(id,pollingPingBean.getSentPacket(),pollingPingBean.getReceivePacket(),pollingPingBean.getPacketLoss(),pollingPingBean.getRTT(),new Timestamp(new Date().getTime())));

        database.update(query,values);

        //QueryEnd

        boolean test;

        if(pollingPingBean.getPacketLoss()<50){

            values = new ArrayList(Arrays.asList("UP",id));

            query="update pollingmonitor set availability=? where id=?";

            database.update(query,values);

            test=true;

        }else {

            values = new ArrayList(Arrays.asList("DOWN",id));

            query="update pollingmonitor set availability=? where id=?";

            database.update(query,values);

            test=true;

        }

        database.releaseConnection();

        return test;

    }
}
