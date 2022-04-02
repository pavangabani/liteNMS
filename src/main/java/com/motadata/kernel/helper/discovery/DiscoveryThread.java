package com.motadata.kernel.helper.discovery;

import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.Cipher;
import com.motadata.kernel.helper.SshConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class DiscoveryThread extends RecursiveAction
{
    String id;

    public DiscoveryThread(String id)
    {
        this.id = id;
    }

    @Override
    protected void compute()
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "select * from monitor where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> data = database.select(query, values);

            //QueryEnd

            if (!data.isEmpty())
            {
                String ip=data.get(0).get("ip");

                String type=data.get(0).get("type");

                boolean discoveryTest = ping(ip);

                if (discoveryTest && type.equals("ssh"))
                {
                    discoveryTest = sshTypeTest(ip);
                }

                if (discoveryTest)
                {
                    //QueryStart

                    query = "insert into pollingmonitor (id,name,ip,type,tag,availability) values(?,?,?,?,?,?)";

                    values = new ArrayList<>(Arrays.asList(id, data.get(0).get("name"), data.get(0).get("ip"), data.get(0).get("type"), data.get(0).get("tag"), "Unknown"));

                    int affectedRow = database.update(query, values);

                    //QueryEnd

                    if (affectedRow == -1)
                    {
                        System.out.println("Monitor already added");
                    }
                    else if (affectedRow > 0)
                    {
                        System.out.println("Monitor added");

                    } else
                    {
                        System.out.println("Monitor not added");
                    }
                } else
                {
                    System.out.println("ping fails!");
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
    }

    public Boolean sshTypeTest(String ip)
    {
        boolean sshTypeTest = false;

        SshConnection sshConnection = null;

        Database database = null;

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
                //credential

                String password = Cipher.decode(data.get(0).get("password"));

                if (password == null) throw new NullPointerException();

                ArrayList<String> credential = new ArrayList<>(Arrays.asList(ip, data.get(0).get("username"), password));

                sshConnection = new SshConnection(credential);

                //command

                ArrayList<String> commands = new ArrayList<>();

                commands.add("uname\n");

                //execute

                String output = sshConnection.executeCommands(commands);

                sshConnection.close();

                //check

                String monitorType = output.substring(output.lastIndexOf("uname") + commands.get(0).length() - 1, output.lastIndexOf("uname") + commands.get(0).length() + 4);

                if (monitorType.trim().equals("Linux"))
                {
                    sshTypeTest = true;
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
        return sshTypeTest;
    }

    public boolean ping(String ip)
    {
        int packetLoss = 100;

        Process process = null;

        BufferedReader reader = null;

        try
        {
            List<String> command = new ArrayList<>(Arrays.asList("ping", "-c", "4", ip));

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            process = processBuilder.start();

            //read

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line, answer = "";

            if (!reader.ready())
            {
                Thread.sleep(5000);
            }

            while (reader.ready() && (line = reader.readLine()) != null)
            {
                answer += line;
            }

            //check

            if (answer.contains("statistics"))
            {
                answer = answer.substring(answer.indexOf("statistics"));

                int receivedPacket = Integer.parseInt((answer.substring(answer.indexOf("transmitted") + 13, answer.indexOf("received"))).trim());

                packetLoss = (int) ((1 - (receivedPacket / 4.0)) * 100);
            }

        } catch (Exception e)
        {
            e.printStackTrace();

            return false;

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
        return packetLoss <= 25;
    }

}
