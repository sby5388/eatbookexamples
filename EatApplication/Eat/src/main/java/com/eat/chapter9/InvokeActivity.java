package com.eat.chapter9;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


public class InvokeActivity extends AppCompatActivity {

//AsyncTask

    private static final String TAG = "InvokeActivity";

    private TextView textStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoke);
        textStatus = (TextView) findViewById(R.id.text_status);
    }


    public void onButtonClick(View v) {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        // TODO: 2019/11/26 切换了2次线程
        Log.d(TAG, "onButtonClick: thread = " + Thread.currentThread().getName());

        simpleExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // TODO: 2019/9/21 java并发编程的Callable，有返回值的线程
                // TODO: 2019/9/21 Callable<T>,Future<T> 相关类的使用

                final List<Callable<String>> tasks = new ArrayList<>();
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

                // TODO: 2019/11/26 固定2个核心线程的线程池
                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
                try {
                    Log.d(TAG, "invokeAll");
                    List<Future<String>> futures = executor.invokeAll(tasks);
                    Log.d(TAG, "invokeAll after");

                    final String mashedData = mashupResult(futures);
                    Log.d(TAG, "run0: thread = " + Thread.currentThread().getName());

                    // TODO: 2019/11/26 作用如同在Activity# runOnUiThread
                    textStatus.post(new Runnable() {
                        @Override
                        public void run() {
                            textStatus.setText(mashedData);
                            Log.d(TAG, "run1: thread = " + Thread.currentThread().getName());
                        }
                    });
                    Log.d(TAG, "mashedData = " + mashedData);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                // TODO: 2019/11/26 需要显式 调用停止的方法 
                executor.shutdown();
            }
        });
    }


    /**
     * Partial:部分
     * 从网络中获取第一部分的数据
     */
    private String getFirstPartialDataFromNetwork() {
        Log.d(TAG, "ProgressReportingTask 1 started");
        SystemClock.sleep(3000);
        Log.d(TAG, "ProgressReportingTask 1 done");
        return "MockA";
    }

    /**
     * Partial:部分
     * 从网络中获取第二部分的数据
     */
    private String getSecondPartialDataFromNetwork() {
        Log.d(TAG, "ProgressReportingTask 2 started");
        SystemClock.sleep(2000);
        Log.d(TAG, "ProgressReportingTask 2 done");
        return "MockB";
    }

    /**
     * mashup ：混搭，处理多个数据
     */
    private String mashupResult(List<Future<String>> futures) throws ExecutionException, InterruptedException {
        StringBuilder builder = new StringBuilder();
        for (Future<String> future : futures) {
            builder.append(future.get());
        }
        return builder.toString();
    }

    // TODO: 2019/11/5 将这些框架用于实际的网络请求之中？
}