package com;

import com.gw.common.SnowflakeIdWorker;
import com.gw.util.DataConvertUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 **/
public class OtherTest {

    @Test
    public void test() {
        String s = null;
        System.out.println(DataConvertUtil.parseLong(s));
        System.out.println(Long.parseLong(s));
        System.out.println(Long.valueOf(s));
    }

    static List<Long> ids = new ArrayList<>();
    static int threadCount = 0;

    @Test
    public void nextId() {
        SnowflakeIdWorker worker = new SnowflakeIdWorker(1,2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    long nextId = worker.nextId();
                    System.out.println("线程2 id = " + nextId + " * " + threadCount++);
                    if (ids.contains(nextId)) {
                        System.out.println("异常");
                    } else {
                        ids.add(nextId);
                    }
                }
            }
        }).start();

        for (int i = 0; i < 100; i++) {
            long nextId = worker.nextId();
            System.out.println("线程1 id = " + nextId + " ** " + i);
            if (ids.contains(nextId)) {
                System.out.println("异常");
            } else {
                ids.add(nextId);
            }
        }
    }

    @Test
    public void nextId2(){
        System.out.println(new SnowflakeIdWorker(0,1));
        System.out.println(new SnowflakeIdWorker(0,2));

        long timestamp = System.currentTimeMillis();
        System.out.println(((timestamp - 1420041600000L) << 22) //
                | (1 << 17) //
                | (0 << 12) //
                | 0);

        System.out.println(((timestamp - 1420041600000L) << 22) //
                | (2 << 17) //
                | (0 << 12) //
                | 0);
    }
}
