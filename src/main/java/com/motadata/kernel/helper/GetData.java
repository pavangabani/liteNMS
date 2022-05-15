package com.motadata.kernel.helper;

import com.motadata.kernel.bean.*;
import com.motadata.kernel.dao.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;

public class GetData
{
    private static final int PACKET_LOSS = 25;

    //GetData_From_Database

    public List<PollingMonitorBean> getAllPollingMonitor()
    {
        List<PollingMonitorBean> pollingmonitorList = null;

        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "select * from pollingmonitor";

            List<HashMap<String, String>> data = database.select(query, new ArrayList<>());

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
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
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
        return pollingmonitorList;
    }

    public List<MonitorBean> getAllMonitor()
    {
        List<MonitorBean> monitorList = null;

        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "select * from monitor";

            List<HashMap<String, String>> data = database.select(query, new ArrayList<>());

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
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
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
        return monitorList;
    }

    public List<AlertBean> getAllAlerts()
    {
        List<AlertBean> alertList = null;

        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "select * from alerts";

            List<HashMap<String, String>> data = database.select(query, new ArrayList<>());

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
                alertList = new ArrayList<>();

                for (HashMap<String, String> row : data)
                {
                    AlertBean alertBean = new AlertBean();

                    alertBean.setId(row.get("id"));

                    alertBean.setName(row.get("name"));

                    alertBean.setStatus(row.get("status"));

                    alertBean.setType(row.get("type"));

                    alertBean.setMetric(row.get("metric"));

                    alertBean.setCritical(Integer.valueOf(row.get("critical")));

                    alertBean.setWarning(Integer.valueOf(row.get("warning")));

                    alertBean.setClear(Integer.valueOf(row.get("clear")));

                    alertList.add(alertBean);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
        return alertList;
    }

    public ArrayList<Object> getDashboardData()
    {
        ArrayList<Object> dashboardData = new ArrayList<>();

        Database database = null;

        try
        {
            //1 Monitor Availability

            //QueryStart

            database = new Database();

            String query = "select count(*), availability from pollingmonitor group by availability;";

            List<HashMap<String, String>> data = database.select(query, new ArrayList<>());

            //QueryEnd

            int down = 0;

            List<Integer> availability = null;

            if (!data.isEmpty())
            {
                int up = 0, unrechable = 0, total;

                for (HashMap<String, String> row : data)
                {
                    String status = row.get("availability");

                    if (status.equals("UP"))
                    {
                        up = Integer.parseInt(row.get("count(*)"));
                    }
                    if (status.equals("DOWN"))
                    {
                        down = Integer.parseInt(row.get("count(*)"));
                    }
                    if (status.equals("Unknown"))
                    {
                        unrechable = Integer.parseInt(row.get("count(*)"));
                    }
                }
                total = up + down + unrechable;

                availability = new ArrayList<>(Arrays.asList(unrechable, up, down, total));
            }

            dashboardData.add(availability);

            //2.Monitor Group

            //QueryStart

            query = "select count(*),availability,type from pollingmonitor group by type, availability";

            data = database.select(query, new ArrayList<>());

            //QueryEnd

            List<HashMap<String, String>> monitorGroup = new ArrayList<>();

            if (!data.isEmpty())
            {
                int pingUp = 0, pingDown = 0, pingTotal, sshUp = 0, sshDown = 0, sshTotal;

                for (HashMap<String, String> row : data)
                {
                    String type = row.get("type");

                    if (type.equals("ping"))
                    {
                        if (row.get("availability").equals("UP"))
                        {
                            pingUp += Integer.parseInt(row.get("count(*)"));

                        } else if (row.get("availability").equals("DOWN"))
                        {
                            pingDown += Integer.parseInt(row.get("count(*)"));
                        }
                    } else
                    {
                        if (row.get("availability").equals("UP"))
                        {
                            sshUp += Integer.parseInt(row.get("count(*)"));

                        } else if (row.get("availability").equals("DOWN"))
                        {
                            sshDown += Integer.parseInt(row.get("count(*)"));
                        }
                    }
                }

                pingTotal = pingDown + pingUp;

                sshTotal = sshDown + sshUp;

                HashMap<String, String> ping = new HashMap<>();

                ping.put("type", "ping");

                ping.put("up", String.valueOf(pingUp));

                ping.put("down", String.valueOf(pingDown));

                ping.put("total", String.valueOf(pingTotal));

                HashMap<String, String> ssh = new HashMap<>();

                ssh.put("type", "ssh");

                ssh.put("up", String.valueOf(sshUp));

                ssh.put("down", String.valueOf(sshDown));

                ssh.put("total", String.valueOf(sshTotal));

                monitorGroup.add(ping);

                monitorGroup.add(ssh);

                dashboardData.add(monitorGroup);
            }

            //3. top 5 rtt

            query = "select ip, max(rtt) as rtt from pingdump inner join pollingmonitor on pingdump.id=pollingmonitor.id where pollingtime in (select max(pollingtime) from pingdump inner join pollingmonitor on pingdump.id=pollingmonitor.id group by pollingmonitor.ip) and rtt!=-1 group by ip order by max(rtt) desc limit 5";

            List<HashMap<String, String>> dataTopRtt = database.select(query, new ArrayList<>());

            dashboardData.add(dataTopRtt);

            // top 5 ssh cpu

            query = "select ip, max(cpu) as cpu from sshdump inner join pollingmonitor on sshdump.id=pollingmonitor.id where pollingtime in (select max(pollingtime) from sshdump inner join pollingmonitor on sshdump.id=pollingmonitor.id group by pollingmonitor.ip) and cpu!=-1 group by ip order by cpu desc limit 5";

            List<HashMap<String, String>> dataTopCpu = database.select(query, new ArrayList<>());

            dashboardData.add(dataTopCpu);

            //top 5 ssh memory

            query = "select ip, max(memory) as memory from sshdump inner join pollingmonitor on sshdump.id=pollingmonitor.id where pollingtime in (select max(pollingtime) from sshdump inner join pollingmonitor on sshdump.id=pollingmonitor.id group by pollingmonitor.ip) and memory!=-1 group by ip order by memory desc limit 5";

            List<HashMap<String, String>> dataTopMemory = database.select(query, new ArrayList<>());

            dashboardData.add(dataTopMemory);

            //top 5 ssh disk

            query = "select ip, max(disk) as disk from sshdump inner join pollingmonitor on sshdump.id=pollingmonitor.id where pollingtime in (select max(pollingtime) from sshdump inner join pollingmonitor on sshdump.id=pollingmonitor.id group by pollingmonitor.ip) and disk!=-1 group by ip order by disk desc limit 5";

            List<HashMap<String, String>> dataTopDisk = database.select(query, new ArrayList<>());

            dashboardData.add(dataTopDisk);

            // 6. monitor health

            int unreachable = 0, critical = 0, warning = 0, clear = 0;

            query = "select max(rtt) as rtt from pollingmonitor inner join pingdump on pollingmonitor.id=pingdump.id where type='ping' and pollingtime in (select max(pollingtime) from pingdump inner join pollingmonitor on pingdump.id=pollingmonitor.id group by pollingmonitor.ip) group by pingdump.id";

            List<HashMap<String, String>> pingTypeHealth = database.select(query, new ArrayList<>());

            query = "select * from alerts where type='ping'";

            List<HashMap<String, String>> criteria = database.select(query, new ArrayList<>());

            for (HashMap<String, String> row : pingTypeHealth)
            {
                if (!criteria.isEmpty())
                {
                    if (Integer.parseInt(row.get("rtt")) > Integer.parseInt(criteria.get(0).get("critical")))
                    {
                        critical++;

                    } else if (Integer.parseInt(row.get("rtt")) > Integer.parseInt(criteria.get(0).get("warning")))
                    {
                        warning++;

                    } else
                    {
                        clear++;
                    }
                }
            }

            query = "select max(cpu) as cpu from pollingmonitor inner join sshdump on pollingmonitor.id=sshdump.id where type='ssh' and pollingtime in (select max(pollingtime) from sshdump inner join pollingmonitor on sshdump.id=pollingmonitor.id group by pollingmonitor.ip) group by sshdump.id";

            List<HashMap<String, String>> sshTypeHealth = database.select(query, new ArrayList<>());

            query = "select * from alerts where type='ssh' and metric='CPU'";

            criteria = database.select(query, new ArrayList<>());

            for (HashMap<String, String> row : sshTypeHealth)
            {
                if (!criteria.isEmpty())
                {
                    if (Integer.parseInt(row.get("cpu")) > Integer.parseInt(criteria.get(0).get("critical")))
                    {
                        critical++;

                    } else if (Integer.parseInt(row.get("cpu")) > Integer.parseInt(criteria.get(0).get("warning")))
                    {
                        warning++;

                    } else if (Integer.parseInt(row.get("cpu")) > 0 && Integer.parseInt(row.get("cpu")) < Integer.parseInt(criteria.get(0).get("clear")))
                    {
                        clear++;
                    }
                }
            }

            ArrayList<Object> health = new ArrayList<>();

            health.add(down);

            health.add(critical);

            health.add(warning);

            if (clear > 1)
            {
               new Thread(()->{

                   String subject = "Alert From LiteNMS";

                   String message = "More than 1 monitor clear";

                   Mail mailer = new Mail();

                   try
                   {
                       mailer.sendPlainTextEmail(subject, message);

                       System.out.println("Email sent.");

                   } catch (Exception ex)
                   {
                       System.out.println("Failed to sent email.");

                       ex.printStackTrace();
                   }

               }).start();
            }
            health.add(clear - down);

            dashboardData.add(health);

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }

        return dashboardData;
    }

    public HashMap<String, Object> getPingStatistic(String id)
    {
        HashMap<String, Object> pingStatistic = null;

        Database database = null;

        try
        {
            pingStatistic = new HashMap<>();

            // 1. Last 24 Hours Availability

            ArrayList<Object> availability = getAvailability(id);

            pingStatistic.put("pie", availability);

            //2. Last 10 Polling Data

            database = new Database();

            String query = "select * from pingdump where id=? ORDER BY pollingtime DESC limit 10";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> barData = database.select(query, values);

            database.releaseConnection();

            if (!barData.isEmpty())
            {
                ArrayList<Object> bary = new ArrayList<>();

                ArrayList<Object> barx = new ArrayList<>();

                for (HashMap<String, String> row : barData)
                {
                    bary.add(row.get("receivepackets"));

                    barx.add(row.get("pollingtime"));
                }

                pingStatistic.put("barx", barx);

                pingStatistic.put("bary", bary);

                //3. Live Data (Matrixs)

                pingStatistic.put("matrix", new ArrayList<>(Arrays.asList(barData.get(0).get("sentpackets"), barData.get(0).get("receivepackets"), barData.get(0).get("packetloss"), barData.get(0).get("rtt"))));
            }
            //End

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
        return pingStatistic;
    }

    public HashMap<String, Object> getSshStatistic(String id)
    {
        HashMap<String, Object> sshStatistic = null;

        Database database = null;

        try
        {
            sshStatistic = new HashMap<>();

            //1. Last 24 Availability

            ArrayList<Object> availability = getAvailability(id);

            sshStatistic.put("pie", availability);

            //2. Last 10 Polling

            database = new Database();

            String query = "select * from sshdump where id=? ORDER BY pollingtime DESC limit 10";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> barData = database.select(query, values);

            database.releaseConnection();

            if (!barData.isEmpty())
            {
                ArrayList<Object> bary = new ArrayList<>();

                ArrayList<Object> barx = new ArrayList<>();

                for (HashMap<String, String> row : barData)
                {
                    bary.add(row.get("cpu"));

                    barx.add(row.get("pollingtime"));
                }

                sshStatistic.put("barx", barx);

                sshStatistic.put("bary", bary);

                //3. Live Data (Matrixs)

                sshStatistic.put("matrix", new ArrayList<>(Arrays.asList(barData.get(0).get("cpu"), barData.get(0).get("memory"), barData.get(0).get("disk"), barData.get(0).get("uptime"), barData.get(0).get("totaldisk"), barData.get(0).get("totalmemory"))));

            }

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
        return sshStatistic;
    }

    ArrayList<Object> getAvailability(String id)
    {
        ArrayList<Object> availability = null;

        Database database = null;

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

            database = new Database();

            String query = "select count(*),packetloss from pingdump where pollingtime BETWEEN '" + lastDayTimestamp + "' AND '" + currentTimeStamp + "' AND id=? group by packetloss";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> data = database.select(query, values);

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
                int pieUp = 0, pieDown = 0;

                for (HashMap<String, String> row : data)
                {
                    int packetloss = Integer.parseInt(row.get("packetloss"));

                    if (packetloss <= PACKET_LOSS)
                    {
                        pieUp += Integer.parseInt(row.get("count(*)"));

                    } else
                    {
                        pieDown += Integer.parseInt(row.get("count(*)"));
                    }
                }

                pieUp = (int) (((float) pieUp / (float) (pieDown + pieUp) * 1.0) * 100);

                pieDown = 100 - pieUp;

                availability = new ArrayList<>();

                availability.add(pieUp);

                availability.add(pieDown);
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
        return availability;
    }

    //Generate_Data

    public PollingPingBean getPingData(String ip)
    {
        BufferedReader reader = null;

        Process process = null;

        PollingPingBean pollingPingBean = new PollingPingBean();

        try
        {
            List<String> command = new ArrayList<>(Arrays.asList("ping", "-c", "4", ip));

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            process = processBuilder.start();

            //read-------------------------------------------------------------------------------------------------

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line, outputString = "";

            if (!reader.ready()) Thread.sleep(5000);

            while (reader.ready() && (line = reader.readLine()) != null) outputString += line;

            if (outputString.contains("statistics"))
            {
                outputString = outputString.substring(outputString.indexOf("statistics"));

                int sentPacket = 4;

                int receivedPacket = Integer.parseInt((outputString.substring(outputString.indexOf("transmitted") + 13, outputString.indexOf("received"))).trim());

                int packetLoss = (int) ((1 - (receivedPacket / 4.0)) * 100);

                int RTT = 0;

                if (packetLoss != 100)
                {
                    RTT = (int) Float.parseFloat((outputString.substring(outputString.lastIndexOf("=") + 1, outputString.lastIndexOf("=") + 7).trim()));
                }

                pollingPingBean.setPacketLoss(packetLoss);

                pollingPingBean.setRTT(RTT);

                pollingPingBean.setReceivePacket(receivedPacket);

                pollingPingBean.setSentPacket(sentPacket);
            } else
            {
                pollingPingBean.setPacketLoss(100);

                pollingPingBean.setRTT(-1);

                pollingPingBean.setReceivePacket(0);

                pollingPingBean.setSentPacket(4);
            }
        } catch (Exception e)
        {
            pollingPingBean.setPacketLoss(100);

            pollingPingBean.setRTT(-1);

            pollingPingBean.setReceivePacket(0);

            pollingPingBean.setSentPacket(4);

            e.printStackTrace();

        } finally
        {
            if (process != null)
            {
                process.destroy();
            }
            if (reader != null)
            {
                try
                {
                    reader.close();

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return pollingPingBean;
    }

    public PollingSshBean getSshData(String ip)
    {
        Database database = null;

        SshConnection sshConnection = null;

        PollingSshBean pollingSshBean = new PollingSshBean();

        try
        {
            //QueryStart

            database = new Database();

            String query = "select * from credential where ip=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(ip));

            List<HashMap<String, String>> data = database.select(query, values);

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
                String username = data.get(0).get("username");

                String password = Cipher.decode(data.get(0).get("password"));

                if (password == null) throw new NullPointerException();

                ArrayList<String> credential = new ArrayList<>(Arrays.asList(ip, username, password));

                String commands = "free -m | grep Mem | awk '{print $3}'\nfree -m | grep Mem | awk '{print $2}'\ndf -hT /home | grep dev | awk '{print $6}' \ndf -hT /home | grep dev | awk '{print $3}' \ntop -bn  2 | grep Cpu\nuptime -p\n";

                sshConnection = new SshConnection(credential);

                String output = sshConnection.executeCommands(commands);

                sshConnection.close();

                try
                {
                    //1.
                    output = output.substring(output.lastIndexOf("free -m | grep Mem | awk '{print $3}'") + "free -m | grep Mem | awk '{print $3}'\n".length() - 1);

                    String usedMemory = output.substring(0, output.indexOf(username + "@"));

                    //2.
                    output = output.substring(output.lastIndexOf("free -m | grep Mem | awk '{print $2}'") + "free -m | grep Mem | awk '{print $2}'\n".length() - 1);

                    String totalMemory = output.substring(0, output.indexOf(username + "@"));

                    //----------------------------------------------------------------------------------------------------------------------

                    int memoryPercentage = (int) ((Float.parseFloat(usedMemory) / Float.parseFloat(totalMemory)) * 100);

                    int totalMemoryInt = (int) Math.ceil(Integer.parseInt(totalMemory) / 1024.0);

                    pollingSshBean.setTotalMemory(String.valueOf(totalMemoryInt));

                    pollingSshBean.setMemory(memoryPercentage);

                } catch (Exception e)
                {
                    pollingSshBean.setTotalMemory("-1");

                    pollingSshBean.setMemory(-1);

                    e.printStackTrace();
                }

                try
                {
                    //3.
                    output = output.substring(output.lastIndexOf("df -hT /home | grep dev | awk '{print $6}'") + "df -hT /home | grep dev | awk '{print $6}' \n".length() - 1);

                    String usedDisk = output.substring(0, output.indexOf(username + "@"));

                    usedDisk = usedDisk.substring(0, usedDisk.indexOf("%"));

                    //4.
                    output = output.substring(output.lastIndexOf("df -hT /home | grep dev | awk '{print $3}'") + "df -hT /home | grep dev | awk '{print $3}' \n".length() - 1);

                    String totalDisk = output.substring(0, output.indexOf(username + "@"));

                    pollingSshBean.setTotalDisk(totalDisk);

                    pollingSshBean.setDisk(Integer.valueOf(usedDisk.trim()));

                } catch (Exception e)
                {
                    pollingSshBean.setTotalDisk("-1");

                    pollingSshBean.setDisk(-1);

                    e.printStackTrace();
                }

                try
                {
                    //5.
                    output = output.substring(output.lastIndexOf("top -bn  2 | grep Cpu") + "top -bn  2 | grep Cpu\n".length() - 1);

                    output = output.substring(output.indexOf("id") + 2);

                    String cpu = output.substring(output.indexOf("id") - 5, output.indexOf("id"));

                    pollingSshBean.setCpu((int) (100 - Float.parseFloat(cpu.trim())));

                } catch (Exception e)
                {
                    pollingSshBean.setCpu(-1);

                    e.printStackTrace();

                }
                try
                {
                    //6.
                    output = output.substring(output.lastIndexOf("uptime -p") + "uptime -p\n".length() - 1);

                    String upTime = output.substring(0, output.indexOf(username + "@"));

                    pollingSshBean.setUpTime(upTime);

                } catch (Exception e)
                {
                    pollingSshBean.setUpTime("-1");

                    e.printStackTrace();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }

            if (sshConnection != null)
            {
                sshConnection.close();
            }
        }
        return pollingSshBean;
    }

}
