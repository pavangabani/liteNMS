package com.motadata.kernel.helper;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GetData {

    public List<PollingMonitorBean> getAllPollingMonitor() {

        String query = "select * from pollingmonitor";

        List<HashMap<String, String>> data = Database.select(query, new ArrayList());

        List<PollingMonitorBean> pollingmonitorList = new ArrayList<>();

        for (HashMap<String, String> row : data) {

            PollingMonitorBean pollingmonitorBean = new PollingMonitorBean();

            pollingmonitorBean.setId(row.get("id"));

            pollingmonitorBean.setName(row.get("name"));

            pollingmonitorBean.setIp(row.get("ip"));

            pollingmonitorBean.setType(row.get("type"));

            pollingmonitorBean.setTag(row.get("tag"));

            pollingmonitorBean.setAvailability(row.get("availability"));

            pollingmonitorList.add(pollingmonitorBean);

        }


        return pollingmonitorList;
    }

    public List<MonitorBean> getAllMonitor() {

        List<MonitorBean> monitorList = new ArrayList<>();

        String query = "select * from monitor";

        List<HashMap<String, String>> data = Database.select(query, new ArrayList());

        for (HashMap<String, String> row : data) {

            MonitorBean monitorBean = new MonitorBean();

            monitorBean.setId(row.get("id"));

            monitorBean.setName(row.get("name"));

            monitorBean.setIp(row.get("ip"));

            monitorBean.setType(row.get("type"));

            monitorBean.setTag(row.get("tag"));

            monitorList.add(monitorBean);

        }

        return monitorList;
    }

    public List<Integer> getDashboardData() {

        String query = "select availability from pollingmonitor";

        List<HashMap<String, String>> data = Database.select(query, new ArrayList());

        int up = 0, down = 0, unrechable = 0, total = 0;

        for (HashMap<String, String> row : data) {

            if (row.get("availability").equals("UP")) {

                up++;

            }
            if (row.get("availability").equals("DOWN")) {

                down++;

            }
            if (row.get("availability").equals("Unknown")) {

                unrechable++;

            }

            total++;

        }


        List<Integer> availability = new ArrayList<>(Arrays.asList(unrechable, up, down, total));

        return availability;

    }

}
