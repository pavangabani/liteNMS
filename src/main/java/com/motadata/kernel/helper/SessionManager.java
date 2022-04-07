package com.motadata.kernel.helper;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager
{
    public static ConcurrentHashMap<String, Session> sessions=new ConcurrentHashMap<>();
}
