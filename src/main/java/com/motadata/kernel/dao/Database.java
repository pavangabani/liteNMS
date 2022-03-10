package com.motadata.kernel.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    public static List<HashMap<String, String>> select(String query, ArrayList values) {

        Connection connection = ConnectionPool.getConnection();

        List<HashMap<String, String>> data = null;

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            int i = 1;

            for (Object value : values) {

                if (value instanceof Integer) {

                    preparedStatement.setInt(i, (Integer) value);

                }
                else if (value instanceof String) {

                    preparedStatement.setString(i, (String) value);

                }
                else if (value instanceof Timestamp) {

                    preparedStatement.setTimestamp(i, (Timestamp) value);

                }

                i++;

            }

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();

            data = new ArrayList<>();

            while (resultSet.next()) {

                HashMap<String, String> row = new HashMap<>();

                for (int j = 1; j <= columnCount; j++) {

                    row.put(resultSetMetaData.getColumnName(j), resultSet.getString(j));

                }

                data.add(row);

            }

            preparedStatement.close();

        } catch (SQLException ex) {

            ex.printStackTrace();

        } finally {

            ConnectionPool.releaseConnection(connection);

        }

        return data;

    }

    public static int update(String query, ArrayList values) {

        Connection connection = ConnectionPool.getConnection();

        int affectedRow=0;

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            int i = 1;

            for (Object value : values) {

                if (value instanceof Integer) {

                    preparedStatement.setInt(i, (Integer) value);

                }
                else if (value instanceof String) {

                    preparedStatement.setString(i, (String) value);

                }
                else if (value instanceof Timestamp) {

                    preparedStatement.setTimestamp(i, (Timestamp) value);

                }

                i++;

            }

            affectedRow=preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }finally {

            ConnectionPool.releaseConnection(connection);

        }

        return affectedRow;
    }

}