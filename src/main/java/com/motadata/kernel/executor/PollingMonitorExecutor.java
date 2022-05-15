package com.motadata.kernel.executor;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.GetData;

import java.util.ArrayList;
import java.util.Arrays;

public class PollingMonitorExecutor
{
    public static void load(PollingMonitorBean pollingMonitorBean)
    {
        try
        {
            GetData getData = new GetData();

            pollingMonitorBean.setPollingMonitorBeanList(getData.getAllPollingMonitor());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void show(PollingMonitorBean pollingMonitorBean)
    {
        try
        {
            GetData getData = new GetData();

            if (pollingMonitorBean.getType().equals("ping"))
            {
                pollingMonitorBean.setPingStatistic(getData.getPingStatistic(pollingMonitorBean.getId()));

            } else
            {
                pollingMonitorBean.setSshStatistic(getData.getSshStatistic(pollingMonitorBean.getId()));
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void emailAlerts(PollingMonitorBean pollingMonitorBean)
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "update emailalerts set email=? where id=1";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(pollingMonitorBean.getEmail()));

            database.update(query, values);

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


    public static void delete(PollingMonitorBean pollingMonitorBean)
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "delete from pollingmonitor where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(pollingMonitorBean.getId()));

            int affectedRaw = database.update(query, values);

            query = "delete from pingdump where id=?";

            database.update(query, values);

            query = "delete from sshdump where id=?";

            database.update(query, values);

            //QueryEnd

            if (affectedRaw > 0)
            {
                pollingMonitorBean.setStatus("Monitor Deleted!");

            } else
            {
                pollingMonitorBean.setStatus("Could not Delete!");
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
