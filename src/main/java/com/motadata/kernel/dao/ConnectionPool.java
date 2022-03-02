package com.motadata.kernel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static BlockingQueue<Connection> connectionsPool;

    private static ArrayList<Connection> usedConnection=new ArrayList<>();

    static
    {
        createFixedSizePool(5);
    }

    static void createFixedSizePool(Integer size){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }

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

    static Connection getConnection(){

        Connection connection= connectionsPool.remove();

        usedConnection.add(connection);

        return connection;
    }

    static void releaseConnection(Connection connection){

        try {

            connectionsPool.put(connection);

            usedConnection.remove(connection);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
    }

}
