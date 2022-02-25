package com.motadata.kernel.helper;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetData {

    public List<PollingMonitorBean> getAllPollingMonitor() {

        Database database=new Database();

        ArrayList attributes=new ArrayList();

        ArrayList values=new ArrayList();

        List<PollingMonitorBean> pollingmonitorList = new ArrayList<>();

        try {

            ResultSet resultSet = database.select("pollingmonitor",attributes,values);

            while (resultSet.next()) {

                PollingMonitorBean pollingmonitorBean = new PollingMonitorBean();

                pollingmonitorBean.setName(resultSet.getString(2));

                pollingmonitorBean.setIp(resultSet.getString(3));

                pollingmonitorBean.setType(resultSet.getString(4));

                pollingmonitorBean.setTag(resultSet.getString(5));

                pollingmonitorBean.setHealth(resultSet.getString(6));

                pollingmonitorBean.setAvailability(resultSet.getString(7));

                pollingmonitorList.add(pollingmonitorBean);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return pollingmonitorList;
    }

    public List<MonitorBean> getAllMonitor() {


        Database database=new Database();

        ArrayList attributes=new ArrayList();

        ArrayList values=new ArrayList();

        List<MonitorBean> monitorList = new ArrayList<>();

        try {

            ResultSet resultSet = database.select("monitor",attributes,values);

            while (resultSet.next()) {

                MonitorBean monitorBean = new MonitorBean();

                monitorBean.setId(resultSet.getString(1));

                monitorBean.setName(resultSet.getString(2));

                monitorBean.setIp(resultSet.getString(3));

                monitorBean.setType(resultSet.getString(4));

                monitorBean.setTag(resultSet.getString(5));

                monitorList.add(monitorBean);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return monitorList;
    }

}
