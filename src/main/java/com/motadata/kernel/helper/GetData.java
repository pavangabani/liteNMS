package com.motadata.kernel.helper;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetData {

    public List<PollingMonitorBean> getAllPollingMonitor() {

        Database database = new Database();

        List<PollingMonitorBean> pollingmonitorList = new ArrayList<>();

        try {

            ResultSet resultSet = database.select("pollingmonitor", new ArrayList(), new ArrayList());

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

        List<MonitorBean> monitorList = new ArrayList<>();

        try {

            ResultSet resultSet = database.select("monitor",new ArrayList(), new ArrayList());

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

        Database database = new Database();

        ResultSet resultSet = database.select("pollingmonitor", new ArrayList(), new ArrayList());

        int up = 0,down = 0,unrechable = 0,total = 0;

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

        List<Integer> availability = new ArrayList<>(Arrays.asList(unrechable,up,down,total));

        return availability;

    }

}
