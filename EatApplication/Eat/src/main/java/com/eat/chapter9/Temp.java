package com.eat.chapter9;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/10/30.
 */
class Temp {

    /**
     * 线程池的构造方法参数
     *
     * @param corePoolSize    核心池的大小，能够同时运行的线程数量：
     *                        线程池中包含的线程的下限。
     *                        实际上，线程池从零个线程开始，
     *                        但一旦达到核心池大小，线程数就不会低于此下限。
     *                        如果在池中的工作线程数低于核心池大小时将任务添加到队列中，
     *                        则即使有空闲线程等待任务，也将创建新线程。
     *                        一旦工作线程的数量等于或高于核心池大小，只有当队列已满（即队列优先于线程信任）时，
     *                        才会创建新的工作线程。
     * @param maximumPoolSize 缓存的大小，最多允许等待运行的线程数量
     *                        可同时执行的最大线程数。
     *                        当达到最大池大小时，添加到队列中的任务将在队列中等待，
     *                        直到有空闲线程可用于处理任务
     * @param keepAliveTime   线程运行的存活时间，如果超时会被自动退出？
     *                        空闲线程在线程池中保持活动状态，以准备处理传入的任务，
     *                        但如果设置了活动时间，系统可以回收非核心池线程。
     *                        活动时间以时间单位配置，时间以时间单位度量。
     * @param unit            时间单元，配合{@link #keepAliveTime}使用
     * @param workQueue       TODO ？？
     * @param threadFactory   TODO  ？？
     * @param handler         TODO ？？
     */
    private void temp(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler) {
//        Fixed size
//        固定大小的线程池维护用户定义的工作线程数。
//        终止的线程将被新线程替换，以保持工作线程数不变。这种类型的池是用executors.newFixedThreadPool（n）创建的，其中n是线程数。
//        这种类型的线程池使用一个无限的任务队列，这意味着随着新任务的添加，队列可以自由增长。因此，生产者在插入任务时不会失败。

//        Dynamic size
//        动态大小（也称为缓存线程池）
//        在需要处理任务时创建一个新线程。
//        空闲线程等待60秒以执行新任务，然后在任务队列为空时终止。
//        因此，线程池随着要执行的任务的数量而增长和收缩。
//        这种类型的池是用executors.newCachedThreadPool（）创建的。

        final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);

    }

    public void temp() {
        //TODO  生成一个线程池：最多同时执行一个线程
        final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


        //TODO Fixed:固定的==>生成固定数量线程的线程池。
        // TODO: 2019/11/25 线程运行结束后，需要显式调用(explicitly) #shutdown() 来终止线程
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        ((ThreadPoolExecutor) fixedThreadPool).setCorePoolSize(4);
        final int corePoolSize = ((ThreadPoolExecutor) fixedThreadPool).getCorePoolSize();
        System.out.println("corePoolSize = " + corePoolSize);


        showMaxAvailableThread();
    }

    private void showMaxAvailableThread() {
        // TODO: 2019/11/25 4核 -->4个进程
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("可用进程树  = " + availableProcessors);
    }
}
