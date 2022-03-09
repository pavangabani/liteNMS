package com.motadata.kernel.dao;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.helper.Discover;
import com.motadata.kernel.helper.GetData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

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

            Database database = new Database();

            ArrayList attributes = new ArrayList(Arrays.asList("id", "name", "ip", "type", "tag", "availability"));

            ArrayList values = new ArrayList(Arrays.asList(monitorBean.getId(), monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag(), "Unknown"));

            int affectedRaw = database.insert("pollingmonitor", attributes, values);

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

        ArrayList attributes = new ArrayList(Arrays.asList("name", "ip", "type", "tag"));

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag()));

        ArrayList conditionAttributes = new ArrayList(Arrays.asList("id"));

        ArrayList conditionValues = new ArrayList(Arrays.asList(monitorBean.getId()));

        int affectedRawPing = database.update("monitor", attributes, values, conditionAttributes, conditionValues);

        if (monitorBean.getType().equals("ping")) {

            if (affectedRawPing > 0) {

                monitorBean.setStatus("Updated Ping");

            }

        } else {

            ArrayList attributeCheck = new ArrayList(Arrays.asList("ip"));

            ArrayList valuesCheck = new ArrayList(Arrays.asList(monitorBean.getIp()));

            ResultSet resultSet = database.select("credential", attributeCheck, valuesCheck);

            try {

                if (resultSet.next()) {

                    ArrayList updateAttributes = new ArrayList(Arrays.asList("username", "password"));

                    String password = monitorBean.getPassword();

                    String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

                    ArrayList updateValues = new ArrayList(Arrays.asList(monitorBean.getUsername(),encodedPassword ));

                    ArrayList updateConditionAttributes = new ArrayList(Arrays.asList("ip"));

                    ArrayList updateConditionValues = new ArrayList(Arrays.asList(monitorBean.getIp()));

                    int affectedRawSsh = database.update("credential", updateAttributes, updateValues, updateConditionAttributes, updateConditionValues);

                    if (affectedRawPing > 0 && affectedRawSsh > 0) {

                        monitorBean.setStatus("Updated SSH");

                    }
                } else {

                    ArrayList insertAttributes = new ArrayList(Arrays.asList("ip", "username", "password"));

                    String password =  monitorBean.getPassword();

                    String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

                    ArrayList insertValues = new ArrayList(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(),encodedPassword));

                    int affectedRawSsh = database.insert("credential", insertAttributes, insertValues);

                    if (affectedRawPing > 0 && affectedRawSsh > 0) {

                        monitorBean.setStatus("Updated SSH");

                    }

                }

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

        monitorBean.setMonitorList(getData.getAllMonitor());

    }

    public void delete(MonitorBean monitorBean) {

        Database database = new Database();

        ArrayList attributes = new ArrayList(Arrays.asList("id"));

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getId()));

        int affectedRaw = database.delete("monitor", attributes, values);

        if (affectedRaw > 0) {

            monitorBean.setStatus("Monitor Deleted!");

        } else {

            monitorBean.setStatus("Could not Delete!");

        }

    }

    public void add(MonitorBean monitorBean) {

        Database database = new Database();

        ArrayList attributes = new ArrayList(Arrays.asList("name", "ip", "type", "tag"));

        ArrayList values = new ArrayList(Arrays.asList(monitorBean.getName(), monitorBean.getIp(), monitorBean.getType(), monitorBean.getTag()));

        int affectedRawPing = database.insert("monitor", attributes, values);

        if (monitorBean.getType().equals("ping")) {

            if (affectedRawPing > 0) {

                monitorBean.setStatus("Added Ping");

            }

        } else {

            ArrayList credentialAttributes = new ArrayList(Arrays.asList("ip", "username", "password"));

            String password = monitorBean.getPassword();

            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

            ArrayList credentialValues = new ArrayList(Arrays.asList(monitorBean.getIp(), monitorBean.getUsername(), encodedPassword));

            int affectedRawSsh = database.insert("credential", credentialAttributes, credentialValues);

            if (affectedRawPing > 0 && affectedRawSsh > 0) {

                monitorBean.setStatus("Added SSH!");

            }
        }

        GetData getData = new GetData();

        monitorBean.setMonitorList(getData.getAllMonitor());

    }

}
