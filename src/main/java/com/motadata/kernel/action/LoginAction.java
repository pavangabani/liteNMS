package com.motadata.kernel.action;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.executor.LoginExecutor;
import com.motadata.kernel.helper.SessionManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class LoginAction implements ModelDriven<LoginBean>, SessionAware
{
    LoginBean loginBean = new LoginBean();

    Map<String, Object> session;

    public String login()
    {
        LoginExecutor.login(loginBean);

        session.put("user", loginBean.getUsername());

        SessionManager.sessions.put(loginBean.getUsername(), session);

        return "LOGIN";
    }

    public String register()
    {
        LoginExecutor.register(loginBean);

        return "REGISTER";
    }

    public String logout()
    {
        try
        {
            System.out.println(SessionManager.sessions);

            SessionManager.sessions.remove(loginBean.getProfileName());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "LOGOUT";
    }

    public String profile()
    {
        try
        {
            Map session = (Map) ActionContext.getContext().getSession();

            loginBean.setProfileName((String) session.get("user"));

        }catch (Exception e){

            e.printStackTrace();
        }


        return "PROFILE";
    }

    @Override
    public LoginBean getModel()
    {
        return loginBean;
    }

    @Override
    public void setSession(Map<String, Object> session)
    {
        this.session =  session;
    }
}