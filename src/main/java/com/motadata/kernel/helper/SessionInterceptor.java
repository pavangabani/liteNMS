package com.motadata.kernel.helper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Map;

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
        Map<String, Object> sessionMain = ActionContext.getContext().getSession();

        boolean result = false;

        if (sessionMain.get("user") != null)
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
