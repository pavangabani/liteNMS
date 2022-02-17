package com.motadata.kernel.action;


import com.motadata.kernel.dao.Database;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends ActionSupport {

    private String username;

    private String password;

    private String status;

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String login()
    {
        Database database=new Database(username,password);

        if (database.validateLogin()){

            status="Success";

        }

        else{

            status="Failure";

        }

        return "LOGIN";

    }

}