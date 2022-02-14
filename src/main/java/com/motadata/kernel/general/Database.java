package com.motadata.kernel.general;

import java.sql.*;

public class Database{
    Connection con;
    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:5000/liteNMS","root","");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getCon() {
        return con;
    }
}