package com.motadata.kernel.executor;

import com.motadata.kernel.bean.LoginBean;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.Cipher;

import java.util.*;

public class LoginExecutor
{
    public void login(LoginBean loginBean)
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "select * from login where user=? AND pass=?";

            String password = Cipher.encode(loginBean.getPassword());

            if (password == null) throw new NullPointerException();

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(loginBean.getUsername(), password));

            List<HashMap<String, String>> data = database.select(query, values);

            database.releaseConnection();

            //QueryEnd

            if (!data.isEmpty())
            {
                loginBean.setStatus("Success");

            } else
            {
                loginBean.setStatus("Failure");
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
    }

    public void register(LoginBean loginBean)
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "insert into login values(?,?)";

            String password = Cipher.encode(loginBean.getPassword());

            if (password == null) throw new NullPointerException();

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(loginBean.getUsername(), password));

            int affectedRow = database.update(query, values);

            database.releaseConnection();

            //QueryEnd

            if (affectedRow == 1)
            {
                loginBean.setStatus("User Registered!");

            } else if (affectedRow == -1)
            {
                loginBean.setStatus("Username is Invalid!");

            } else
            {
                loginBean.setStatus("Fail to Register!");
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
    }
}
