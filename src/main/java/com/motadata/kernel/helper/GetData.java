package com.motadata.kernel.helper;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.bean.PollingPingBean;
import com.motadata.kernel.bean.PollingSshBean;
import com.motadata.kernel.dao.Database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;

public class GetData
{

    //GetData_From_Database

    public List<PollingMonitorBean> getAllPollingMonitor()
    {
        List<PollingMonitorBean> pollingmonitorList = null;

        try
        {
            //QueryStart

            Database database = new Database();

            String query = "select * from pollingmonitor";

            List<HashMap<String, String>> data = database.select(query, new ArrayList());

            //QueryEnd

            pollingmonitorList = new ArrayList<>();

            for (HashMap<String, String> row : data)
            {
                PollingMonitorBean pollingmonitorBean = new PollingMonitorBean();

                pollingmonitorBean.setId(row.get("id"));

                pollingmonitorBean.setName(row.get("name"));

                pollingmonitorBean.setIp(row.get("ip"));

                pollingmonitorBean.setType(row.get("type"));

                pollingmonitorBean.setTag(row.get("tag"));

                pollingmonitorBean.setAvailability(row.get("availability"));

                pollingmonitorList.add(pollingmonitorBean);
            }
            database.releaseConnection();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return pollingmonitorList;
    }

    public List<MonitorBean> getAllMonitor()
    {
        List<MonitorBean> monitorList = null;

        try
        {
            //QueryStart

            Database database = new Database();

            String query = "select * from monitor";

            List<HashMap<String, String>> data = database.select(query, new ArrayList());

            //QueryEnd

            monitorList = new ArrayList<>();

            for (HashMap<String, String> row : data)
            {
                MonitorBean monitorBean = new MonitorBean();

                monitorBean.setId(row.get("id"));

                monitorBean.setName(row.get("name"));

                monitorBean.setIp(row.get("ip"));

                monitorBean.setType(row.get("type"));

                monitorBean.setTag(row.get("tag"));

                monitorList.add(monitorBean);
            }
            database.releaseConnection();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return monitorList;
    }

    public List<Integer> getDashboardData()
    {
        List<Integer> availability = null;

        try
        {
            //QueryStart

            Database database = new Database();

            String query = "select availability from pollingmonitor";

            List<HashMap<String, String>> data = database.select(query, new ArrayList());

            //QueryEnd

            int up = 0, down = 0, unrechable = 0, total = 0;

            for (HashMap<String, String> row : data)
            {
                if (row.get("availability").equals("UP"))
                {
                    up++;
                }
                else if (row.get("availability").equals("DOWN"))
                {
                    down++;
                }
                else if (row.get("availability").equals("Unknown"))
                {
                    unrechable++;
                }
                total++;
            }
            availability = new ArrayList<>(Arrays.asList(unrechable, up, down, total));

            database.releaseConnection();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return availability;
    }

    public HashMap<String, Object> getPingStatistic(String id)
    {
        HashMap<String, Object> pingStatistic = null;

        try
        {
            Database database = new Database();

            pingStatistic = new HashMap<>();

            // 1. Last 24 Hours Availability

            ArrayList<Object> availability = getAvailability(id);

            pingStatistic.put("pie", availability);

            //2. Live Data (Matrixs)

            ArrayList<Object> values = new ArrayList(Arrays.asList(id));

            String query = "select * from pingdump where id=? ORDER BY pollingtime DESC limit 1";

            List<HashMap<String, String>> matrixData = database.select(query, values);

            pingStatistic.put("matrix", new ArrayList(Arrays.asList(matrixData.get(0).get("sentpackets"), matrixData.get(0).get("receivepackets"), matrixData.get(0).get("packetloss"), matrixData.get(0).get("rtt"))));

            //3. Last 10 Polling Data

            query = "select * from pingdump where id=? ORDER BY pollingtime DESC limit 10";

            List<HashMap<String, String>> barData = database.select(query, values);

            ArrayList<Object> bary = new ArrayList();

            ArrayList<Object> barx = new ArrayList();

            for (HashMap<String, String> row : barData)
            {
                bary.add(row.get("receivepackets"));

                barx.add(row.get("pollingtime"));
            }

            pingStatistic.put("barx", barx);

            pingStatistic.put("bary", bary);

            //End

            database.releaseConnection();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return pingStatistic;
    }

    public HashMap<String, Object> getSshStatistic(String id)
    {

        HashMap<String, Object> sshStatistic = null;

        try
        {
            Database database = new Database();

            sshStatistic = new HashMap<>();

            //1. Last 24 Availability

            ArrayList<Object> availability = getAvailability(id);

            sshStatistic.put("pie", availability);

            //2. Live Data (Matrixs)

            String query = "select * from sshdump where id=? ORDER BY pollingtime DESC limit 1";

            ArrayList<Object> values = new ArrayList(Arrays.asList(id));

            List<HashMap<String, String>> matrixData = database.select(query, values);

            sshStatistic.put("matrix", new ArrayList(Arrays.asList(matrixData.get(0).get("cpu"), matrixData.get(0).get("memory"), matrixData.get(0).get("disk"), matrixData.get(0).get("uptime"), matrixData.get(0).get("totaldisk"), matrixData.get(0).get("totalmemory"))));

            //3. Last 10 Polling

            query = "select * from sshdump where id=? ORDER BY pollingtime DESC limit 10";

            List<HashMap<String, String>> barData = database.select(query, values);

            ArrayList<Object> bary = new ArrayList();

            ArrayList<Object> barx = new ArrayList();

            for (HashMap<String, String> row : barData)
            {
                bary.add(row.get("cpu"));

                barx.add(row.get("pollingtime"));
            }

            sshStatistic.put("barx", barx);

            sshStatistic.put("bary", bary);

            database.releaseConnection();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return sshStatistic;
    }

    ArrayList<Object> getAvailability(String id)
    {
        ArrayList<Object> availability = null;

        try
        {
            Calendar calendar = Calendar.getInstance();

            Date date = new Date();

            calendar.setTime(date);

            calendar.add(Calendar.DATE, -1);

            Date lastDay = calendar.getTime();

            Timestamp lastDayTimestamp = new Timestamp(lastDay.getTime());

            Timestamp currentTimeStamp = new Timestamp(date.getTime());

            //QueryStart

            Database database = new Database();

            ArrayList<Object> values = new ArrayList(Arrays.asList(id));

            String query = "select * from pingdump where pollingtime BETWEEN '" + lastDayTimestamp + "' AND '" + currentTimeStamp + "' AND id=?";

            List<HashMap<String, String>> data = database.select(query, values);

            //QueryEnd

            int pingSuccess = 0;

            int pingTotal = 0;

            for (HashMap<String, String> row : data)
            {

                pingTotal += 1;

                if (Integer.parseInt(row.get("packetloss")) < 50)
                {

                    pingSuccess += 1;

                }

            }

            int pieUp = (int) ((float) pingSuccess / (float) pingTotal * 100);

            int pieDown = 100 - pieUp;

            availability = new ArrayList();

            availability.add(pieUp);

            availability.add(pieDown);

            database.releaseConnection();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return availability;
    }

    //Generate_Data

    public PollingPingBean getPingData(String ip)
    {
        PollingPingBean pollingPingBean = new PollingPingBean();

        try
        {
            //GetData

            List<String> command=new ArrayList<>(Arrays.asList("ping","-c", "4",ip));

            ProcessBuilder processBuilder=new ProcessBuilder(command);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            String outputString = "";

            while ((line = reader.readLine()) != null)
            {
                outputString += line;
            }

            //ManipulateData

            if (outputString.indexOf("statistics") == -1)
            {
                pollingPingBean.setPacketLoss(100);

                pollingPingBean.setRTT(-1);

                pollingPingBean.setReceivePacket(0);

                pollingPingBean.setSentPacket(4);

            } else
            {
                outputString = outputString.substring(outputString.indexOf("statistics"));

                //-----------------------------------------------------------------------------------------

                int sentPacket = 4;

                int receivedPacket = Integer.parseInt((outputString.substring(outputString.indexOf("transmitted") + 13, outputString.indexOf("received"))).trim());

                int packetLoss = (int) ((1 - (receivedPacket / 4.0)) * 100);

                int RTT = 0;

                if (packetLoss != 100)
                {
                    RTT = (int) Float.parseFloat((outputString.substring(outputString.lastIndexOf("=") + 1, outputString.lastIndexOf("=") + 7).trim()));
                }

                //------------------------------------------------------------------------------------------

                pollingPingBean.setPacketLoss(packetLoss);

                pollingPingBean.setRTT(RTT);

                pollingPingBean.setReceivePacket(receivedPacket);

                pollingPingBean.setSentPacket(sentPacket);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return pollingPingBean;
    }

    public PollingSshBean getSshData(String ip)
    {
        PollingSshBean pollingSshBean = null;

        try
        {
            pollingSshBean = new PollingSshBean();

            //QueryStart

            Database database = new Database();

            String query = "select * from credential where ip=?";

            ArrayList<Object> values = new ArrayList(Arrays.asList(ip));

            List<HashMap<String, String>> data = database.select(query, values);

            //QueryEnd

            String ussername = data.get(0).get("username");

            String password = data.get(0).get("password");

            //------------------------------------------------------------

            Properties config = new Properties();

            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();

            Session session = jsch.getSession(ussername, ip, 22);

            session.setPassword(Cipher.decode(password));

            session.setConfig(config);

            session.connect();

            //----------------------------------------------------------------------

            Channel channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(
                    "free -m | grep Mem | awk '{print $2}';" +
                            "free -m | grep Mem | awk '{print $3}';" +
                            "mpstat | grep IST;" +
                            "df -hT /home | grep dev | awk '{print $6}';" +
                            "uptime -p;" +
                            "df -hT /home | grep dev | awk '{print $3}'"
            );

            channel.connect();

            //GetData-----------------------------------------------------------------

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            String temp;

            String answer = "";

            while ((temp = bufferedReader.readLine()) != null)
            {
                answer += temp + "\n";
            }

            String[] ansArray = answer.split("\n");

            //-----------------------------------------------------------------------------

            int totalmemory = (Integer.parseInt(ansArray[0].trim())/1024)+1;

            int memory = (int) ((Float.parseFloat(ansArray[1].trim()) / Float.parseFloat(ansArray[0].trim())) * 100);

            int cpu = 100 - (int) Float.parseFloat(ansArray[3].substring(ansArray[3].length() - 6).trim());

            int disk = Integer.parseInt(ansArray[4].substring(0, ansArray[4].indexOf("%")));

            String upTime = ansArray[5].substring(ansArray[5].indexOf("up") + 3).trim();

            String totalDisk = ansArray[6].trim();

            //--------------------------------------------------------------------------------

            pollingSshBean.setMemory(memory);

            pollingSshBean.setCpu(cpu);

            pollingSshBean.setDisk(disk);

            pollingSshBean.setUpTime(upTime);

            pollingSshBean.setTotalDisk(totalDisk);

            pollingSshBean.setTotalMemory(totalmemory);

            database.releaseConnection();

        } catch (Exception e)
        {
            pollingSshBean.setTotalMemory(-1);

            pollingSshBean.setMemory(-1);

            pollingSshBean.setCpu(-1);

            pollingSshBean.setDisk(-1);

            pollingSshBean.setUpTime("-1");

            pollingSshBean.setTotalDisk("-1");

            e.printStackTrace();
        }

        return pollingSshBean;
    }
}
