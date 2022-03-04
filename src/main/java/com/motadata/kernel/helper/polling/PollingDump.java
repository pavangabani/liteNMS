package com.motadata.kernel.helper.polling;

import com.jcraft.jsch.*;
import com.motadata.kernel.bean.PollingPingBean;
import com.motadata.kernel.bean.PollingSshBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PollingDump {

    PollingPingBean getPingData(String ip){

        PollingPingBean pollingPingBean=new PollingPingBean();

        try {

            Runtime runtime = Runtime.getRuntime();

            String command = "ping -c 4 " + ip;

            String[] cmd = {"/bin/sh", "-c", command};

            Process process = runtime.exec(cmd);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            String outputString = "";

            while ((line = reader.readLine()) != null) {

                outputString += line;

            }
            System.out.println(ip);

            outputString=outputString.substring(outputString.indexOf("statistics"));

            Integer RTT=Integer.parseInt((outputString.substring(outputString.indexOf("time") + 5, outputString.indexOf("ms"))).trim());

            Integer receivedPacket=Integer.parseInt((outputString.substring(outputString.indexOf("transmitted") + 13, outputString.indexOf("received"))).trim());

            int packetLoss=(int)((1-(receivedPacket/4.0))*100);

            Integer sentPacket=4;

            pollingPingBean.setPacketLoss(packetLoss);

            pollingPingBean.setRTT(RTT);

            pollingPingBean.setReceivePacket(receivedPacket);

            pollingPingBean.setSentPacket(sentPacket);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return pollingPingBean;

    }
    PollingSshBean getSshData(String ip){

        PollingSshBean pollingSshBean=new PollingSshBean();

        try {

            Properties config = new Properties();

            config.put("StrictHostKeyChecking", "no");

            JSch jsch=new JSch();

            Session session=  jsch.getSession("pavan",ip,22);

            session.setPassword("Mind@123");

            session.setConfig(config);

            session.connect();

            System.out.println("Connected: " + ip);

            Channel channel=session.openChannel("exec");

            ((ChannelExec)channel).setCommand("free -m | grep Mem | awk '{print $2}';free -m | grep Mem | awk '{print $3}'");

            InputStream in=channel.getInputStream();

            channel.connect();

            String ans =inputStreamToString(in);

            String[] ansArray= ans.split("\n");

            float total=Float.valueOf(ansArray[0].trim());

            float used=Float.valueOf(ansArray[1].trim());

            float memoryUsage=(used/total)*100;

            System.out.println("Memory Usage: "+memoryUsage+" %");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return pollingSshBean;

    }

    String inputStreamToString(InputStream inputStream) throws IOException {

        StringBuilder sb = new StringBuilder();

        for (int ch; (ch = inputStream.read()) != -1; ) {

            sb.append((char) ch);

        }

        return sb.toString();

    }
}
