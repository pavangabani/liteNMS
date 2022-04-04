package com.motadata.kernel.helper.discovery;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/server-endpoint")
public class ServerEndPoint
{
    static Session session;

    public static void send(String msg)
    {
        try
        {
            session.getBasicRemote().sendText(msg);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void openConnection(Session session)
    {
        this.session = session;
    }

    @OnError
    public void handleError(Throwable t)
    {
        t.printStackTrace();
    }
}
