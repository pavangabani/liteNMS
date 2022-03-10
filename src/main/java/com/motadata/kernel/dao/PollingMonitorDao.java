package com.motadata.kernel.dao;

import com.motadata.kernel.bean.PollingMonitorBean;
import com.motadata.kernel.helper.GetData;
import com.motadata.kernel.helper.polling.PollingDump;

import java.util.ArrayList;
import java.util.Arrays;

public class PollingMonitorDao {

    public void load(PollingMonitorBean pollingMonitorBean){

        GetData getData=new GetData();

        pollingMonitorBean.setPollingMonitorBeanList(getData.getAllPollingMonitor());

    }

    public void show(PollingMonitorBean pollingMonitorBean){

        try {

            PollingDump getData=new PollingDump();

            if (pollingMonitorBean.getType().equals("ping")){

                pollingMonitorBean.setPingStatistic(getData.getPingStatistic(pollingMonitorBean.getId()));

            }
            else {

                pollingMonitorBean.setSshStatistic(getData.getSshStatistic(pollingMonitorBean.getId()));

            }

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    public void delete(PollingMonitorBean pollingMonitorBean){

        ArrayList values = new ArrayList(Arrays.asList(pollingMonitorBean.getId()));

        String query="delete from pollingmonitor where id=?";

        int affectedRaw = Database.update(query,values);

        if (affectedRaw > 0) {

            pollingMonitorBean.setStatus("Monitor Deleted!");

        } else {

            pollingMonitorBean.setStatus("Could not Delete!");

        }

    }
}
