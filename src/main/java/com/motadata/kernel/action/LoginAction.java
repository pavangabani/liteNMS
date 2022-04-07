package com.motadata.kernel.action;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.executor.LoginExecutor;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginAction implements ModelDriven<LoginBean>
{
    LoginBean loginBean = new LoginBean();

    public String login()
    {
        LoginExecutor.login(loginBean);

        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession session = request.getSession();

        session.setAttribute("uname", loginBean.getUsername());

        loginBean.setSessionId(session.getId());

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
            HttpServletRequest request = ServletActionContext.getRequest();

            request.getSession().invalidate();

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
            final HttpServletRequest request = ServletActionContext.getRequest();

            HttpSession session = request.getSession(false);

            String profileName = (String) session.getAttribute("uname");

            loginBean.setProfileName(profileName);

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

}