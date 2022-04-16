package com.motadata.kernel.helper.autodiscovery;

import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.discovery.ServerEndPoint;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class AutoDiscover extends RecursiveAction
{

    String ip;

    String sessionId;

    public AutoDiscover(String ip, String sessionId)
    {
        this.ip = ip;

        this.sessionId = sessionId;
    }

    @Override
    protected void compute()
    {
        Database database = null;

        try
        {
            boolean pingTest = ping(ip);

            System.out.println("ping " + ip);

            if (pingTest)
            {
                System.out.println("ping success" + ip);

                String name = getMacAddress(ip);

                if (name.length() < 20 && name.length() > 1)
                {
                    database = new Database();

                    String query = "insert into monitor (name,ip,type,tag) values(?,?,?,?)";

                    ArrayList<Object> values = new ArrayList<>(Arrays.asList(name, ip, "ping", "auto_discovered"));

                    database.update(query, values);

                    ServerEndPoint.send("+1"+ip + " Discovered", sessionId);
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

            if (!reader.ready()) Thread.sleep(5000);

            while (reader.ready() && (line = reader.readLine()) != null) answer += line;

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

    public String getMacAddress(String ip) throws IOException
    {
        Process process = null;

        BufferedReader reader = null;

        String macAddress = "";

        try
        {
            List<String> command = new ArrayList<>(Arrays.asList("arp", "-a"));

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            process = processBuilder.start();

            //read

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line, answer = "";

            if (!reader.ready()) Thread.sleep(5000);

            while (reader.ready() && (line = reader.readLine()) != null) answer += line;

            if (answer.contains(ip))
            {
                answer = answer.substring(answer.indexOf(ip) + ip.length());

                macAddress = answer.substring(answer.indexOf("at") + "at".length(), answer.indexOf("["));

                macAddress.trim();
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return macAddress;
    }
}
