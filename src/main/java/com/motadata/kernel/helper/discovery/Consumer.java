package com.motadata.kernel.helper.discovery;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;
import com.motadata.kernel.helper.PoolUtil;


public class Consumer
{
    private static NSQLookup lookup = null;

    private static NSQConsumer consumer = null;

    public static void startConsumer()
    {
        try
        {
            NSQLookup lookup = new DefaultNSQLookup();

            lookup.addLookupAddress("localhost", 4161);

            consumer = new NSQConsumer(lookup, "Discovery", "First", (message) ->
            {
                String id = new String(message.getMessage());

                PoolUtil.discoveryForkJoinPool.execute(new DiscoveryThread(id));

                message.finished();
            });

            consumer.start();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
