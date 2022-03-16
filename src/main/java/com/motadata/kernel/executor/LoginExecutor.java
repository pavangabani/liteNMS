package com.motadata.kernel.executor;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.Cipher;

import java.util.*;

public class LoginExecutor {

    public void login(LoginBean loginBean) {

        //QueryStart

        Database database = new Database();

        ArrayList<Object> values = new ArrayList(Arrays.asList(loginBean.getUsername(), Cipher.encode(loginBean.getPassword())));

        String query = "select * from login where user=? AND pass=?";

        List<HashMap<String, String>> data = database.select(query, values);

        database.releaseConnection();

        //QueryEnd

        if (data.size() == 1) {

            loginBean.setStatus("Success");

        } else {

            loginBean.setStatus("Failure");
        }
    }

    public void register(LoginBean loginBean) {

        //QueryStart

        Database database = new Database();

        ArrayList<Object> values = new ArrayList(Arrays.asList(loginBean.getUsername(), Cipher.encode(loginBean.getPassword())));

        String query = "insert into login values(?,?)";

        int affectedRow = database.update(query, values);

        database.releaseConnection();

        //QueryEnd

        if (affectedRow == 1) {

            loginBean.setStatus("User Registered");

        } else if(affectedRow == -1){

            loginBean.setStatus("Username is already taken");

        }else {

            loginBean.setStatus("Fail to Register");
        }
    }
}
