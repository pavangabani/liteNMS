package com.motadata.kernel.helper;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.bean.PollingPingBean;
import com.motadata.kernel.bean.PollingSshBean;
import com.motadata.kernel.dao.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;

public class GetData
{
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

    public List<Integer> getDashboardData()
    {
        List<Integer> availability = null;

        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "select availability from pollingmonitor";

            List<HashMap<String, String>> data = database.select(query, new ArrayList<>());

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
                int up = 0, down = 0, unrechable = 0, total = 0;

                for (HashMap<String, String> row : data)
                {
                    if (row.get("availability").equals("UP"))
                    {
                        up++;

                    } else if (row.get("availability").equals("DOWN"))
                    {
                        down++;

                    } else if (row.get("availability").equals("Unknown"))
                    {
                        unrechable++;
                    }
                    total++;
                }
                availability = new ArrayList<>(Arrays.asList(unrechable, up, down, total));
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

            //2. Live Data (Matrixs)

            database = new Database();

            String query = "select * from pingdump where id=? ORDER BY pollingtime DESC limit 1";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> matrixData = database.select(query, values);

            if (!matrixData.isEmpty())
            {
                pingStatistic.put("matrix", new ArrayList<>(Arrays.asList(matrixData.get(0).get("sentpackets"), matrixData.get(0).get("receivepackets"), matrixData.get(0).get("packetloss"), matrixData.get(0).get("rtt"))));

            }

            //3. Last 10 Polling Data

            query = "select * from pingdump where id=? ORDER BY pollingtime DESC limit 10";

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

            //2. Live Data (Matrixs)

            database = new Database();

            String query = "select * from sshdump where id=? ORDER BY pollingtime DESC limit 1";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> matrixData = database.select(query, values);

            if (!matrixData.isEmpty())
            {
                sshStatistic.put("matrix", new ArrayList<>(Arrays.asList(matrixData.get(0).get("cpu"), matrixData.get(0).get("memory"), matrixData.get(0).get("disk"), matrixData.get(0).get("uptime"), matrixData.get(0).get("totaldisk"), matrixData.get(0).get("totalmemory"))));

            }

            //3. Last 10 Polling

            query = "select * from sshdump where id=? ORDER BY pollingtime DESC limit 10";

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

            String query = "select * from pingdump where pollingtime BETWEEN '" + lastDayTimestamp + "' AND '" + currentTimeStamp + "' AND id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> data = database.select(query, values);

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
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

            //-----------------------------------------------------------------------------------------------------

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line, outputString = "";

            while ((line = reader.readLine())!=null)
            {
                outputString += line;
            }

            //ManipulateData-------------------------------------------------------------------------------------------

            if (!outputString.contains("statistics"))
            {
                pollingPingBean.setPacketLoss(100);

                pollingPingBean.setRTT(-1);

                pollingPingBean.setReceivePacket(0);

                pollingPingBean.setSentPacket(4);

            } else
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

                //------------------------------------------------------------------------------------------

                pollingPingBean.setPacketLoss(packetLoss);

                pollingPingBean.setRTT(RTT);

                pollingPingBean.setReceivePacket(receivedPacket);

                pollingPingBean.setSentPacket(sentPacket);
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

                String password = data.get(0).get("password");

                ArrayList<String> credential = new ArrayList<>(Arrays.asList(ip, username, Cipher.decode(password)));

                List<String> commands = new ArrayList<>();

                commands.add("free -m | grep Mem | awk '{print $3}'\n");

                commands.add("free -m | grep Mem | awk '{print $2}'\n");

                commands.add("df -hT /home | grep dev | awk '{print $6}' \n");

                commands.add("df -hT /home | grep dev | awk '{print $3}' \n");

                commands.add("mpstat | grep all\n");

                commands.add("uptime -p\n");

                sshConnection = new SshConnection(credential);

                String output = sshConnection.executeCommands(commands);

                sshConnection.close();

                try
                {
                    //1.
                    output = output.substring(output.lastIndexOf("free -m | grep Mem | awk '{print $3}'") + commands.get(0).length() - 1);

                    String usedMemory = output.substring(0, output.indexOf(username + "@"));

                    //2.
                    output = output.substring(output.lastIndexOf("free -m | grep Mem | awk '{print $2}'") + commands.get(1).length() - 1);

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
                    output = output.substring(output.lastIndexOf("df -hT /home | grep dev | awk '{print $6}'") + commands.get(2).length() - 1);

                    String usedDisk = output.substring(0, output.indexOf(username + "@"));

                    usedDisk = usedDisk.substring(0, usedDisk.indexOf("%"));

                    //4.
                    output = output.substring(output.lastIndexOf("df -hT /home | grep dev | awk '{print $3}'") + commands.get(3).length() - 1);

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
                    output = output.substring(output.lastIndexOf("mpstat | grep all") + commands.get(4).length() - 1);

                    String cpu = output.substring(output.indexOf(username + "@") - 5, output.indexOf(username + "@") - 3);

                    pollingSshBean.setCpu(100 - Integer.parseInt(cpu.trim()));

                } catch (Exception e)
                {
                    pollingSshBean.setCpu(-1);

                    e.printStackTrace();

                }
                try
                {
                    //6.
                    output = output.substring(output.lastIndexOf("uptime -p") + commands.get(5).length() - 1);

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
