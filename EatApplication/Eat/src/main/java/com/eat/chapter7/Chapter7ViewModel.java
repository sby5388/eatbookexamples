package com.eat.chapter7;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2020/8/20.
 */
public class Chapter7ViewModel extends AndroidViewModel {
    public Chapter7ViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<String> mLiveData;

    public MutableLiveData<String> getLiveData() {
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<>();
            loadDta();
        }
        return mLiveData;
    }

    private void loadDta() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                SystemClock.sleep(TimeUnit.SECONDS.toMillis(5));
                return "come from background";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mLiveData.setValue(s);
            }
        }.execute();
    }
}
