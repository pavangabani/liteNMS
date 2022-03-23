package com.motadata.kernel.executor;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.*;

import java.util.*;

public class MonitorExecutor
{
    public void load(MonitorBean monitorBean)
    {
        try
        {
            GetData getData = new GetData();

            monitorBean.setMonitorList(getData.getAllMonitor());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void add(MonitorBean monitorBean)
    {
        Database database = new Database();

        try
        {
            String query = "insert into monitor (name,ip,type,tag) values(?,?,?,?)";

            ArrayList<Object> values = new ArrayList<Object>(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag()));

            database.update(query, values);

            if (monitorBean.getType().equals("ssh"))
            {
                query = "insert into credential (ip,username,password) values(?,?,?)";

                ArrayList<Object> credentialValues = new ArrayList<Object>(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), Cipher.encode(monitorBean.getPassword())));

                database.update(query, credentialValues);
            }

            monitorBean.setStatus("Added!");

        } catch (Exception e)
        {
            e.printStackTrace();

        }finally
        {
            database.releaseConnection();
        }
    }

    public void addPolling(MonitorBean monitorBean)
    {
        Database database = new Database();

        try
        {
            //QueryStart

            String query = "select * from monitor where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(monitorBean.getId()));

            List<HashMap<String, String>> data = database.select(query, values);

            //QueryEnd

            if(data.size()==1)
            {
                boolean discoveryTest = PoolUtil.discoveryForkJoinPool.invoke(new DiscoveryThread(data.get(0).get("ip"), data.get(0).get("type")));

                if (discoveryTest)
                {
                    //QueryStart

                    query = "insert into pollingmonitor (id,name,ip,type,tag,availability) values(?,?,?,?,?,?)";

                    values = new ArrayList<Object>(Arrays.asList(monitorBean.getId(), data.get(0).get("name"), data.get(0).get("ip"), data.get(0).get("type"), data.get(0).get("tag"), "Unknown"));

                    int affectedRaw = database.update(query, values);

                    //QueryEnd

                    if (affectedRaw > 0)
                    {
                        monitorBean.setStatus("Monitor Added!");

                    } else if (affectedRaw == -1)
                    {
                        monitorBean.setStatus("Monitor Already Added! ");

                    } else
                    {
                        monitorBean.setStatus("Failed to Add!");
                    }
                } else
                {
                    monitorBean.setStatus("Ping Fail!");
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        }finally
        {
            database.releaseConnection();
        }
    }

    public void edit(MonitorBean monitorBean)
    {
        Database database = new Database();

        try
        {
            //QueryStart

            String query = "update monitor set name=?,ip=?,type=?,tag=? where id=?";

            ArrayList<Object> values = new ArrayList<Object>(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag(), monitorBean.getId()));

            database.update(query, values);

            //QueryEnd

            if (monitorBean.getType().equals("ssh"))
            {
                //QueryStart

                query = "select * from credential where ip=?";

                ArrayList<Object> valuesCheck = new ArrayList<Object>(Arrays.asList(monitorBean.getIp()));

                List<HashMap<String, String>> data = database.select(query, valuesCheck);

                //QueryEnd

                if (data.size() == 1)
                {
                    //QueryStart

                    query = "update credential set username=?,password=? where ip=?";

                    ArrayList<Object> updateValues = new ArrayList<Object>(Arrays.asList(monitorBean.getUsername(), Cipher.encode(monitorBean.getPassword()), monitorBean.getIp()));

                    database.update(query, updateValues);

                    //QueryEnd

                } else
                {
                    //QueryStart

                    query = "insert into credential (ip,username,password) values(?,?,?)";

                    ArrayList<Object> insertValues = new ArrayList<Object>(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), Cipher.encode(monitorBean.getPassword())));

                    database.update(query, insertValues);

                    //QueryEnd
                }
            }

            //forLoad Data

            GetData getData = new GetData();

            monitorBean.setMonitorList(getData.getAllMonitor());

        } catch (Exception e)
        {
            e.printStackTrace();

        }finally
        {
            database.releaseConnection();
        }
    }

    public void delete(MonitorBean monitorBean)
    {
        Database database = new Database();

        try
        {
            //QueryStart

            String query = "delete from monitor where id=?";

            ArrayList<Object> values = new ArrayList<Object>(Arrays.asList(monitorBean.getId()));

            int affectedRaw = database.update(query, values);

            //QueryEnd

            if (affectedRaw > 0)
            {
                monitorBean.setStatus("Monitor Deleted!");

            } else
            {
                monitorBean.setStatus("Could not Delete!");
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        }finally
        {
            database.releaseConnection();
        }
    }
}
