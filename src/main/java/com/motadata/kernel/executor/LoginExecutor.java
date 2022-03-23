package com.motadata.kernel.executor;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.Cipher;

import java.util.*;

public class LoginExecutor
{
    public void login(LoginBean loginBean)
    {
        Database database = new Database();

        try
        {
            //QueryStart

            String query = "select * from login where user=? AND pass=?";

            ArrayList<Object> values = new ArrayList(Arrays.asList(loginBean.getUsername(), Cipher.encode(loginBean.getPassword())));

            List<HashMap<String, String>> data = database.select(query, values);

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

        }finally
        {
            database.releaseConnection();
        }
    }

    public void register(LoginBean loginBean)
    {
        Database database = new Database();

        try
        {
            //QueryStart

            String query = "insert into login values(?,?)";

            ArrayList<Object> values = new ArrayList(Arrays.asList(loginBean.getUsername(), Cipher.encode(loginBean.getPassword())));

            int affectedRow = database.update(query, values);

            //QueryEnd

            if (affectedRow == 1)
            {
                loginBean.setStatus("User Registered!");

            } else if (affectedRow == -1)
            {
                loginBean.setStatus("Username is invalid!");

            } else
            {
                loginBean.setStatus("Fail to Register!");
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        }finally
        {
            database.releaseConnection();
        }
    }
}
