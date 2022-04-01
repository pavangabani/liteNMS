package com.motadata.kernel.helper.discovery;

import com.github.brainlag.nsq.NSQProducer;

public class Producer
{
    private static NSQProducer producer = null;

    public static NSQProducer getProducer()
    {
        try
        {
            if (producer == null)
            {
                producer = new NSQProducer();

                producer.setConfig(Nsq.getDeflateConfig());

                producer.addAddress(Nsq.getNsqdHost(), 4150);

                producer.start();
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return producer;
    }
}
