package com.motadata.kernel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private BlockingQueue<Connection> connectionsPool;

    private ArrayList<Connection> usedConnection=new ArrayList<>();

    ConnectionPool(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }

    }

    void createFixedSizePool(Integer size){

        connectionsPool=new ArrayBlockingQueue(size);

        for(int i=0;i<size;i++){

            try {

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/liteNMS", "root", "Mind@123");

                connectionsPool.add(connection);

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }
    }

    Connection getConnection(){

        Connection connection= connectionsPool.remove();

        usedConnection.add(connection);

        return connection;
    }

    void releaseConnection(Connection connection){

        try {

            connectionsPool.put(connection);

            usedConnection.remove(connection);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
    }

}
