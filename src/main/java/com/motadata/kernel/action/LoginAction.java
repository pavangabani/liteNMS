package com.motadata.kernel.action;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.executor.LoginExecutor;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class LoginAction implements ModelDriven, SessionAware {

    LoginBean loginBean=new LoginBean();

    LoginExecutor loginExecutor =new LoginExecutor();

    SessionMap<String, Object> session;

    public String login() {

        loginExecutor.login(loginBean);

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