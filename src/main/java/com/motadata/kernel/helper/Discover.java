package com.motadata.kernel.helper;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.motadata.kernel.dao.Database;

import java.io.*;
import java.util.*;

public class Discover
{
    public boolean discovery(String ip, String type)
    {
        boolean discoveryTest = ping(ip);

        try
        {
            if (type.equals("ping"))
            {
                return discoveryTest;
            }
            if (discoveryTest && type.equals("ssh"))
            {
                Database database = new Database();

                String query = "select * from credential where ip=?";

                ArrayList<Object> values = new ArrayList<>(Arrays.asList(ip));

                List<HashMap<String, String>> data = database.select(query, values);

                ArrayList<Object> credential = new ArrayList<>(Arrays.asList(ip, data.get(0).get("username"), Cipher.decode(data.get(0).get("password"))));

                SSHCommand sshCommand = new SSHCommand();

                String answer = sshCommand.runCommand(credential, "uname");

                if (answer.trim().equals("Linux"))
                {
                    discoveryTest = true;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
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

            answer = answer.substring(answer.indexOf("statistics"));

            int receivedPacket = Integer.parseInt((answer.substring(answer.indexOf("transmitted") + 13, answer.indexOf("received"))).trim());

            packetLoss = (int) ((1 - (receivedPacket / 4.0)) * 100);

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
