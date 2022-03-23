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
        boolean discoveryTest = ping(ip);

        Database database = new Database();

        SshConnection sshConnection = null;

        try
        {
            if (type.equals("ping"))
            {
                return discoveryTest;
            }
            if (discoveryTest && type.equals("ssh"))
            {
                //QueryStart

                String query = "select * from credential where ip=?";

                ArrayList<Object> values = new ArrayList<>(Arrays.asList(ip));

                List<HashMap<String, String>> data = database.select(query, values);

                //QueryEnd

                if (data.size() > 0)
                {
                    ArrayList<String> credential = new ArrayList<String>(Arrays.asList(ip, data.get(0).get("username"), Cipher.decode(data.get(0).get("password"))));

                    sshConnection = new SshConnection(credential);

                    ArrayList<String> commands = new ArrayList<>();

                    commands.add("uname\n");

                    String output = sshConnection.executeCommands(commands);

                    type = output.substring(output.lastIndexOf("uname") + commands.get(0).length() - 1, output.lastIndexOf("uname") + commands.get(0).length() + 4);

                    if (type.trim().equals("Linux"))
                    {
                        discoveryTest = true;
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            database.releaseConnection();

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

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line, answer = "";

            while ((line = reader.readLine()) != null)
            {
                answer += line;
            }
            if (answer.contains("statistics"))
            {
                answer = answer.substring(answer.indexOf("statistics"));

                int receivedPacket = Integer.parseInt((answer.substring(answer.indexOf("transmitted") + 13, answer.indexOf("received"))).trim());

                packetLoss = (int) ((1 - (receivedPacket / 4.0)) * 100);
            }

        } catch (Exception e)
        {
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
        if (packetLoss < 50)
        {
            return true;
        }
        return false;
    }

}
