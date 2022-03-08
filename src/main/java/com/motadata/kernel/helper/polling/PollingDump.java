package com.motadata.kernel.helper.polling;

import com.jcraft.jsch.*;
import com.motadata.kernel.bean.PollingPingBean;
import com.motadata.kernel.bean.PollingSshBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.Discover;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class PollingDump {

    PollingPingBean getPingData(String ip) {

        PollingPingBean pollingPingBean = new PollingPingBean();

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

            if (outputString.indexOf("statistics") == -1) {

                pollingPingBean.setPacketLoss(100);

                pollingPingBean.setRTT(-1);

                pollingPingBean.setReceivePacket(0);

                pollingPingBean.setSentPacket(4);
            } else {
                outputString = outputString.substring(outputString.indexOf("statistics"));

                Integer RTT = Integer.parseInt((outputString.substring(outputString.indexOf("time") + 5, outputString.indexOf("ms"))).trim());

                Integer receivedPacket = Integer.parseInt((outputString.substring(outputString.indexOf("transmitted") + 13, outputString.indexOf("received"))).trim());

                int packetLoss = (int) ((1 - (receivedPacket / 4.0)) * 100);

                Integer sentPacket = 4;

                pollingPingBean.setPacketLoss(packetLoss);

                pollingPingBean.setRTT(RTT);

                pollingPingBean.setReceivePacket(receivedPacket);

                pollingPingBean.setSentPacket(sentPacket);

            }


        } catch (Exception e) {

            e.printStackTrace();

        }

        return pollingPingBean;

    }

    PollingSshBean getSshData(String ip) {

        PollingSshBean pollingSshBean = new PollingSshBean();

        try {

            Database database = new Database();

            ArrayList attributes = new ArrayList(Arrays.asList("ip"));

            ArrayList values = new ArrayList(Arrays.asList(ip));

            ResultSet resultSet = database.select("credential", attributes, values);

            resultSet.next();

            String ussername = resultSet.getString(2);

            String password = resultSet.getString(3);

            Properties config = new Properties();

            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();

            Session session = jsch.getSession(ussername, ip, 22);

            session.setPassword(password);

            session.setConfig(config);

            session.connect();

            Channel channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(
                    "free -m | grep Mem | awk '{print $2}';" +
                            "free -m | grep Mem | awk '{print $3}';" +
                            "mpstat | grep all | awk {'print $4'};" +
                            "df -hT /home | grep dev | awk '{print $6}';" +
                            "uptime -p");

            InputStream in = channel.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            channel.connect();

            String temp = "";

            String answer = "";

            while ((temp = bufferedReader.readLine()) != null) {

                answer += temp + "\n";

            }

            String[] ansArray = answer.split("\n");

            int memory = (int) ((Float.valueOf(ansArray[1].trim()) / Float.valueOf(ansArray[0].trim())) * 100);

            int cpu = (int) Float.parseFloat(ansArray[2].trim());

            int disk = Integer.parseInt(ansArray[3].substring(0, ansArray[3].indexOf("%")));

            String upTime = ansArray[4].substring(ansArray[4].indexOf("up") + 3).trim();

            pollingSshBean.setMemory(memory);

            pollingSshBean.setCpu(cpu);

            pollingSshBean.setDisk(disk);

            pollingSshBean.setUpTime(upTime);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return pollingSshBean;

    }

    public HashMap<String, Object> getPingStatistic(String id) {

        HashMap<String, Object> pingStatistic = new HashMap<>();

        Database database = new Database();

        ArrayList attributes = new ArrayList(Arrays.asList("id"));

        ArrayList values = new ArrayList(Arrays.asList(id));

        try {

            ArrayList availability = getAvailability(id);

            pingStatistic.put("pie", availability);

            String additionalcondition = " ORDER BY pollingtime DESC limit 1";

            ResultSet resultSetMatrix = database.select("pingdump", attributes, values, additionalcondition, true);

            resultSetMatrix.next();

            pingStatistic.put("matrix", new ArrayList(Arrays.asList(resultSetMatrix.getInt(2), resultSetMatrix.getInt(3), resultSetMatrix.getInt(4), resultSetMatrix.getInt(5))));

            additionalcondition = " ORDER BY pollingtime DESC limit 10";

            ResultSet resultSetBar = database.select("pingdump", attributes, values, additionalcondition, true);

            ArrayList<Integer> bary = new ArrayList();

            ArrayList<Timestamp> barx = new ArrayList();

            while (resultSetBar.next()) {

                bary.add(resultSetBar.getInt(3));

                barx.add(resultSetBar.getTimestamp(6));

            }

            pingStatistic.put("barx", barx);

            pingStatistic.put("bary", bary);

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return pingStatistic;

    }

    public HashMap<String, Object> getSshStatistic(String id) {

        Database database = new Database();

        HashMap<String, Object> sshStatistic = new HashMap<>();

        try {

            ArrayList availability = getAvailability(id);

            sshStatistic.put("pie", availability);

            String additionalcondition = " ORDER BY pollingtime DESC limit 1";

            ArrayList attributes = new ArrayList(Arrays.asList("id"));

            ArrayList values = new ArrayList(Arrays.asList(id));

            ResultSet resultSetMatrix = database.select("sshdump", attributes, values, additionalcondition, true);

            resultSetMatrix.next();

            sshStatistic.put("matrix", new ArrayList(Arrays.asList(resultSetMatrix.getInt(2), resultSetMatrix.getInt(3), resultSetMatrix.getInt(4), resultSetMatrix.getString(5))));

            additionalcondition = " ORDER BY pollingtime DESC limit 10";

            ResultSet resultSetBar = database.select("sshdump", attributes, values, additionalcondition, true);

            ArrayList<Integer> bary = new ArrayList();

            ArrayList<Timestamp> barx = new ArrayList();

            while (resultSetBar.next()) {

                bary.add(resultSetBar.getInt(2));

                barx.add(resultSetBar.getTimestamp(6));

            }

            sshStatistic.put("barx", barx);

            sshStatistic.put("bary", bary);

            return sshStatistic;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return sshStatistic;

    }

    ArrayList getAvailability(String id) {

        ArrayList availability = new ArrayList();

        try {

            Database database = new Database();

            Calendar calendar = Calendar.getInstance();

            Date date = new Date();

            calendar.setTime(date);

            calendar.add(Calendar.DATE, -1);

            Date lastDay = calendar.getTime();

            Timestamp lastDayTimestamp = new Timestamp(lastDay.getTime());

            Timestamp currentTimeStamp = new Timestamp(date.getTime());

            String additionalcondition = "pollingtime BETWEEN '" + lastDayTimestamp + "' AND '" + currentTimeStamp + "'";

            ArrayList attributes = new ArrayList(Arrays.asList("id"));

            ArrayList values = new ArrayList(Arrays.asList(id));

            ResultSet resultSet = database.select("pingdump", attributes, values, additionalcondition, false);

            int pingSuccess = 0;

            int pingTotal = 0;

            while (resultSet.next()) {

                pingTotal += 1;

                if (resultSet.getInt(4) < 50) {

                    pingSuccess += 1;

                }

            }

            int pieUp = (int) ((float) pingSuccess / (float) pingTotal) * 100;

            int pieDown = 100 - pieUp;

            availability.add(pieUp);

            availability.add(pieDown);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return availability;

    }

    public void refreshAvailality(String id,int packetLoss) {

        Database database=new Database();

        ArrayList conditionAttributes=new ArrayList(Arrays.asList("id"));

        ArrayList conditionValues=new ArrayList(Arrays.asList(id));

        if(packetLoss<50){

            ArrayList attributes=new ArrayList(Arrays.asList("availability"));

            ArrayList values=new ArrayList(Arrays.asList("UP"));

            database.update("pollingmonitor",attributes,values,conditionAttributes,conditionValues);
        }else {

            ArrayList attributes=new ArrayList(Arrays.asList("availability"));

            ArrayList values=new ArrayList(Arrays.asList("DOWN"));

            database.update("pollingmonitor",attributes,values,conditionAttributes,conditionValues);

        }

    }
}
