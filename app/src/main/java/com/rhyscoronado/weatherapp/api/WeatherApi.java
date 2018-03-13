package com.rhyscoronado.weatherapp.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.rhyscoronado.weatherapp.constants.Constants;
import com.rhyscoronado.weatherapp.interfaces.ResponseHandler;

import com.rhyscoronado.weatherapp.responsemodel.WeatherResponse;
import com.rhyscoronado.weatherapp.util.GsonUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhysc on 3/13/18.
 */

public class WeatherApi {

    public static Request getWeather(final int requestCode, String lat, String lon, final ResponseHandler responseHandler) {

        String url = String.format("%s/%s?%s=%s&%s=%s&%s=%s",
                Constants.DOMAIN, Constants.WEATHER, Constants.LAT, lat, Constants.LON, lon, Constants.APPID, Constants.APPKEY);

        Request getCityWeather = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Gson gson = GsonUtility.createGsonBuilder(WeatherResponse.class, new WeatherResponse.WeatherResponseInstance()).create();
                WeatherResponse apiResponse = null;
                apiResponse = gson.fromJson(response.toString(), WeatherResponse.class);
                responseHandler.onSuccess(requestCode, apiResponse);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "An error occured.";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    message = "No connection available.";
                } else if (error instanceof AuthFailureError) {
                    message = "Authentication Failure.";
                } else if (error instanceof ServerError) {
                    message = "Server error.";
                } else if (error instanceof NetworkError) {
                    message = "Network Error.";
                } else if (error instanceof ParseError) {
                    message = "Parse error.";
                }
                responseHandler.onFailed(requestCode, message);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        return getCityWeather;

    }
}
