package com.motadata.kernel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool
{
    private static BlockingQueue<Connection> connectionsPool;

    private static ArrayList<Connection> usedConnection = new ArrayList<>();

    public static void createFixedSizePool(Integer size)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connectionsPool = new ArrayBlockingQueue(size);

            for (int i = 0; i < size; i++)
            {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/liteNMS", "root", "Mind@123");

                connectionsPool.add(connection);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        Connection connection = null;

        try
        {
            connection = connectionsPool.take();

            usedConnection.add(connection);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    public static void releaseConnection(Connection connection)
    {
        try
        {
            connectionsPool.put(connection);

            usedConnection.remove(connection);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
