package com.motadata.kernel.helper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager
{
    public static ConcurrentHashMap<String, Map> sessions=new ConcurrentHashMap<>();
}
