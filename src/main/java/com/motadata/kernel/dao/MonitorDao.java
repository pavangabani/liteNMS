package com.motadata.kernel.dao;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.helper.Discover;
import com.motadata.kernel.helper.GetData;

import java.util.*;

public class MonitorDao {

    public void load(MonitorBean monitorBean) {

        GetData getData = new GetData();

        monitorBean.setMonitorList(getData.getAllMonitor());

    }

    public void run(MonitorBean monitorBean) {

        Discover discover = new Discover();

        if (discover.discovery(monitorBean.getIp(), monitorBean.getType())) {

            monitorBean.setStatus("Discovery Successful!");

        } else {

            monitorBean.setStatus("Discovery Fails!");

        }

    }

    public void addPolling(MonitorBean monitorBean) {

        run(monitorBean);

        if (monitorBean.getStatus().equals("Discovery Successful!")) {

            ArrayList values = new ArrayList(Arrays.asList(monitorBean.getId(), monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag(), "Unknown"));

            String query="insert into pollingmonitor (id,name,ip,type,tag,availability) values(?,?,?,?,?,?)";

            int affectedRaw = Database.update(query,values);

            if (affectedRaw > 0) {

                monitorBean.setStatus("Monitor Added!");

            } else {

                monitorBean.setStatus("Failed to Add!");

            }

        } else {

            monitorBean.setStatus("Fails to add");

        }

    }

    public void edit(MonitorBean monitorBean) {

        Database database = new Database();

        GetData getData = new GetData();

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag(),monitorBean.getId()));

        String query="update monitor set name=?,ip=?,type=?,tag=? where id=?";

        int affectedRawPing = Database.update(query,values);

        if (monitorBean.getType().equals("ping")) {

            if (affectedRawPing > 0) {

                monitorBean.setStatus("Updated Ping");

            }

        } else {

            ArrayList valuesCheck = new ArrayList(Arrays.asList(monitorBean.getIp()));

            query = "select * from credential where ip=?";

            List<HashMap<String, String>> data = Database.select(query, valuesCheck);

            if (data.size() == 1) {

                String password = monitorBean.getPassword();

                String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

                ArrayList updateValues = new ArrayList(Arrays.asList(monitorBean.getUsername(), encodedPassword,monitorBean.getIp()));

                query="update credential set username=?,password=? where ip=?";

                int affectedRawSsh = Database.update(query,updateValues);

                if (affectedRawPing > 0 && affectedRawSsh > 0) {

                    monitorBean.setStatus("Updated SSH");

                }
            } else {

                String password = monitorBean.getPassword();

                String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

                ArrayList insertValues = new ArrayList(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), encodedPassword));

                query="insert into credential (ip,username,password) values(?,?,?)";

                int affectedRawSsh = Database.update(query,insertValues);

                if (affectedRawPing > 0 && affectedRawSsh > 0) {

                    monitorBean.setStatus("Updated SSH");

                }

            }

        }

        monitorBean.setMonitorList(getData.getAllMonitor());

    }

    public void delete(MonitorBean monitorBean) {

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getId()));

        String query="delete from monitor where id=?";

        int affectedRaw = Database.update(query,values);

        if (affectedRaw > 0) {

            monitorBean.setStatus("Monitor Deleted!");

        } else {

            monitorBean.setStatus("Could not Delete!");

        }

    }

    public void add(MonitorBean monitorBean) {

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag()));

        String query="insert into monitor (name,ip,type,tag) values(?,?,?,?)";

        int affectedRawPing = Database.update(query,values);

        if (monitorBean.getType().equals("ping")) {

            if (affectedRawPing > 0) {

                monitorBean.setStatus("Added Ping");

            }

        } else {

            String password = monitorBean.getPassword();

            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

            ArrayList credentialValues = new ArrayList(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), encodedPassword));

            query="insert into credential (ip,username,password) values(?,?,?)";

            int affectedRawSsh = Database.update(query,credentialValues);

            if (affectedRawPing > 0 && affectedRawSsh > 0) {

                monitorBean.setStatus("Added SSH!");

            }
        }

        GetData getData = new GetData();

        monitorBean.setMonitorList(getData.getAllMonitor());

    }

}
