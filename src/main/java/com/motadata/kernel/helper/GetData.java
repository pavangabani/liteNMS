package com.motadata.kernel.helper;

import com.motadata.kernel.action.Dashboard;
import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GetData {

    public List<PollingMonitorBean> getAllPollingMonitor() {

        Database database = new Database();

        ArrayList attributes = new ArrayList();

        ArrayList values = new ArrayList();

        List<PollingMonitorBean> pollingmonitorList = new ArrayList<>();

        try {

            ResultSet resultSet = database.select("pollingmonitor", attributes, values);

            while (resultSet.next()) {

                PollingMonitorBean pollingmonitorBean = new PollingMonitorBean();

                pollingmonitorBean.setId(resultSet.getString(1));

                pollingmonitorBean.setName(resultSet.getString(2));

                pollingmonitorBean.setIp(resultSet.getString(3));

                pollingmonitorBean.setType(resultSet.getString(4));

                pollingmonitorBean.setTag(resultSet.getString(5));

                pollingmonitorBean.setAvailability(resultSet.getString(6));

                pollingmonitorList.add(pollingmonitorBean);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return pollingmonitorList;
    }

    public List<MonitorBean> getAllMonitor() {

        Database database = new Database();

        ArrayList attributes = new ArrayList();

        ArrayList values = new ArrayList();

        List<MonitorBean> monitorList = new ArrayList<>();

        try {

            ResultSet resultSet = database.select("monitor", attributes, values);

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

    public List<Integer> getDashboardData() {

        List<Integer> availability = new ArrayList<>(4);

        Database database = new Database();

        ArrayList attributes = new ArrayList();

        ArrayList values = new ArrayList();

        ResultSet resultSet = database.select("pollingmonitor", attributes, values);

        int up = 0;

        int down = 0;

        int unrechable = 0;

        int total = 0;

        try {


            while (resultSet.next()) {

                if (resultSet.getString(6).equals("UP")) {

                    up++;

                } else if (resultSet.getString(6).equals("DOWN")) {

                    down++;

                } else {

                    unrechable++;

                }
                
                total++;

            }


        } catch (Exception e) {

            e.printStackTrace();

        }

        availability.add(unrechable);

        availability.add(up);

        availability.add(down);

        availability.add(total);

        return availability;

    }

}
