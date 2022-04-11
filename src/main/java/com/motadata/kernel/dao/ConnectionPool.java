package com.motadata.kernel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool
{
    private static final BlockingQueue<Connection> connectionsPool= new LinkedBlockingQueue<>();

    private static final Queue<Connection> usedConnection = new LinkedList<>();

    public static int createFixedSizePool(Integer size)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            for (int i = 0; i < size; i++)
            {
                try
                {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/liteNMS", "root", "Mind@123");

                    connectionsPool.add(connection);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return connectionsPool.size();
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

    public static Boolean isAvailable(Connection connection)
    {
        return !usedConnection.contains(connection);
    }
}
