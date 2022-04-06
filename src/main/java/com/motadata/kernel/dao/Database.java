package com.motadata.kernel.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database
{
    Connection connection = ConnectionPool.getConnection();

    public List<HashMap<String, String>> select(String query, ArrayList<Object> values)
    {
        PreparedStatement preparedStatement = null;

        List<HashMap<String, String>> data = null;

        try
        {
            if (connection != null && !connection.isClosed())
            {
                //Set Prepare statement

                preparedStatement = connection.prepareStatement(query);

                int i = 1;

                for (Object value : values)
                {
                    if (value.getClass() == Integer.class)
                    {
                        preparedStatement.setInt(i, (Integer) value);

                    } else if (value.getClass() == String.class)
                    {
                        preparedStatement.setString(i, (String) value);

                    } else if (value.getClass() == Timestamp.class)
                    {
                        preparedStatement.setTimestamp(i, (Timestamp) value);
                    }
                    i++;
                }
                ResultSet resultSet = preparedStatement.executeQuery();

                //Get data from Resultset

                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                int columnCount = resultSetMetaData.getColumnCount();

                data = new ArrayList<>();

                while (resultSet.next())
                {
                    HashMap<String, String> row = new HashMap<>();

                    for (int j = 1; j <= columnCount; j++)
                    {
                        row.put(resultSetMetaData.getColumnName(j), resultSet.getString(j));
                    }
                    data.add(row);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();

        } finally
        {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return data;
    }

    public int update(String query, ArrayList<Object> values)
    {
        PreparedStatement preparedStatement = null;

        int affectedRow = 0;

        try
        {
            if (connection != null && !connection.isClosed())
            {
                preparedStatement = connection.prepareStatement(query);

                int i = 1;

                for (Object value : values)
                {
                    if (value.getClass() == Integer.class)
                    {
                        preparedStatement.setInt(i, (Integer) value);

                    } else if (value.getClass() == String.class)
                    {
                        preparedStatement.setString(i, (String) value);

                    } else if (value.getClass() == Timestamp.class)
                    {
                        preparedStatement.setTimestamp(i, (Timestamp) value);
                    }
                    i++;
                }
                affectedRow = preparedStatement.executeUpdate();
            }
        } catch (SQLIntegrityConstraintViolationException e)
        {
            return -1;

        } catch (SQLException e)
        {
            e.printStackTrace();

        } finally
        {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return affectedRow;
    }

    public void releaseConnection()
    {
        if (connection!=null && !ConnectionPool.isAvailable(connection))
        {
            ConnectionPool.releaseConnection(connection);
        }
    }
}