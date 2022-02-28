package com.motadata.kernel.helper;

import com.jcraft.jsch.*;
import com.motadata.kernel.dao.Database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class Discover {

    Session session;

    Properties config;

    JSch jsch;

    public Discover(){

        config = new Properties();

        config.put("StrictHostKeyChecking", "no");

        jsch=new JSch();

    }

    public boolean discovery(String ip,String type){

        boolean test=false;

        if(type.equals("ping")){

            test=ping(ip);

        }
        else {

            Database database=new Database();

            ArrayList attributes=new ArrayList<>();

            attributes.add("ip");

            ArrayList values=new ArrayList<>();

            values.add(ip);

            ResultSet resultSet=database.select("credential",attributes,values);

            if(!ping(ip)){

                return false;

            }

            try {
                resultSet.absolute(1);

                boolean sshTest=ssh(ip,resultSet.getString(2),resultSet.getString(3));

                test=sshTest;

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

        return test;

    }

    public boolean ping(String ip){

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

            return false;

        }

        else {

            return true;
        }

    }

    public boolean ssh(String ip,String username,String password) {

        boolean test;

        try {

            session = jsch.getSession(username,ip,22);

            session.setPassword(password);

            session.setConfig(config);

            session.connect();

            test=true;


        } catch (JSchException e) {

            test=false;

        }

          return test;

    }

}
