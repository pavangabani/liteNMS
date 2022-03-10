package com.motadata.kernel.action;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.LoginDao;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class Login implements ModelDriven, SessionAware {

    LoginBean loginBean=new LoginBean();

    LoginDao loginDao=new LoginDao();

    Map<String, Object> map;

    public String login() {

        loginDao.login(loginBean);

        map.put("user",loginBean);

        return "LOGIN";

    }

    @Override
    public Object getModel() {

        return loginBean;

    }

    @Override
    public void setSession(Map<String, Object> map) {

        this.map=map;

    }
}