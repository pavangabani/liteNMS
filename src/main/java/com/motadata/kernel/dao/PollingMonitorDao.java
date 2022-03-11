package com.motadata.kernel.dao;

import com.motadata.kernel.bean.PollingMonitorBean;

import java.util.ArrayList;
import java.util.Arrays;

public class PollingMonitorDao {

    public void load(PollingMonitorBean pollingMonitorBean) {

        GetData getData = new GetData();

        pollingMonitorBean.setPollingMonitorBeanList(getData.getAllPollingMonitor());

    }

    public void show(PollingMonitorBean pollingMonitorBean) {
        
        GetData getData = new GetData();

        if (pollingMonitorBean.getType().equals("ping")) {

            pollingMonitorBean.setPingStatistic(getData.getPingStatistic(pollingMonitorBean.getId()));

        } else {

            pollingMonitorBean.setSshStatistic(getData.getSshStatistic(pollingMonitorBean.getId()));

        }

    }

    public void delete(PollingMonitorBean pollingMonitorBean) {

        //QueryStart

        String query = "delete from pollingmonitor where id=?";

        ArrayList values = new ArrayList(Arrays.asList(pollingMonitorBean.getId()));

        int affectedRaw = Database.update(query, values);

        //QueryEnd

        if (affectedRaw > 0) {

            pollingMonitorBean.setStatus("Monitor Deleted!");

        } else {

            pollingMonitorBean.setStatus("Could not Delete!");

        }
    }
}
