package com.motadata.kernel.helper;

import java.util.concurrent.ForkJoinPool;

public class PoolUtil
{
    static int processorCount = Runtime.getRuntime().availableProcessors();

    public static ForkJoinPool forkJoinPool = new ForkJoinPool(processorCount);

    public static ForkJoinPool discoveryForkJoinPool = new ForkJoinPool(1);
}
