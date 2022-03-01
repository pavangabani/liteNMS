package com.motadata.kernel.dao;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.helper.Discover;
import com.motadata.kernel.helper.GetData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MonitorDao {

    public void load(MonitorBean monitorBean){

        GetData getData=new GetData();

        monitorBean.setMonitorList(getData.getAllMonitor());

    }

    public void add(MonitorBean monitorBean){

        Database database = new Database();

        GetData getData=new GetData();

        ArrayList attributes=new ArrayList();

        attributes.add("name");

        attributes.add("ip");

        attributes.add("type");

        attributes.add("tag");

        ArrayList values=new ArrayList();

        values.add(monitorBean.getName());

        values.add(monitorBean.getIp());

        values.add(monitorBean.getType());

        values.add(monitorBean.getTag());

        int affectedRawPing=database.insert("monitor",attributes,values);

        if (monitorBean.getType().equals("ping")) {

            if (affectedRawPing > 0) {

                monitorBean.setStatus("Added Ping");

            }

        } else {

            ArrayList attributes2=new ArrayList();

            attributes2.add("ip");

            attributes2.add("username");

            attributes2.add("password");

            ArrayList values2=new ArrayList();

            values2.add(monitorBean.getIp());

            values2.add(monitorBean.getUsername());

            values2.add(monitorBean.getPassword());

            int affectedRawSsh=database.insert("credential",attributes2,values2);

            if (affectedRawPing > 0 && affectedRawSsh > 0) {

                monitorBean.setStatus("Added SSH");

            }
        }

        monitorBean.setMonitorList(getData.getAllMonitor());
    }

    public void run(MonitorBean monitorBean){

        Discover discover=new Discover();

        if(discover.discovery(monitorBean.getIp(),monitorBean.getType())) {

            monitorBean.setStatus("Discovery Successful!");

        }else {

            monitorBean.setStatus("Discovery Fails!");

        }

    }

    public void addPolling(MonitorBean monitorBean){

        Database database=new Database();

        ArrayList attributes=new ArrayList();

        attributes.add("id");

        attributes.add("name");

        attributes.add("ip");

        attributes.add("type");

        attributes.add("tag");

        attributes.add("availability");

        ArrayList values=new ArrayList();

        values.add(monitorBean.getId());

        values.add(monitorBean.getName());

        values.add(monitorBean.getIp());

        values.add(monitorBean.getType());

        values.add(monitorBean.getTag());

        values.add("unknown");

        int affectedRaw= database.insert("pollingmonitor",attributes,values);

        if (affectedRaw > 0) {

            monitorBean.setStatus("Monitor added for polling");

        } else {

            monitorBean.setStatus("Fails to add");

        }

    }

    public void edit(MonitorBean monitorBean){

        Database database = new Database();

        GetData getData=new GetData();

        ArrayList attributes=new ArrayList();

        attributes.add("name");

        attributes.add("ip");

        attributes.add("type");

        attributes.add("tag");

        ArrayList values=new ArrayList();

        values.add(monitorBean.getName());

        values.add(monitorBean.getIp());

        values.add(monitorBean.getType());

        values.add(monitorBean.getTag());

        ArrayList conditionAttributes=new ArrayList();

        conditionAttributes.add("id");

        ArrayList conditionValues=new ArrayList();

        conditionValues.add(monitorBean.getId());

        int affectedRawPing=database.update("monitor",attributes,values,conditionAttributes,conditionValues);

        if (monitorBean.getType().equals("ping")) {

            if (affectedRawPing > 0) {

                monitorBean.setStatus("Updated Ping");

            }

        } else {

            ArrayList attributeCheck=new ArrayList();

            attributeCheck.add("ip");

            ArrayList valuesCheck=new ArrayList();

            valuesCheck.add(monitorBean.getIp());

            ResultSet resultSet=database.select("credential",attributeCheck,valuesCheck);

            try {

                if(resultSet.next()){

                    ArrayList attributes2=new ArrayList();

                    attributes2.add("username");

                    attributes2.add("password");

                    ArrayList values2=new ArrayList();

                    values2.add(monitorBean.getUsername());

                    values2.add(monitorBean.getPassword());

                    ArrayList conditionAttributes2=new ArrayList<>();

                    conditionAttributes2.add("ip");

                    ArrayList conditionValues2=new ArrayList<>();

                    conditionValues2.add(monitorBean.getIp());

                    int affectedRawSsh=database.update("credential",attributes2,values2,conditionAttributes2,conditionValues2);

                    if (affectedRawPing > 0 && affectedRawSsh > 0) {

                        monitorBean.setStatus("Updates SSH");

                    }
                }
                else {

                    ArrayList attributes2=new ArrayList();

                    attributes2.add("ip");

                    attributes2.add("username");

                    attributes2.add("password");

                    ArrayList values2=new ArrayList();

                    values2.add(monitorBean.getIp());

                    values2.add(monitorBean.getUsername());

                    values2.add(monitorBean.getPassword());

                    int affectedRawSsh=database.insert("credential",attributes2,values2);

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

    public void delete(MonitorBean monitorBean){

        Database database=new Database();

        ArrayList attributes=new ArrayList<>();

        attributes.add("id");

        ArrayList values=new ArrayList<>();

        values.add(monitorBean.getId());

        int affectedRaw=database.delete("monitor",attributes,values);

        if (affectedRaw > 0) {

            monitorBean.setStatus("Monitor Deleted");

        } else {

            monitorBean.setStatus("Could not Delete");

        }

    }
}
