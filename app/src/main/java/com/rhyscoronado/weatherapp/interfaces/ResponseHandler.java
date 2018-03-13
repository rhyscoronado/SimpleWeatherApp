package com.rhyscoronado.weatherapp.interfaces;

/**
 * Created by rlcoronado on 03/13/2017.
 */

public interface ResponseHandler {

    public void onSuccess(int requestCode, Object object);
    public void onFailed(int requestCode, String message);

}
