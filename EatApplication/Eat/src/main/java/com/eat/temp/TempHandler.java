package com.eat.temp;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/8/29.
 */
public class TempHandler {

    private void temp(Handler handler){
        handler.post(new Runnable() {
            @Override
            public void run() {
                System.out.println();
            }
        });
        handler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {

            }
        });
        // TODO: 2019/8/29  与postDelayed相比，这里用的是绝对时间
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {

            }
        },System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(2));
        // TODO: 2019/8/29 相对时间
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },TimeUnit.MINUTES.toMillis(2));


    }
}
