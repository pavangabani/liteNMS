package com.motadata.kernel.helper.polling;

import com.motadata.kernel.dao.Database;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PollingJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {

            Database database = new Database();

            ArrayList attributes = new ArrayList();

            ArrayList values = new ArrayList();

            ResultSet resultSet = database.select("pollingmonitor", attributes, values);

            ExecutorService pool = Executors.newFixedThreadPool(10);

            while (resultSet.next()) {

                String id=resultSet.getString(1);

                String ip=resultSet.getString(3);

                String type=resultSet.getString(4);

                Runnable pingTread=new PingTread(id,ip);

                pool.execute(pingTread);

                if(type.equals("ssh")){

                    Runnable sshTread=new SshTread(id,ip);

                    pool.execute(sshTread);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}

