package com.sasuke.imagify.util;

/**
 * Created by abc on 5/1/2018.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sasuke.imagify.Imagify;

public class NetworkUtil {

    private NetworkUtil() {
    }

    private static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) Imagify.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isConnected() {
        NetworkInfo networkInfo = getConnectivityManager().getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected()
                && !networkInfo.isRoaming();
    }

}
