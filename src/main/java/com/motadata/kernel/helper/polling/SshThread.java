package com.motadata.kernel.helper.polling;

import com.motadata.kernel.bean.PollingSshBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.GetData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.RecursiveAction;

public class SshThread extends RecursiveAction
{

    String id;

    String ip;

    SshThread(String id, String ip)
    {
        this.id = id;

        this.ip = ip;
    }

    @Override
    protected void compute()
    {
        Database database = null;

        try
        {
            PollingSshBean pollingSshBean = new GetData().getSshData(ip);

            //QueryStart

            database = new Database();

            String query = "insert into sshdump (id,cpu,memory,disk,uptime,pollingtime,totaldisk,totalmemory) values(?,?,?,?,?,?,?,?)";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id, pollingSshBean.getCpu(), pollingSshBean.getMemory(), pollingSshBean.getDisk(), pollingSshBean.getUpTime(), new Timestamp(new Date().getTime()), pollingSshBean.getTotalDisk(), pollingSshBean.getTotalMemory()));

            database.update(query, values);

            //QueryEnd

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
