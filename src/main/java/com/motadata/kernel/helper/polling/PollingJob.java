package com.motadata.kernel.helper.polling;

import com.motadata.kernel.dao.Database;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PollingJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Database database = new Database();

        ArrayList attributes = new ArrayList(Arrays.asList("id", "ip", "type"));

        ArrayList values = new ArrayList();

        ResultSet resultSet = database.select("pollingmonitor", attributes, values);

        ExecutorService pool = Executors.newFixedThreadPool(10);

        try {

            while (resultSet.next()) {

                String id=resultSet.getString(1);

                String ip=resultSet.getString(2);

                String type=resultSet.getString(3);

                if(type.equals("ping")){

                    Runnable pingTread=new PingTread(id,ip);

                    pool.execute(pingTread);

                }
                else {

                    Runnable sshTread=new SshTread(id,ip);

                    pool.execute(sshTread);

                }

              
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}

