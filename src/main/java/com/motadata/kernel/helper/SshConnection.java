package com.motadata.kernel.helper;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class SshConnection
{
    private Session session;

    private ChannelShell channel;

    private final String username;

    private final String password;

    private final String hostname;

    SshConnection(ArrayList<String> credential)
    {
        hostname = credential.get(0);

        username = credential.get(1);

        password = credential.get(2);
    }

    private Session getSession()
    {
        if (session == null || !session.isConnected())
        {
            session = connect(hostname, username, password);
        }
        return session;
    }

    private Channel getChannel()
    {
        try
        {
            session = getSession();

            if (session != null && channel == null)
            {
                channel = (ChannelShell) session.openChannel("shell");

                channel.connect(10000);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return channel;
    }

    private Session connect(String hostname, String username, String password)
    {
        JSch jSch = new JSch();

        try
        {
            Properties config = new Properties();

            config.put("StrictHostKeyChecking", "no");

            session = jSch.getSession(username, hostname, 22);

            session.setConfig(config);

            session.setPassword(password);

            session.connect(10000);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return session;
    }

    private void sendCommands(Channel channel, List<String> commands)
    {
        BufferedWriter bufferedWriter = null;

        try
        {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(channel.getOutputStream()));

            for (String command : commands)
            {
                bufferedWriter.write(command);
            }
            bufferedWriter.write("exit\n");

            bufferedWriter.flush();

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            try
            {
                if (bufferedWriter != null)
                {
                    bufferedWriter.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String readChannelOutput(Channel channel) throws IOException
    {
        BufferedReader bufferedReader = null;

        String answer = "";

        try
        {
            bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            String temp;

            while (!((temp = bufferedReader.readLine()).equals("logout")))
            {
                answer += temp;
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (bufferedReader != null)
            {
                bufferedReader.close();
            }
        }
        return answer;
    }

    String executeCommands(List<String> commands)
    {
        try
        {
            Channel channel = getChannel();

            if (channel != null)
            {
                sendCommands(channel, commands);

                return readChannelOutput(channel);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void close()
    {
        if (channel != null && channel.isConnected())
        {
            channel.disconnect();
        }
        if (session != null && session.isConnected())
        {
            session.disconnect();
        }
    }
}