package com.motadata.kernel.action;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.executor.LoginExecutor;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class LoginAction implements ModelDriven<LoginBean>, SessionAware
{
    LoginBean loginBean = new LoginBean();

    SessionMap<String, Object> sessionMain;

    public String login()
    {
        LoginExecutor.login(loginBean);

        //For_WebSocket--------------------------------

        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession session = request.getSession();

        session.setAttribute("uname", loginBean.getUsername());

        loginBean.setSessionId(session.getId());

        //WebSocket--------------------------------

        sessionMain.put("user", loginBean.getUsername());

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
            sessionMain.invalidate();

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

        } catch (Exception e)
        {

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
        this.sessionMain = (SessionMap<String, Object>) session;
    }
}