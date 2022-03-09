package com.motadata.kernel.dao;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.helper.polling.PollingScheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class LoginDao {

    public void login(LoginBean loginBean) {

        Database database = new Database();

        ArrayList attributes = new ArrayList(Arrays.asList("user","pass"));

        String username=loginBean.getUsername();

        String password = loginBean.getPassword();

        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        ArrayList values=new ArrayList(Arrays.asList(username,encodedPassword));

        ResultSet resultSet = database.select("login", attributes, values);

        try {

            if (resultSet.next()) {

                loginBean.setStatus("Success");

                activityOnLogin();

            } else {

                loginBean.setStatus("Failure");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    void activityOnLogin(){

        PollingScheduler pollingScheduler=new PollingScheduler();

        pollingScheduler.createScheduler();

        pollingScheduler.startScheduler();

    }
}
