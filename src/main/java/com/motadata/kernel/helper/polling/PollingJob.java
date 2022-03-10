package com.motadata.kernel.helper.polling;

import com.motadata.kernel.dao.Database;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PollingJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {

            Database database = new Database();

            String query="select * from pollingmonitor";

            List<HashMap<String,String>> data=Database.select(query,new ArrayList());

            ExecutorService pool = Executors.newFixedThreadPool(10);

            for (HashMap<String,String> row:data){

                String id=row.get("id");

                String ip=row.get("ip");

                String type=row.get("type");

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

