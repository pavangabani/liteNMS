package com.motadata.kernel.helper.discovery;

import com.github.brainlag.nsq.NSQConfig;

public class Nsq
{
    public static String getNsqdHost()
    {
        String hostName = System.getenv("NSQD_HOST");

        if (hostName == null)
        {
            hostName = "localhost";
        }
        return hostName;
    }

    public static NSQConfig getDeflateConfig()
    {
        final NSQConfig config = new NSQConfig();

        config.setCompression(NSQConfig.Compression.DEFLATE);

        config.setDeflateLevel(4);

        return config;
    }

    public static String getNsqLookupdHost()
    {
        String hostName = System.getenv("NSQLOOKUPD_HOST");

        if (hostName == null)
        {
            hostName = "localhost";
        }
        return hostName;
    }
}
