package com.motadata.kernel.dao;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    Connection connection;

    PreparedStatement preparedStatement;

    public Database(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }
    }

    void getConnection() {

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/liteNMS", "root", "Mind@123");

        } catch(SQLException e){

            e.printStackTrace();

        }


    }
    public ResultSet select(String tableName, ArrayList attributes, ArrayList values) {

        getConnection();

        ResultSet resultSet;

        int size = attributes.size();

        String query="select * from "+tableName+" where ";

        for(Object attribute:attributes){

            query+=attribute+"=? AND ";

        }

        query+= "1=1";

        try {
            preparedStatement=connection.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            for(int i=1;i<=size;i++){

                if(values.get(i-1) instanceof Integer){

                    preparedStatement.setInt(i, (Integer) values.get(i-1));

                }
                else {

                    preparedStatement.setString(i,(String) values.get(i-1));
                }

            }

            resultSet=preparedStatement.executeQuery();

            return resultSet;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    int delete(String tableName,ArrayList attributes,ArrayList values)  {

        getConnection();

        int affectedRaw=0;

        int size = attributes.size();

        String query="delete from "+tableName+" where ";

        for(Object attribute:attributes){

            query+=attribute+"=? AND ";

        }

        query+= "1=1";

        try {

            preparedStatement=connection.prepareStatement(query);

            for(int i=1;i<=size;i++){

                if(values.get(i-1) instanceof Integer){

                    preparedStatement.setInt(i, (Integer) values.get(i-1));

                }
                else {

                    preparedStatement.setString(i,(String) values.get(i-1));
                }

            }

            affectedRaw=preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return affectedRaw;

    }

    int insert(String tableName,ArrayList attributes,ArrayList values) {

        getConnection();

        int affectedRaw=0;

        int length=attributes.size();

        String attributesString ="(";

        String valueString="(";

        for(int i=0;i<length;i++){

            if(length-1==i){

                attributesString += attributes.get(i);

                valueString+="?";

            }else {

                attributesString += attributes.get(i)+",";

                valueString+="?,";

            }

        }

        attributesString+=")";

        valueString+=")";

        String query= "insert into "+tableName+" "+attributesString+" values"+valueString;

        try {

            preparedStatement=connection.prepareStatement(query);

            for(int i=1;i<=length;i++){

                if(values.get(i-1) instanceof Integer){

                    preparedStatement.setInt(i, (Integer) values.get(i-1));

                }
                else {

                    preparedStatement.setString(i,(String) values.get(i-1));
                }

            }

            affectedRaw=preparedStatement.executeUpdate();


        } catch (SQLException e) {

            e.printStackTrace();

        }

        return affectedRaw;
    }

    void closeConnection() {

        try {

            connection.close();

            preparedStatement.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}