package com.motadata.kernel.helper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class SessionInterceptor implements Interceptor
{

    String _allowedURLs[] = {"/login.jsp"};

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
        final ActionContext context = actionInvocation.getInvocationContext();

        HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

        HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

        if (response != null)
        {
            response.setHeader("Cache-control", "no-cache, no-store");

            response.setHeader("Pragma", "no-cache");

            response.setHeader("Expires", "-1");
        }

        Map<String, Object> session = ActionContext.getContext().getSession();

        boolean userActionQualified = false;

        if (session.get("user") != null)
        {
            actionInvocation.invoke();

            userActionQualified = true;

        } else
        {
            String requestURL = request.getRequestURI();

            for (String url : _allowedURLs)
            {
                if (requestURL.contains(url))
                {
                    actionInvocation.invoke();

                    userActionQualified = true;
                }
            }
        }

        if (!userActionQualified)
        {
            return "loginUser";
        }

        return ActionSupport.SUCCESS;
    }
}
