package com.motadata.kernel.helper.discovery;

import com.github.brainlag.nsq.NSQProducer;

public class Producer
{
    public static NSQProducer producer = null;

    public static void startProducer()
    {
        try
        {
            producer = new NSQProducer().addAddress("localhost", 4150).start();

            Producer.producer.produce("Discovery", "-1".getBytes());

        } catch (Exception e)
        {
            e.printStackTrace();

            System.exit(-1);
        }
    }
}
