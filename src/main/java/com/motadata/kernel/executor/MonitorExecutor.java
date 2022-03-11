package com.motadata.kernel.executor;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.Cipher;
import com.motadata.kernel.helper.Discover;
import com.motadata.kernel.helper.GetData;

import java.util.*;

public class MonitorExecutor {

    public void load(MonitorBean monitorBean) {

        GetData getData = new GetData();

        monitorBean.setMonitorList(getData.getAllMonitor());

    }

    public void addPolling(MonitorBean monitorBean) {

        Discover discover = new Discover();

        boolean discoveryTest = discover.discovery(monitorBean.getIp(), monitorBean.getType());

        if (discoveryTest) {

            //QueryStart

            String query = "insert into pollingmonitor (id,name,ip,type,tag,availability) values(?,?,?,?,?,?)";

            ArrayList values = new ArrayList(Arrays.asList(monitorBean.getId(), monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag(), "Unknown"));

            int affectedRaw = Database.update(query, values);

            //QueryEnd

            if (affectedRaw > 0) {

                monitorBean.setStatus("Monitor Added!");

            } else {

                monitorBean.setStatus("Failed to Add!");

            }

        } else {

            monitorBean.setStatus("Failed to Add!");

        }

    }

    public void edit(MonitorBean monitorBean) {

        //QueryStart

        String query = "update monitor set name=?,ip=?,type=?,tag=? where id=?";

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag(), monitorBean.getId()));

        Database.update(query, values);

        //QueryEnd

        if (monitorBean.getType().equals("ssh")) {

            //QueryStart

            query = "select * from credential where ip=?";

            ArrayList valuesCheck = new ArrayList(Arrays.asList(monitorBean.getIp()));

            List<HashMap<String, String>> data = Database.select(query, valuesCheck);

            //QueryEnd

            if (data.size() == 1) {

                //QueryStart

                query = "update credential set username=?,password=? where ip=?";

                ArrayList updateValues = new ArrayList(Arrays.asList(monitorBean.getUsername(), Cipher.encode(monitorBean.getPassword()), monitorBean.getIp()));

                Database.update(query, updateValues);

                //QueryEnd

            } else {

                //QueryStart

                query = "insert into credential (ip,username,password) values(?,?,?)";

                ArrayList insertValues = new ArrayList(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), Cipher.encode(monitorBean.getPassword())));

                Database.update(query, insertValues);

                //QueryEnd

            }
        }

        //forLoad Data

        GetData getData = new GetData();

        monitorBean.setMonitorList(getData.getAllMonitor());

    }

    public void delete(MonitorBean monitorBean) {

        //QueryStart

        String query = "delete from monitor where id=?";

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getId()));

        int affectedRaw = Database.update(query, values);

        //QueryEnd

        if (affectedRaw > 0) {

            monitorBean.setStatus("Monitor Deleted!");

        } else {

            monitorBean.setStatus("Could not Delete!");

        }

    }

    public void add(MonitorBean monitorBean) {

        //QueryStart

        String query = "insert into monitor (name,ip,type,tag) values(?,?,?,?)";

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag()));

        Database.update(query, values);

        //QueryEnd

        if(monitorBean.getType().equals("ssh")){

            //QueryStart

            query = "insert into credential (ip,username,password) values(?,?,?)";

            ArrayList credentialValues = new ArrayList(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), Cipher.encode(monitorBean.getPassword())));

            Database.update(query, credentialValues);

            //QueryEnd

        }

        //ForLoadData

        GetData getData = new GetData();

        monitorBean.setMonitorList(getData.getAllMonitor());

    }
}
