package com.motadata.kernel.helper;

import java.util.concurrent.ForkJoinPool;

public class PoolUtil
{
    private static final int processorCount = Runtime.getRuntime().availableProcessors();

    public static final ForkJoinPool forkJoinPool = new ForkJoinPool(processorCount);
    
    public static final ForkJoinPool forkJoinPoolDiscover = new ForkJoinPool(processorCount);
}
