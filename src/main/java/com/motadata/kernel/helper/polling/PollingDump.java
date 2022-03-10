package com.motadata.kernel.helper.polling;

import com.jcraft.jsch.*;
import com.motadata.kernel.bean.PollingPingBean;
import com.motadata.kernel.bean.PollingSshBean;
import com.motadata.kernel.dao.Database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;

public class PollingDump {

    PollingPingBean getPingData(String ip) {

        PollingPingBean pollingPingBean = new PollingPingBean();

        try {

            Runtime runtime = Runtime.getRuntime();

            String command = "ping -c 4 " + ip;

            Process process = runtime.exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            String outputString = "";

            while ((line = reader.readLine()) != null) {

                outputString += line;

            }

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

            ArrayList values = new ArrayList(Arrays.asList(ip));

            String query = "select * from credential where ip=?";

            List<HashMap<String, String>> data = Database.select(query, values);

            String ussername = data.get(0).get("username");

            String password = data.get(0).get("password");

            byte[] passwordBytes = Base64.getDecoder().decode(password);

            String decodePassword = new String(passwordBytes);

            Properties config = new Properties();

            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();

            Session session = jsch.getSession(ussername, ip, 22);

            session.setPassword(decodePassword);

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

        ArrayList values = new ArrayList(Arrays.asList(id));

        ArrayList availability = getAvailability(id);

        pingStatistic.put("pie", availability);

        String query = "select * from pingdump where id=? ORDER BY pollingtime DESC limit 1";

        List<HashMap<String, String>> matrixData = Database.select(query, values);

        pingStatistic.put("matrix", new ArrayList(Arrays.asList(matrixData.get(0).get("sentpackets"), matrixData.get(0).get("receivepackets"), matrixData.get(0).get("packetloss"), matrixData.get(0).get("rtt"))));

        query = "select * from pingdump where id=? ORDER BY pollingtime DESC limit 10";

        List<HashMap<String, String>> barData = Database.select(query, values);

        ArrayList bary = new ArrayList();

        ArrayList barx = new ArrayList();

        for (HashMap<String, String> row : barData) {

            bary.add(row.get("recrivepackets"));

            barx.add(row.get("pollingtime"));

        }

        pingStatistic.put("barx", barx);

        pingStatistic.put("bary", bary);

        return pingStatistic;

    }

    public HashMap<String, Object> getSshStatistic(String id) {

        HashMap<String, Object> sshStatistic = new HashMap<>();

        ArrayList values = new ArrayList(Arrays.asList(id));

        ArrayList availability = getAvailability(id);

        sshStatistic.put("pie", availability);

        String query = "select * from sshdump where id=? ORDER BY pollingtime DESC limit 1";

        List<HashMap<String, String>> matrixData = Database.select(query, values);

        sshStatistic.put("matrix", new ArrayList(Arrays.asList(matrixData.get(0).get("cpu"), matrixData.get(0).get("memory"), matrixData.get(0).get("disk"), matrixData.get(0).get("uptime"))));

        query = "select * from sshdump where id=? ORDER BY pollingtime DESC limit 10";

        List<HashMap<String, String>> barData = Database.select(query, values);

        ArrayList bary = new ArrayList();

        ArrayList barx = new ArrayList();

        for (HashMap<String, String> row : barData) {

            bary.add(row.get("cpu"));

            barx.add(row.get("pollingtime"));

        }

        sshStatistic.put("barx", barx);

        sshStatistic.put("bary", bary);

        return sshStatistic;

    }

    ArrayList getAvailability(String id) {

        ArrayList availability = new ArrayList();

        try {

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

            String query="select * from pingdump where pollingtime BETWEEN '" + lastDayTimestamp + "' AND '" + currentTimeStamp + "' AND id=?";

            List<HashMap<String,String>> data=Database.select(query,values);

            int pingSuccess = 0;

            int pingTotal = 0;

            for (HashMap<String,String> row:data){

                pingTotal += 1;

                if (Integer.parseInt(row.get("packetloss"))< 50) {

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

    public void refreshAvailality(String id, int packetLoss) {

        if (packetLoss < 50) {

            ArrayList values = new ArrayList(Arrays.asList("UP",id));

            String query="update pollingmonitor set availability=? where id=?";

            Database.update(query,values);

        } else {

            ArrayList values = new ArrayList(Arrays.asList("DOWN",id));

            String query="update pollingmonitor set availability=? where id=?";

            Database.update(query,values);

        }

    }
}
