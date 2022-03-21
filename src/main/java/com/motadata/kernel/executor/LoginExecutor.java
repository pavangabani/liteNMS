package com.motadata.kernel.executor;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.Cipher;

import java.util.*;

public class LoginExecutor
{
    public void login(LoginBean loginBean)
    {
        try
        {
            //QueryStart

            Database database = new Database();

            String query = "select * from login where user=? AND pass=?";

            ArrayList<Object> values = new ArrayList(Arrays.asList(loginBean.getUsername(), Cipher.encode(loginBean.getPassword())));

            List<HashMap<String, String>> data = database.select(query, values);

            database.releaseConnection();

            //QueryEnd

            if (data.size() == 1)
            {
                loginBean.setStatus("Success");

            } else
            {
                loginBean.setStatus("Failure");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void register(LoginBean loginBean)
    {
        try
        {
            //QueryStart

            Database database = new Database();

            String query = "insert into login values(?,?)";

            ArrayList<Object> values = new ArrayList(Arrays.asList(loginBean.getUsername(), Cipher.encode(loginBean.getPassword())));

            int affectedRow = database.update(query, values);

            database.releaseConnection();

            //QueryEnd

            if (affectedRow == 1)
            {
                loginBean.setStatus("User Registered!");

            } else if (affectedRow == -1)
            {
                loginBean.setStatus("Username already taken!");

            } else
            {
                loginBean.setStatus("Fail to Register!");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
