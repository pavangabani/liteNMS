package com.motadata.kernel.executor;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.*;
import com.motadata.kernel.helper.discovery.Producer;

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
        Database database = null;

        try
        {
            database = new Database();

            String query = "select * from monitor where ip=? and type=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(monitorBean.getIp(), monitorBean.getType()));

            List<HashMap<String, String>> data = database.select(query, values);

            if (data.isEmpty())
            {
                //QueryStart

                query = "insert into monitor (name,ip,type,tag) values(?,?,?,?)";

                values = new ArrayList<>(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag()));

                database.update(query, values);

                //QueryEnd

                if (monitorBean.getType().equals("ssh"))
                {
                    String password = Cipher.encode(monitorBean.getPassword());

                    if (password == null) throw new NullPointerException();

                    //QueryStart

                    query = "insert into credential (ip,username,password) values(?,?,?)";

                    ArrayList<Object> credentialValues = new ArrayList<>(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), password));

                    database.update(query, credentialValues);

                    //QueryEnd
                }

                monitorBean.setStatus(monitorBean.getIp() + " Added!");

            } else
            {
                monitorBean.setStatus(monitorBean.getIp() + " Already Added!");
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

    public void addDiscovery(MonitorBean monitorBean)
    {
        Database database = null;

        try
        {
            String id = monitorBean.getId();

            Producer.producer.produce("Discovery", id.getBytes());

            //QueryStart

            database = new Database();

            String query = "select ip from monitor where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> data = database.select(query, values);

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
                monitorBean.setStatus(data.get(0).get("ip") + " Queued for Discovery");
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

    public void editData(MonitorBean monitorBean)
    {
        Database database = null;

        try
        {
            database = new Database();

            String query = "select * from monitor where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(monitorBean.getId()));

            List<HashMap<String, String>> data = database.select(query, values);

            database.releaseConnection();

            if (!data.isEmpty())
            {
                monitorBean.setName(data.get(0).get("name"));

                monitorBean.setIp(data.get(0).get("ip"));

                monitorBean.setType(data.get(0).get("type"));

                monitorBean.setTag(data.get(0).get("tag"));
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

    public void edit(MonitorBean monitorBean)
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "update monitor set name=?,ip=?,type=?,tag=? where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag(), monitorBean.getId()));

            database.update(query, values);

            //QueryEnd

            if (monitorBean.getType().equals("ssh"))
            {
                //QueryStart

                query = "select * from credential where ip=?";

                ArrayList<Object> valuesCheck = new ArrayList<>(Arrays.asList(monitorBean.getIp()));

                List<HashMap<String, String>> data = database.select(query, valuesCheck);

                //QueryEnd

                String password = Cipher.encode(monitorBean.getPassword());

                if (password == null) throw new NullPointerException();

                if (!data.isEmpty())
                {
                    //QueryStart

                    query = "update credential set username=?,password=? where ip=?";

                    ArrayList<Object> updateValues = new ArrayList<>(Arrays.asList(monitorBean.getUsername(), password, monitorBean.getIp()));

                    database.update(query, updateValues);

                    //QueryEnd

                } else
                {
                    //QueryStart

                    query = "insert into credential (ip,username,password) values(?,?,?)";

                    ArrayList<Object> insertValues = new ArrayList<>(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), password));

                    database.update(query, insertValues);

                    //QueryEnd
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

    public void delete(MonitorBean monitorBean)
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "delete from monitor where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(monitorBean.getId()));

            int affectedRaw = database.update(query, values);

            database.releaseConnection();

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

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
    }
}
