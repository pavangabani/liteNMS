package com.motadata.kernel.helper;

import com.motadata.kernel.dao.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DiscoveryThread extends RecursiveTask<Boolean>
{
    String ip;

    String type;

    public DiscoveryThread(String ip, String type)
    {
        this.ip = ip;

        this.type = type;
    }

    @Override
    protected Boolean compute()
    {
        SshConnection sshConnection = null;

        Database database = null;

        boolean discoveryTest = ping(ip);

        try
        {
            if (type.equals("ping"))
            {
                return discoveryTest;
            }
            if (discoveryTest && type.equals("ssh"))
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

                    ArrayList<String> credential = new ArrayList<>(Arrays.asList(ip, data.get(0).get("username"), Cipher.decode(data.get(0).get("password"))));

                    sshConnection = new SshConnection(credential);

                    //command

                    ArrayList<String> commands = new ArrayList<>();

                    commands.add("uname\n");

                    //execute

                    String output = sshConnection.executeCommands(commands);

                    sshConnection.close();

                    //check

                    String monitorType = output.substring(output.lastIndexOf("uname") + commands.get(0).length() - 1, output.lastIndexOf("uname") + commands.get(0).length() + 4);

                    if (!monitorType.trim().equals("Linux"))
                    {
                        discoveryTest = false;
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();

            discoveryTest = false;

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
        return discoveryTest;
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

            while ((line = reader.readLine()) != null)
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
