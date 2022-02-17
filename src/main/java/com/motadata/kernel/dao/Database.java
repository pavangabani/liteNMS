package com.motadata.kernel.dao;

import com.motadata.kernel.bean.MonitorBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    Connection connection;

    PreparedStatement preparedStatement;

    private String name;

    private String ip;

    private String type;

    private String tag;

    private String username;

    private String password;

    public Database(){

    }

    public Database(String name, String ip, String type, String tag) {

        this.name = name;

        this.ip = ip;

        this.type = type;

        this.tag = tag;
    }

    public Database(String name, String ip, String type, String tag, String password, String username) {

        this.name = name;

        this.ip = ip;

        this.type = type;

        this.tag = tag;

        this.username = username;

        this.password = password;
    }

    public Database(String username, String password) {

        this.username = username;

        this.password = password;
    }

     void getConnection() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:5000/liteNMS", "root", "");

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

        }


    }

    void closeConnection(){

        try {

            connection.close();

            preparedStatement.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }
    public boolean addPingMonitor() {

        getConnection();

        int i=0;

        try {

            preparedStatement = connection.prepareStatement("insert into monitor values(?,?,?,?)");

            preparedStatement.setString(1, name);

            preparedStatement.setString(2, ip);

            preparedStatement.setString(3, type);

            preparedStatement.setString(4, tag);

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        if(i>0){

            return true;

        }

        else {

            return false;

        }

    }

    public boolean addSshMonitor() {

        getConnection();

        int i=0,j=0;

        try {

            preparedStatement = connection.prepareStatement("insert into monitor values(?,?,?,?)");

            preparedStatement.setString(1, name);

            preparedStatement.setString(2, ip);

            preparedStatement.setString(3, type);

            preparedStatement.setString(4, tag);

            i = preparedStatement.executeUpdate();

            PreparedStatement preparedStatementSsh = connection.prepareStatement("insert into credential values(?,?,?)");

            preparedStatementSsh.setString(1, ip);

            preparedStatementSsh.setString(2, username);

            preparedStatementSsh.setString(3, password);

            j = preparedStatementSsh.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        if(i>0 && j>0){

            return true;

        }

        else {

            return false;

        }
    }

    public List<MonitorBean> getAllMonitor() {

        getConnection();

        List<MonitorBean> monitorList = new ArrayList<>();

        try {

            preparedStatement = connection.prepareStatement("select * from monitor");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                MonitorBean monitorBean = new MonitorBean(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));

                monitorList.add(monitorBean);

                monitorBean.display();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return monitorList;
    }

    public boolean validateLogin(){

        getConnection();

        try {

            preparedStatement = connection.prepareStatement("SELECT * FROM login WHERE user=? AND pass=?");

            preparedStatement.setString(1,username);

            preparedStatement.setString(2,password);

            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()){

                return true;

            }

            else {

                return false;


            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

}