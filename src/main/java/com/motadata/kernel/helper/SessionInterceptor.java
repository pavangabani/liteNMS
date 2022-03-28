package com.motadata.kernel.helper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletResponse;
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
        ActionContext context = actionInvocation.getInvocationContext();

        HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

        if (response != null)
        {
            response.setHeader("Cache-control", "no-cache, no-store");

            response.setHeader("Expires", "-1");
        }

        Map<String, Object> session = ActionContext.getContext().getSession();

        boolean result = false;

        if (session.get("user") != null)
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
