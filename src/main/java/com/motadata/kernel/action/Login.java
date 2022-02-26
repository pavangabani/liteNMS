package com.motadata.kernel.action;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.LoginDao;
import com.opensymphony.xwork2.ModelDriven;

public class Login implements ModelDriven {

    LoginBean loginBean=new LoginBean();

    LoginDao loginDao=new LoginDao();

    public String login()
    {

        loginDao.login(loginBean);

        return "LOGIN";

    }

    @Override
    public Object getModel() {

        return loginBean;

    }
}