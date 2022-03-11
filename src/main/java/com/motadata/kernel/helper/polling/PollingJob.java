package com.motadata.kernel.helper.polling;

import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.PoolUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PollingJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        //QueryStart

        String query = "select id,ip,type from pollingmonitor";

        List<HashMap<String, String>> data = Database.select(query, new ArrayList());

        //QueryEnd

        for (HashMap<String, String> row : data) {

            String id=row.get("id"),ip=row.get("ip"),type=row.get("type");

            //forkjoinpool

            Boolean ping = PoolUtil.forkJoinPool.invoke(new PingTread(id,ip));

            if (ping && type.equals("ssh")) {

                PoolUtil.forkJoinPool.invoke(new SshTread(id,ip));

            }

        }


    }

}

