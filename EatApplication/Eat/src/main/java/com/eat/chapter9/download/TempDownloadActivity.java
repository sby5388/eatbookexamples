package com.eat.chapter9.download;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eat.R;

/**
 * 使用
 * {@link DownloadManager}
 * 来实现下载的功能
 */
public class TempDownloadActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_EXTERNAL_STORAGE = 100;
    private static final int GET_DOWNLOAD_SAVE_PATH = 200;
    private static final String[] PERMISSION_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private EditText mEditTextInput;
    private TextView mTextView;

    private DownloadManager mDownloadManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_download);

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (mDownloadManager == null) {
            throw new NullPointerException("DownloadManager == null !");
        }

        mEditTextInput = findViewById(R.id.edittext_download_path);
        mTextView = findViewById(R.id.textview_save_path);

        findViewById(R.id.button_start_download).setOnClickListener(v -> startDownload());
        findViewById(R.id.button_select_save_path).setOnClickListener(v -> selectSavePath());

    }

    private void startDownload() {
        final String remotePath = mEditTextInput.getText().toString().trim();
        final String savePath = mTextView.getText().toString().trim();

        if (TextUtils.isEmpty(remotePath)) {
            Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO: 2019/10/31 checkPermission 获取相应的权限
        // TODO: 2019/11/5 检查网络是否可用


    }

    private void selectSavePath() {
        // TODO: 2019/11/5 文件访问权限
        boolean getExternalStorage = true;
        for (String permission : PERMISSION_EXTERNAL_STORAGE) {
            final int selfPermission = ActivityCompat.checkSelfPermission(this, permission);
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                getExternalStorage = false;
                break;
            }
        }
        if (!getExternalStorage) {
            ActivityCompat.requestPermissions(this, PERMISSION_EXTERNAL_STORAGE, REQUEST_CODE_EXTERNAL_STORAGE);
            return;
        }

        final Intent intent = BrowserFileActivity.newIntentSetSavePath(this);
        startActivityForResult(intent, GET_DOWNLOAD_SAVE_PATH);


    }


    /**
     * @param remote
     * @param save
     */
    private void download(final String remote, final String save) {
        // TODO: 2019/10/31 before downloading,should check networkState and networkType ,avoid use mobile network to download any big File
        final Uri uri = Uri.parse(remote);
        final DownloadManager.Request request = new DownloadManager.Request(uri);
        // TODO: 2019/10/31 只允许在WIFI网络下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // TODO: 2019/10/31 设置下载文件的保存位置
        // TODO: 2019/11/5 未完成
//        request.setDestinationInExternalFilesDir()


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_EXTERNAL_STORAGE) {
            boolean success = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    success = false;
                    break;
                }
            }
            if (success) {
                selectSavePath();
            } else {
                Toast.makeText(this, "需要开启读写文件权限才能正常工作", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_DOWNLOAD_SAVE_PATH) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // TODO: 2019/11/5 重新设定保存的位置
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
