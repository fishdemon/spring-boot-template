package com.fishdemon.sbt.util;

import org.springframework.util.StopWatch;

/**
 * StopWatch 时间记录器
 * @author Anjin.Ma
 * @date 2019-6-1
 */
public class StopWatchDemo {

    public static void main(String[] args) throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start("task1");
        // do task
        Thread.sleep(1000);
        sw.stop();

        sw.start("task2");
        Thread.sleep(500);
        sw.stop();

        System.out.println("```````Short Summary`````````");
        System.out.println(sw.shortSummary());
        System.out.println("````````Pretty Print````````");
        System.out.println(sw.prettyPrint());
    }


}
