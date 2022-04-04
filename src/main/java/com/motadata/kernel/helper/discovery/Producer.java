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

            String id = "-1";

            Producer.producer.produce("Discovery", id.getBytes());

        } catch (Exception e)
        {
            e.printStackTrace();

            System.exit(-1);
        }
    }
}
