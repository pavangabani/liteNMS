package com.motadata.kernel.helper;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

public class SSHCommand
{
    static String runCommand(ArrayList<Object> credential, String command)
    {
        String answer = "";

        BufferedReader bufferedReader = null;

        try
        {
            Properties config = new Properties();

            config.put("StrictHostKeyChecking", "no");

            //------------------------------------------------------------------

            JSch jsch = new JSch();

            Session session = jsch.getSession((String) credential.get(1), (String) credential.get(0), 22);

            session.setPassword((String) credential.get(2));

            session.setConfig(config);

            session.connect();

            //-------------------------------------------------------------------

            Channel channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(command);

            channel.connect();

            //--------------------------------------------------------------------

            bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            String temp;

            while ((temp = bufferedReader.readLine()) != null)
            {
                answer += temp + "\n";
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return answer;
    }
}
