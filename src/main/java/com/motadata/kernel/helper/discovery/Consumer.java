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

            NSQConsumer consumer = new NSQConsumer(lookup, "Discovery", "Main", (message) ->
            {
                try
                {
                    String messageText = new String(message.getMessage());

                    if (messageText.contains("sessionId:"))
                    {
                        String id = messageText.substring(0, messageText.indexOf("sessionId:"));

                        String sessionId = messageText.substring(messageText.indexOf("sessionId:") + "sessionId:".length());

                        new Discovery(id, sessionId).discover();
                    }
                    message.finished();

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
            consumer.start();

        } catch (Exception e)
        {
            e.printStackTrace();

            System.exit(-1);
        }
    }
}
