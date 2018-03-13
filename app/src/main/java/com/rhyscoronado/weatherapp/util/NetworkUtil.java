package com.rhyscoronado.weatherapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.reflect.Method;

/**
 * Created by rhysc on 3/14/18.
 */

public class NetworkUtil {
    /**
     * Detects if the network is available (ie. State is CONNECTED)
     *
     * @param context The Activity context
     * @return true if network is connected and false otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return false;
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();

            // In certain cases we've seen an issue where if the user disables mobile data on the
            // device that
            // Attempt to determine if the user has disabled mobile data on the device
            boolean mobileDataEnabled = false; // Assume disabled
            try {
                Class cmClass = Class.forName(cm.getClass().getName());
                Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
                method.setAccessible(true); // Make the method callable
                mobileDataEnabled = (Boolean) method.invoke(cm);
            } catch (Exception e) {
                // Default to true so that in the case of an API change we don't break network calls.
                mobileDataEnabled = true;
            }

            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE &&
                                mobileDataEnabled) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}