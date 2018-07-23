package com.rhyscoronado.weatherapp.interfaces;

/**
 * Created by rlcoronado on 03/13/2017.
 */

public interface ResponseHandler {

    public void onResponse(boolean isSuccess, int requestCode, String object);

}
