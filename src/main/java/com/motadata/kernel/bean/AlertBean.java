package com.motadata.kernel.bean;

import java.util.List;

public class AlertBean
{
    private String id;

    private String name;

    private String status;

    private String type;

    private String metric;

    private Integer critical;

    private Integer warning;

    private Integer clear;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getMetric()
    {
        return metric;
    }

    public void setMetric(String metric)
    {
        this.metric = metric;
    }

    public Integer getCritical()
    {
        return critical;
    }

    public void setCritical(Integer critical)
    {
        this.critical = critical;
    }

    public Integer getWarning()
    {
        return warning;
    }

    public void setWarning(Integer warning)
    {
        this.warning = warning;
    }

    public Integer getClear()
    {
        return clear;
    }

    public void setClear(Integer clear)
    {
        this.clear = clear;
    }

    public List<AlertBean> getAlertsList()
    {
        return alertsList;
    }

    public void setAlertsList(List<AlertBean> alertsList)
    {
        this.alertsList = alertsList;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    private List<AlertBean> alertsList;

}
