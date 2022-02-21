package com.motadata.kernel.dao;

import com.motadata.kernel.bean.MonitorBean;
import com.motadata.kernel.bean.PollingMonitorBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    Connection connection;

    PreparedStatement preparedStatement;

    private String id;

    private String name;

    private String ip;

    private String type;

    private String tag;

    private String username;

    private String password;

    public Database(){

    }

    public Database(String id){
        this.id=id;
    }

    public Database(String id,String name, String ip, String type, String tag) {

        this.id=id;

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

    public boolean addPollingMonitor(){

        getConnection();

        int i=0;

        try {

            preparedStatement = connection.prepareStatement("insert into pollingmonitor  values(?,?,?,?,?,?,?)");

            preparedStatement.setString(1, id);

            preparedStatement.setString(2, name);

            preparedStatement.setString(3, ip);

            preparedStatement.setString(4, type);

            preparedStatement.setString(5, tag);

            preparedStatement.setString(6, "unknown");

            preparedStatement.setString(7, "unknown");

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
    public boolean deleteMonitor(){

        int test=0;

        getConnection();

        try {

            preparedStatement=connection.prepareStatement("delete from monitor where id=?");

            preparedStatement.setString(1,this.id);

            test=preparedStatement.executeUpdate();


        } catch (SQLException e) {

            e.printStackTrace();

        }

        if(test>0){

            return true;

        } else {

            return false;

        }

    }
    public boolean addPingMonitor() {

        getConnection();

        int i=0;

        try {

            preparedStatement = connection.prepareStatement("insert into monitor (name,ip,type,tag) values(?,?,?,?)");

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

            preparedStatement = connection.prepareStatement("insert into monitor (name,ip,type,tag)  values(?,?,?,?)");

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

    public List<PollingMonitorBean> getAllPollingMonitor(){

        getConnection();

        List<PollingMonitorBean> pollingmonitorList = new ArrayList<>();

        try {

            preparedStatement = connection.prepareStatement("select * from pollingmonitor");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                PollingMonitorBean pollingmonitorBean = new PollingMonitorBean( resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7));

                pollingmonitorList.add(pollingmonitorBean);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return pollingmonitorList;
    }
    public List<MonitorBean> getAllMonitor() {

        getConnection();

        List<MonitorBean> monitorList = new ArrayList<>();

        try {

            preparedStatement = connection.prepareStatement("select * from monitor");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                MonitorBean monitorBean = new MonitorBean(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5));

                monitorList.add(monitorBean);

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