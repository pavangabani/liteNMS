package com.motadata.kernel.helper;

import com.jcraft.jsch.*;
import com.motadata.kernel.dao.Database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class Discover {

    Session session;

    Properties config;

    JSch jsch;

    public Discover() {

        config = new Properties();

        config.put("StrictHostKeyChecking", "no");

        jsch = new JSch();

    }

    public boolean discovery(String ip, String type) {

        boolean discoveryTest = ping(ip);

        if (type.equals("ssh")) {

            try {

                Database database = new Database();

                ArrayList attributes = new ArrayList(Arrays.asList("ip"));

                ArrayList values = new ArrayList(Arrays.asList(ip));

                ResultSet resultSet = database.select("credential", attributes, values);

                resultSet.absolute(1);

                boolean sshTest = ssh(ip, resultSet.getString(2), resultSet.getString(3));

                discoveryTest = sshTest;

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

        return discoveryTest;

    }

    public boolean ping(String ip) {

        Integer packetLoss = 100;

        try {

            Runtime runtime = Runtime.getRuntime();

            String command = "ping -c 4 " + ip;

            Process process = runtime.exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            String answer = "";

            while ((line = reader.readLine()) != null) {

                answer += line;

            }

            answer = (answer.substring(answer.indexOf("received") + 10, answer.indexOf("%"))).trim();

            packetLoss = Integer.parseInt(answer);

        } catch (Exception e) {

            return false;

        }

        if (packetLoss > 50) {

            return false;

        } else {

            return true;

        }

    }

    public boolean ssh(String ip, String username, String password) {

        boolean sshTest;

        try {

            session = jsch.getSession(username, ip, 22);

            session.setPassword(password);

            session.setConfig(config);

            session.connect();

            sshTest = true;

        } catch (JSchException e) {

            sshTest = false;

        }

        return sshTest;

    }

}
