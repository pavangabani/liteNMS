package com.motadata.kernel.helper.discovery;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

public class Consumer
{
    public static void startConsumer()
    {
        try
        {
            NSQLookup lookup = new DefaultNSQLookup();

            lookup.addLookupAddress("localhost", 4161);

            NSQConsumer consumer = new NSQConsumer(lookup, "Discovery", "First", (message) ->
            {
                String id = new String(message.getMessage());

                new Discovery(id).discover();

                message.finished();
            });

            consumer.start();

        } catch (Exception e)
        {
            e.printStackTrace();

            System.exit(-1);
        }
    }
}
