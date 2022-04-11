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
        try
        {
            LoginExecutor.login(loginBean);

            if(loginBean.getStatus().equals("Success")){

                HttpServletRequest request = ServletActionContext.getRequest();

                HttpSession session = request.getSession();

                session.setAttribute("uname", loginBean.getUsername());

                loginBean.setSessionId(session.getId());
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "LOGIN";
    }

    public String register()
    {
        try
        {
            LoginExecutor.register(loginBean);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "REGISTER";
    }

    public String logout()
    {
        try
        {
            final HttpServletRequest request = ServletActionContext.getRequest();

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
            HttpServletRequest request = ServletActionContext.getRequest();

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