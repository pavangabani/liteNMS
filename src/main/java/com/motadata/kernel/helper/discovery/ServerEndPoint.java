package com.motadata.kernel.helper.discovery;

import com.motadata.kernel.helper.SessionManager;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/server-endpoint")
public class ServerEndPoint
{
    static Session session;

    public static void send(String msg, String sessionId)
    {
        try
        {
            session = SessionManager.sessions.get(sessionId);

            if (session != null)
            {
                session.getBasicRemote().sendText(msg);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void openConnection(Session session, String message)
    {
        ServerEndPoint.session = session;

        SessionManager.sessions.put(message, session);
    }

    @OnError
    public void handleError(Throwable t)
    {
        t.printStackTrace();
    }
}
