package com.motadata.kernel.executor;

import com.motadata.kernel.bean.AlertBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.GetData;
import java.util.ArrayList;
import java.util.Arrays;

public class AlertExecutor
{
    public static void load(AlertBean alertBean)
    {
        try
        {
            GetData getData = new GetData();

            alertBean.setAlertsList(getData.getAllAlerts());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void add(AlertBean alertBean)
    {
        Database database = null;

        try
        {
            database = new Database();

            String query = "insert into alerts (name,status,type,metric,critical,warning,clear) values(?,?,?,?,?,?,?)";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(alertBean.getName(),alertBean.getStatus(),alertBean.getType(),alertBean.getMetric(),alertBean.getCritical(),alertBean.getWarning(),alertBean.getClear()));

            database.update(query, values);

            alertBean.setStatus("Alert Added");

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

    public static void delete(AlertBean alertBean)
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "delete from alerts where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(alertBean.getId()));

            int affectedRaw = database.update(query, values);

            database.releaseConnection();

            //QueryEnd

            if (affectedRaw > 0)
            {
                alertBean.setStatus("Alert Deleted!");

            } else
            {
                alertBean.setStatus("Alert not Delete!");
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
