package com.eat.temp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

/**
 * @author Administrator  on 2019/10/31.
 */
public class ConnectedTemp {


    private void temp(Context context) {
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Toast.makeText(context, "版本不支持", Toast.LENGTH_SHORT).show();
            return;
        }
        if (manager == null) {
            throw new NullPointerException();
        }
        final NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        final int networkInfoType = activeNetworkInfo.getType();


    }
}
