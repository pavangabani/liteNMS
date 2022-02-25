package com.motadata.kernel.dao;

import com.motadata.kernel.bean.LoginBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDao {

    public void login(LoginBean loginBean) {

        Database database = new Database();

        ArrayList attributes = new ArrayList();

        attributes.add("user");

        attributes.add("pass");

        ArrayList values = new ArrayList();

        values.add(loginBean.getUsername());

        values.add(loginBean.getPassword());

        ResultSet resultSet = database.select("login", attributes, values);

        try {
            if (resultSet.next()) {

                loginBean.setStatus("Success");

            } else {

                loginBean.setStatus("Failure");

            }
        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

}
