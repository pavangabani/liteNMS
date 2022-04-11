package com.motadata.kernel.helper;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionInterceptor implements Interceptor
{

    @Override
    public void destroy()
    {

    }

    @Override
    public void init()
    {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception
    {
        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession session = request.getSession(false);

        String uname = null;

        if (session != null)
        {
            uname = (String) session.getAttribute("uname");
        }

        boolean result = false;

        if (uname != null)
        {
            actionInvocation.invoke();

            result = true;
        }

        if (!result)
        {
            return "loginUser";
        }

        return ActionSupport.SUCCESS;
    }
}
