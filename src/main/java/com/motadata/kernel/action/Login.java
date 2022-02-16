package com.motadata.kernel.action;


import com.motadata.kernel.general.Database;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends ActionSupport {

    private String username;
    private String password;
    private String status="";

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
        System.out.println(username+password);
        Database database=new Database();
        Connection connection= database.getCon();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM login WHERE user=? AND pass=?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()){
                status="SUCCESS1";
            }
            else {
                status="FAILURE1";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "LOGIN";
    }

}