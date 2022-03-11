package com.motadata.kernel.helper;

import com.jcraft.jsch.*;
import com.motadata.kernel.dao.Database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Discover {

    public boolean discovery(String ip, String type) {

        boolean discoveryTest = ping(ip);

        if (discoveryTest && type.equals("ssh")) {

            //QueryStart

            String query = "select * from credential where ip=?";

            ArrayList values = new ArrayList(Arrays.asList(ip));

            List<HashMap<String, String>> data = Database.select(query, values);

            //QueryEnd

            discoveryTest = ssh(ip, data.get(0).get("username"), Cipher.decode(data.get(0).get("password")));

        }

        return discoveryTest;

    }

    public boolean ping(String ip) {

        int packetLoss = 100;

        try {

            Runtime runtime = Runtime.getRuntime();

            Process process = runtime.exec("ping -c 4 " + ip);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line, answer = "";

            while ((line = reader.readLine()) != null) {

                answer += line;

            }

            answer = (answer.substring(answer.indexOf("received") + 10, answer.indexOf("%"))).trim();

            packetLoss = Integer.parseInt(answer);

        } catch (Exception e) {

            e.printStackTrace();

        }

        if (packetLoss < 50) {

            return true;

        }

        return false;

    }

    public boolean ssh(String ip, String username, String password) {

        boolean sshTest = false;

        Session session;

        try {

            Properties config = new Properties();

            JSch jsch = new JSch();

            config.put("StrictHostKeyChecking", "no");

            session = jsch.getSession(username, ip, 22);

            session.setPassword(password);

            session.setConfig(config);

            session.connect();

            if (session.isConnected()) {

                sshTest = true;

            }

        } catch (JSchException e) {

            e.printStackTrace();

        }

        return sshTest;

    }
}
