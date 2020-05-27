package com.example.rocketmq.rocketmqdemo.app;

import org.apache.rocketmq.common.ThreadFactoryImpl;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class ThreadPoolExecutorTest {

    private final static int MIN_THREAD_COUNT = 5;
    private final static int MAX_THREAD_COUNT = 15;


    public static void main(String[] args) throws Exception {

        //BlockingQueue<Runnable> consumeRequestQueue = new LinkedBlockingQueue<Runnable>();
        ArrayBlockingQueue<Runnable> consumeRequestQueue = new ArrayBlockingQueue<Runnable>(10);
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
                MIN_THREAD_COUNT,
                MAX_THREAD_COUNT,
                1000 * 10,
                TimeUnit.MILLISECONDS,
                consumeRequestQueue,
                new ThreadFactoryImpl("ConsumeMessageThread_"));

        class MyThread implements Runnable {

            private int myID;
            MyThread(int i) {
                myID = i;
            }

            @Override
            public void run() {
                System.out.println("Thread " + myID);
                try {
                    sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        for(int i=0;i<100;i++) {
            tpe.execute(new MyThread(i));

            System.out.println(i + " Pool size:"+ tpe.getPoolSize() + ", corepoolsize:" + tpe.getCorePoolSize() + ", activeCount:" + tpe.getActiveCount() + ", Queue size:" + consumeRequestQueue.size() );
        }
    }
}
