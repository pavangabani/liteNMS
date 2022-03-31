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
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "select id,ip,type from pollingmonitor";

            List<HashMap<String, String>> data = database.select(query, new ArrayList<>());

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
                for (HashMap<String, String> row : data)
                {
                    String id = row.get("id"), ip = row.get("ip"), type = row.get("type");

                    PoolUtil.forkJoinPool.execute(new PollingThread(id,ip,type));
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
}

