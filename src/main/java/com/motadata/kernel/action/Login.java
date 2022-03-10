package com.motadata.kernel.action;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.LoginDao;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class Login implements ModelDriven, SessionAware {

    LoginBean loginBean=new LoginBean();

    LoginDao loginDao=new LoginDao();

    SessionMap<String, Object> session;

    public String login() {

        loginDao.login(loginBean);

        session.put("user",loginBean.getUsername());

        return "LOGIN";

    }

    public String logout(){

        session.invalidate();

        return "LOGOUT";
    }

    @Override
    public Object getModel() {

        return loginBean;

    }

    @Override
    public void setSession(Map<String, Object> session) {

        this.session=(SessionMap<String, Object>) session;

    }
}