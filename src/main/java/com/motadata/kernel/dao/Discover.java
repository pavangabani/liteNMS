package com.motadata.kernel.dao;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Discover {

    Session session;
    Properties config;
    JSch jsch;

    public Discover(){
//        config = new Properties();
//        config.put("StrictHostKeyChecking", "no");
//        jsch=new JSch();
    }

    String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int ch; (ch = inputStream.read()) != -1; ) {
            sb.append((char) ch);
        }
        return sb.toString();
    }

    public String ping(String ip){

        Integer packetLoss=100;
        try {
            Runtime runtime=Runtime.getRuntime();
            String command="ping -c 4 "+ip;
            String[] cmd = { "/bin/sh", "-c", command };
            Process process=runtime.exec(cmd);
            BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String answer="";
            while((line= reader.readLine())!=null){
                answer+=line;
            }
            answer=(answer.substring(answer.indexOf("received")+10,answer.indexOf("%"))).trim();
            packetLoss=Integer.parseInt(answer);

        }catch (Exception e){
            e.printStackTrace();
        }

        if(packetLoss>50){

            return "Ping Packet loss: "+packetLoss+"%";

        }

        else {

            return "Ping Successful";
        }
    }

    public String ssh() {

//        session = jsch.getSession("pavan",ip,22);
//        session.setPassword("Mind@123");
//        session.setConfig(config);
//        session.connect();
//        System.out.println("Connected: " + ip);
//
//        Channel channel=session.openChannel("exec");
//        ((ChannelExec)channel).setCommand("free -m | grep Mem | awk '{print $2}';free -m | grep Mem | awk '{print $3}'");
//        InputStream in=channel.getInputStream();
//
//        channel.connect();
//        String ans =inputStreamToString(in);
//
          return null;

    }
}
