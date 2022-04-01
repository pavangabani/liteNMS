package com.motadata.kernel.helper.discovery;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;
import com.motadata.kernel.dao.Database;
import com.motadata.kernel.helper.PoolUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Consumer
{
    private static NSQLookup lookup = null;

    public static boolean isStart = false;

    public static void startConsumer()
    {
        try
        {
            NSQLookup lookup = getLookup();

            NSQConsumer consumer = new NSQConsumer(lookup, "Discovery", "First", (message) ->
            {
                String id = new String(message.getMessage());

                discover(id);

                message.finished();

            }, Nsq.getDeflateConfig());

            consumer.start();

            isStart = true;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static NSQLookup getLookup()
    {
        if (lookup == null)
        {
            lookup = new DefaultNSQLookup();

            lookup.addLookupAddress(Nsq.getNsqLookupdHost(), 4161);
        }
        return lookup;
    }

    public static void discover(String id)
    {
        Database database = null;

        try
        {
            //QueryStart

            database = new Database();

            String query = "select * from monitor where id=?";

            ArrayList<Object> values = new ArrayList<>(Arrays.asList(id));

            List<HashMap<String, String>> data = database.select(query, values);

            //QueryEnd

            if (!data.isEmpty())
            {
                boolean discoveryTest = PoolUtil.discoveryForkJoinPool.invoke(new DiscoveryThread(data.get(0).get("ip"), data.get(0).get("type")));

                if (discoveryTest)
                {
                    //QueryStart

                    query = "insert into pollingmonitor (id,name,ip,type,tag,availability) values(?,?,?,?,?,?)";

                    values = new ArrayList<>(Arrays.asList(id, data.get(0).get("name"), data.get(0).get("ip"), data.get(0).get("type"), data.get(0).get("tag"), "Unknown"));

                    System.out.println(database.update(query, values));

                    System.out.println("Added");

                    //QueryEnd
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (database != null)
            {
                database.releaseConnection();
            }
        }
    }
}
