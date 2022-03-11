package com.motadata.kernel.helper.polling;

import com.motadata.kernel.bean.PollingPingBean;
import com.motadata.kernel.dao.Database;

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

        PollingDump dump=new PollingDump();

        PollingPingBean pollingPingBean=dump.getPingData(ip);

        //QueryStart

        String query="insert into pingdump (id,sentpackets,receivepackets,packetloss,rtt,pollingtime) values(?,?,?,?,?,?)";

        ArrayList values=new ArrayList(Arrays.asList(id,pollingPingBean.getSentPacket(),pollingPingBean.getReceivePacket(),pollingPingBean.getPacketLoss(),pollingPingBean.getRTT(),new Timestamp(new Date().getTime())));

        Database.update(query,values);

        //QueryEnd

        dump.refreshAvailality(id,pollingPingBean.getPacketLoss());

        if(pollingPingBean.getPacketLoss()<50){

            return true;

        }else {

            return false;

        }
    }
}
