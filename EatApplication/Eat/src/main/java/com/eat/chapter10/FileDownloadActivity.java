package com.eat.chapter10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.eat.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;


public class FileDownloadActivity extends AppCompatActivity {

    private static final String[] DOWNLOAD_URLS = {
            "http://d.lanrentuku.com/down/png/1904/international_food.jpg",
            "http://d.lanrentuku.com/down/png/1904/fantasy_and_role_play_game.jpg",
            "http://d.lanrentuku.com/down/png/1904/social_media_2019.jpg",
            "http://d.lanrentuku.com/down/png/1904/food-icons-const.jpg"
    };

    private DownloadTask mFileDownloaderTask;

    // Views from layout file
    private ProgressBar mProgressBar;
    private LinearLayout mLayoutImages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_download);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setMax(DOWNLOAD_URLS.length);
        mLayoutImages = findViewById(R.id.layout_images);

        mFileDownloaderTask = new DownloadTask(this);
        mFileDownloaderTask.execute(DOWNLOAD_URLS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFileDownloaderTask.clearActivity();
        mFileDownloaderTask.cancel(true);
    }

    /**
     * 根据链接直接获取图片
     *
     * @param path
     * @return
     */
    private Bitmap temp(final String path) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(path).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 异步下载任务
     */
    private static class DownloadTask extends AsyncTask<String, Bitmap, Void> {

        private final WeakReference<FileDownloadActivity> mReference;
        private int mCount = 0;

        DownloadTask(FileDownloadActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        void clearActivity() {
            mReference.clear();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            final FileDownloadActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            activity.mProgressBar.setVisibility(View.VISIBLE);
            activity.mProgressBar.setProgress(0);
        }

        @Override
        protected Void doInBackground(String... urls) {
            for (String url : urls) {
                if (!isCancelled()) {
                    Bitmap bitmap = downloadFile(url);
                    publishProgress(bitmap);
                }
            }
            return null;
        }


        // TODO: 2019/7/27 进度值不一定要使用数值，也可以是其他的类型
        // TODO: 2019/11/5 更新进度时，同时更新UI
        @Override
        protected void onProgressUpdate(Bitmap... bitmaps) {
            super.onProgressUpdate(bitmaps);
            final FileDownloadActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            activity.mProgressBar.setProgress(++mCount);
            ImageView iv = new ImageView(activity);
            iv.setImageBitmap(bitmaps[0]);
            activity.mLayoutImages.addView(iv);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final FileDownloadActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            activity.mProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            final FileDownloadActivity activity = mReference.get();
            if (activity == null) {
                return;
            }
            activity.mProgressBar.setVisibility(View.GONE);
        }


        /**
         * runOnWorkerThread 在工作线程上运行
         *
         * @param url
         * @return
         */
        private Bitmap downloadFile(String url) {
            final int THREAD_ID = 10050;
            Bitmap bitmap = null;
            try {
                // TODO: 2019/11/5 根据url直接获取图片的方法
                bitmap = BitmapFactory
                        .decodeStream((InputStream) new URL(url)
                                .getContent());
                TrafficStats.setThreadStatsTag(THREAD_ID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

    }

    /**
     * runOnWorkerThread 在工作线程上运行
     *
     * @param url
     * @return
     */
    private Bitmap downloadFile(String url) {
        final int THREAD_ID = 10050;
        Bitmap bitmap = null;
        try {
            // TODO: 2019/11/5 根据url直接获取图片的方法
            bitmap = BitmapFactory
                    .decodeStream((InputStream) new URL(url)
                            .getContent());
            TrafficStats.setThreadStatsTag(THREAD_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}