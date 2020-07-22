package com.genlot.futureTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long start = getNowTime();
                Thread.sleep(100);
                long end = getNowTime();
                long seed = end - start;
                System.out.println("seed = " + seed);
                return seed;
            }
        };

        List<Callable<Long>> tasks = new ArrayList<>();
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        tasks.add(callable);
        int poolSize = getCPUPrcessors();
        System.out.println("poolSize = " + poolSize);
        ExecutorService executorService = getExecutorService(poolSize);
        List<Future<Long>> futures = executorService.invokeAll(tasks);

        long result = 0;
        for (Future<Long> future : futures) {
            result += future.get();
        }
        System.out.println("result = " + result);
        executorService.shutdown();
    }

    //获取执行服务
    static ExecutorService getExecutorService(int poolSize){
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        return executorService;
    }

    static  int getCPUPrcessors(){
        int i = Runtime.getRuntime().availableProcessors();
        return i;
    }

    static Long getNowTime() {
        long l = System.currentTimeMillis();
        return l;
    }


}
