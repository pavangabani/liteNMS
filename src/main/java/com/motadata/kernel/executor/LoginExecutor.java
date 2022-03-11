package com.motadata.kernel.executor;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.Cipher;

import java.util.*;

public class LoginExecutor {

    public void login(LoginBean loginBean) {

        //QueryStart

        ArrayList values = new ArrayList(Arrays.asList(loginBean.getUsername(), Cipher.encode(loginBean.getPassword())));

        String query = "select * from login where user=? AND pass=?";

        List<HashMap<String, String>> data = Database.select(query, values);

        //QueryEnd

        if (data.size() == 1) {

            loginBean.setStatus("Success");

        } else {

            loginBean.setStatus("Failure");
        }
    }
}
