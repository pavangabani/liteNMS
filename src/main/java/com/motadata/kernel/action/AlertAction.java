package com.motadata.kernel.action;

import com.motadata.kernel.bean.AlertBean;
import com.motadata.kernel.executor.AlertExecutor;
import com.opensymphony.xwork2.ModelDriven;

public class AlertAction implements ModelDriven<AlertBean>
{
    AlertBean alertBean = new AlertBean();

    public String load()
    {
        try
        {
            AlertExecutor.load(alertBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "LOADED";
    }

    public String add()
    {
        try
        {
            AlertExecutor.add(alertBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "ADDED";
    }

    public String delete()
    {
        try
        {
            AlertExecutor.delete(alertBean);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "DELETED";
    }

    @Override
    public AlertBean getModel()
    {
        return alertBean;
    }
}
