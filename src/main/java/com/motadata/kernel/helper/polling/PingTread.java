package com.motadata.kernel.helper.polling;

import com.motadata.kernel.bean.PollingPingBean;
import com.motadata.kernel.dao.Database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PingTread implements Runnable{

    String id;

    String ip;

    PingTread(String id,String ip){

        this.id=id;

        this.ip=ip;
    }

    @Override
    public void run() {

        PollingPingBean pollingPingBean=new PollingDump().getPingData(ip);

        ArrayList values=new ArrayList(Arrays.asList(id,pollingPingBean.getSentPacket(),pollingPingBean.getReceivePacket(),pollingPingBean.getPacketLoss(),pollingPingBean.getRTT(),new Timestamp(new Date().getTime())));

        new PollingDump().refreshAvailality(id,pollingPingBean.getPacketLoss());

        String query="insert into pingdump (id,sentpackets,receivepackets,packetloss,rtt,pollingtime) values(?,?,?,?,?,?)";

        Database.update(query,values);

    }
}
