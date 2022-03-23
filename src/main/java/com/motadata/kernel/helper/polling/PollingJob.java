package com.motadata.kernel.helper.polling;

import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.PoolUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PollingJob implements Job
{
    @Override
    public void execute(JobExecutionContext jobExecutionContext)
    {
        Database database = new Database();

        try
        {
            //QueryStart

            String query = "select id,ip,type from pollingmonitor";

            List<HashMap<String, String>> data = database.select(query, new ArrayList<>());

            //QueryEnd

            if (data.size() > 0)
            {
                for (HashMap<String, String> row : data)
                {
                    String id = row.get("id"), ip = row.get("ip"), type = row.get("type");

                    Boolean ping = PoolUtil.forkJoinPool.invoke(new PingThread(id, ip));

                    if (ping && type.equals("ssh"))
                    {
                        PoolUtil.forkJoinPool.invoke(new SshThread(id, ip));
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            database.releaseConnection();
        }
    }
}

