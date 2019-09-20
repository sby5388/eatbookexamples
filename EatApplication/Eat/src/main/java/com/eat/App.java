package com.eat;

import android.app.Application;
import android.os.StrictMode;

/**
 * @author Administrator  on 2019/7/27.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.enableDefaults();
    }
}
