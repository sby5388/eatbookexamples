package com.eat.chapter9;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * invoke 调用
 *
 * @author Administrator  on 2019/10/29.
 */
class CopyInvokeActivity {
    private static final String TAG = CopyInvokeActivity.class.getSimpleName();

    /**
     * dump run on ui Thread
     *
     * @param runnable
     */
    private void runOnUiThread(Runnable runnable) {
        // TODO: 2019/10/29
        System.out.println("current ThreadName = " + Thread.currentThread().getName());
        final Activity activity = getActivity();
        activity.runOnUiThread(runnable);

    }

    private Activity getActivity() {
        throw new RuntimeException();
    }

    private void temp() {
        final SimpleExecutor simpleExecutor = new SimpleExecutor();
        simpleExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Callable<String>> tasks = new ArrayList<>();
                tasks.add(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return getFirstPartialDataFromNetwork();
                    }
                });

                tasks.add(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return getSecondPartialDataFromNetwork();
                    }
                });

                final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
                try {
                    Log.d(TAG, "run: invokeAll");
                    final List<Future<String>> futures = executor.invokeAll(tasks);
                    final String mashupResult = mashupResult(futures);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: 2019/10/29 模拟切换线程
                            System.out.println("result = " + mashupResult);
                        }
                    });

                    Log.d(TAG, "run: invokeAll after");

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //停止执行？
                // TODO: 2019/10/29 不再运行新增加的任务，但原来的任务会继续运行直到完成
                executor.shutdown();
            }
        });
    }

    /**
     * partial : 部分
     *
     * @return
     */
    private String getFirstPartialDataFromNetwork() {
        throw new RuntimeException();
    }

    /**
     * partial : 部分
     *
     * @return
     */
    private String getSecondPartialDataFromNetwork() {
        throw new RuntimeException();
    }

    /**
     * mashup : 混搭？
     *
     * @param futures
     * @return
     * @throws Exception
     */
    private String mashupResult(List<Future<String>> futures) throws ExecutionException {
        throw new RuntimeException();
    }
}
