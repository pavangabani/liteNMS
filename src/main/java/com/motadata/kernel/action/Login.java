package com.motadata.kernel.action;
import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.Database;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class Login extends ActionSupport implements ModelDriven {

    LoginBean loginBean=new LoginBean();

    public String login()
    {
        Database database=new Database();

        database.setUsername(loginBean.getUsername());

        database.setPassword(loginBean.getPassword());

        if (database.validateLogin()){

            loginBean.setStatus("Success");

        }

        else{

            loginBean.setStatus("Failure");

        }

        return "LOGIN";

    }

    @Override
    public Object getModel() {

        return loginBean;

    }
}