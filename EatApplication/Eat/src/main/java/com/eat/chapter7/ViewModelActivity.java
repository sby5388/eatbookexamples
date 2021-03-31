package com.eat.chapter7;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eat.R;

/**
 * @author Administrator  on 2020/8/20.
 */
public class ViewModelActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    private Chapter7ViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        mTextView = findViewById(R.id.textView_show);
        mButton = findViewById(R.id.button_load);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        mViewModel = ViewModelProviders.of(this).get(Chapter7ViewModel.class);
        mViewModel.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (mTextView == null) {
                    return;
                }
                mTextView.setText(s);
            }
        });

    }

    private void loadData() {
        mViewModel.getLiveData();
    }
}
