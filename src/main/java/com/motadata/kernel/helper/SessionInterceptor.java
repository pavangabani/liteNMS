package com.motadata.kernel.helper;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;

public class SessionInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        Map<String,Object> session=actionInvocation.getInvocationContext().getSession();

        if(session.get("user")!=null){
            return actionInvocation.invoke();
        }

        return "loginUser";

    }
}
