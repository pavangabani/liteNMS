package com.motadata.kernel.helper;

import com.motadata.kernel.dao.Database;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Mail
{
    public void sendPlainTextEmail(String subject, String message) throws MessagingException
    {

        // sets SMTP server properties
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");

        properties.put("mail.smtp.port", "587");

        properties.put("mail.smtp.auth", "true");

        properties.put("mail.smtp.starttls.enable", "true");

        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        properties.put("mail.smtp.user", "pavan.gabani.1@gmail.com");

        // creates a new session, no Authenticator (will connect() later)
        Session session = Session.getDefaultInstance(properties);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress("pavan.gabani.1@gmail.com"));

        InternetAddress[] toAddresses = {new InternetAddress(getMail())};

        msg.setRecipients(Message.RecipientType.TO, toAddresses);

        msg.setSubject(subject);

        // set plain text message
        msg.setText(message);

        // sends the e-mail
        Transport t = session.getTransport("smtp");

        t.connect("pavan.gabani.1@gmail.com", "123456P@VAN");

        t.sendMessage(msg, msg.getAllRecipients());

        t.close();

    }

    private static String getMail()
    {
        String email="";

        try
        {
            Database database = new Database();

            String query = "select * from emailalerts;";

            List<HashMap<String, String>> data = database.select(query, new ArrayList<>());

            database.releaseConnection();

            email = data.get(0).get("email");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return email;
    }

}