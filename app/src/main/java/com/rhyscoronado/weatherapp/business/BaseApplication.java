package com.rhyscoronado.weatherapp.business;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

/**
 * Created by rhysc on 3/14/18.
 */

public class BaseApplication extends Application {


    private static BaseApplication _instance;
    private RequestQueue _requestQueue;
    SharedPreferences _sharedPreference;

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;
        _sharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    }


    public static BaseApplication getInstance(){

        return _instance;

    }


    public SharedPreferences getSharedPreference() {

        return _sharedPreference;

    }


    public RequestQueue getRequestQueue() {

        if (_requestQueue == null) {
            _requestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        }
        return _requestQueue;
    }
}
