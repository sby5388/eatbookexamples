package com.eat.chapter9;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eat.R;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 图片下载
 */
public class ECSImageDownloaderActivity extends AppCompatActivity {

    private static final String TAG = "ECSImageDownloaderActivity";

    private LinearLayout layoutImages;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecs_image_downloader);
        layoutImages = (LinearLayout) findViewById(R.id.layout_images);

        DownloadCompletionService ecs = new DownloadCompletionService(Executors.newCachedThreadPool());
        new ConsumerThread(ecs).start();

        // TODO: 2019/10/30 一共模拟下载了五个图片
        for (int i = 0; i < 5; i++) {
            // TODO: 2019/7/27 类型未经检查!
            ecs.submit(new ImageDownloadTask());
        }

        ecs.shutdown();
    }


    private void addImage(final Bitmap image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView iv = new ImageView(ECSImageDownloaderActivity.this);
                iv.setImageBitmap(image);
                layoutImages.addView(iv);
            }
        });
    }


    private class ImageDownloadTask implements Callable<Bitmap> {

        @Override
        public Bitmap call() throws Exception {
            return downloadRemoteImage();
        }

        private Bitmap downloadRemoteImage() {
            // TODO: 2019/7/26 模拟下载图片耗时操作
            SystemClock.sleep((int) (5000f - new Random().nextFloat() * 5000f));
            return BitmapFactory.decodeResource(ECSImageDownloaderActivity.this.getResources(), R.drawable.ic_launcher);
        }
    }

    // TODO: 2019/11/26  ExecutorCompletionService ???
    private class DownloadCompletionService extends ExecutorCompletionService {

        private ExecutorService mExecutor;

        private DownloadCompletionService(ExecutorService executor) {
            super(executor);
            mExecutor = executor;
        }

        private void shutdown() {
            mExecutor.shutdown();
        }

        private boolean isTerminated() {
            return mExecutor.isTerminated();
        }
    }

    private class ConsumerThread extends Thread {

        private DownloadCompletionService mEcs;

        private ConsumerThread(DownloadCompletionService ecs) {
            this.mEcs = ecs;
        }

        @Override
        public void run() {
            super.run();
            try {
                while (!mEcs.isTerminated()) {
                    Future<Bitmap> future = mEcs.poll(1, TimeUnit.SECONDS);
                    if (future != null) {
                        addImage(future.get());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}