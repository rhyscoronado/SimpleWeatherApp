package com.rhyscoronado.weatherapp.api;

import android.os.AsyncTask;
import android.util.Log;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhysc on 3/13/18.
 */

public class WeatherApi {

    public static class FetchWeatherData extends AsyncTask<Void, Void, String> {

        String lat, lon;
        ResponseHandler responseHandler;

        public FetchWeatherData(String latitude, String longitude, ResponseHandler handler) {
            lat = latitude;
            lon = longitude;
            responseHandler = handler;
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;

            try {
                URL url = new URL(String.format("%s?%s=%s&%s=%s&%s=%s",
                        Constants.DOMAIN, Constants.LAT, lat, Constants.LON, lon, Constants.APPID, Constants.APPKEY));

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                responseHandler.onResponse(false, 1001, "Something went wrong.");
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }

            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("json", s);
            responseHandler.onResponse(true, 1001, s);
        }
    }
}
