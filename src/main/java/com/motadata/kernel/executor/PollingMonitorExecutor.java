package com.motadata.kernel.executor;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.GetData;

import java.util.ArrayList;
import java.util.Arrays;

public class PollingMonitorExecutor {

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

        Database database=new Database();

        String query = "delete from pollingmonitor where id=?";

        ArrayList<Object> values = new ArrayList(Arrays.asList(pollingMonitorBean.getId()));

        int affectedRaw = database.update(query, values);

        //QueryEnd

        if (affectedRaw > 0) {

            pollingMonitorBean.setStatus("Monitor Deleted!");

        } else {

            pollingMonitorBean.setStatus("Could not Delete!");

        }

        database.releaseConnection();
    }
}
