package com.motadata.kernel.dao;

import com.motadata.kernel.bean.LoginBean;
import java.util.*;

public class LoginDao {

    public void login(LoginBean loginBean) {

        String username = loginBean.getUsername();

        String password = loginBean.getPassword();

        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

        ArrayList values = new ArrayList(Arrays.asList(username, encodedPassword));

        String query = "select * from login where user=? AND pass=?";

        List<HashMap<String, String>> data = Database.select(query, values);

        if (data.size() == 1) {

            loginBean.setStatus("Success");

        } else {

            loginBean.setStatus("Failure");

        }

    }

}
