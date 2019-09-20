package com.eat.chapter7;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eat.R;


public class ThreadRetainWithFragmentActivity extends AppCompatActivity implements ThreadFragment.Callback {

    private static final String TAG = "ThreadRetainActivity";

    private static final String KEY_TEXT = "key_text";
    private static final String TAG_THREAD_FRAGMENT = "threadFragment";
    private ThreadFragment mThreadFragment;

    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retain_thread);
        mTextView = (TextView) findViewById(R.id.text_retain);

        final FragmentManager manager = getSupportFragmentManager();

        mThreadFragment = (ThreadFragment) manager.findFragmentByTag(TAG_THREAD_FRAGMENT);

        if (mThreadFragment == null) {
            FragmentTransaction transaction = manager.beginTransaction();
//            mThreadFragment = new ThreadFragment();
            transaction.add(new ThreadFragment(), TAG_THREAD_FRAGMENT);
            transaction.commit();
        }
    }

    @Override
    public void onSetText(String string) {
        setText(string);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ThreadFragment) {
            mThreadFragment = (ThreadFragment) fragment;
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTextView.setText(savedInstanceState.getString(KEY_TEXT));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT, (String) mTextView.getText());
    }

    // Method called to start a worker thread
    public void onStartThread(View v) {
        mThreadFragment.execute();
    }

    public void setText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(text);
            }
        });
    }
}