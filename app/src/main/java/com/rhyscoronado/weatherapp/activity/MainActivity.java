package com.rhyscoronado.weatherapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.rhyscoronado.weatherapp.R;
import com.rhyscoronado.weatherapp.api.WeatherApi;
import com.rhyscoronado.weatherapp.business.BaseApplication;
import com.rhyscoronado.weatherapp.constants.Constants;
import com.rhyscoronado.weatherapp.fragment.WeatherInfoFragment;
import com.rhyscoronado.weatherapp.interfaces.ResponseHandler;
import com.rhyscoronado.weatherapp.responsemodel.WeatherResponse;

import com.rhyscoronado.weatherapp.model.Weather;
import com.rhyscoronado.weatherapp.util.DateTimeUtil;
import com.rhyscoronado.weatherapp.util.NetworkUtil;

import java.util.Calendar;


/**
 * Created by rhysc on 3/12/18.
 */

public class MainActivity extends AppCompatActivity implements LocationListener, ResponseHandler {

    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private static final int RC_GET_CITY_WEATHER = 1001;

    //set naming convention for class variables having underline to prevent confusion for inline variables.

    private WeatherInfoFragment _weatherInfoFragment;

    private RequestQueue _requestQueue;
    private LocationManager _locationManager;
    private ProgressDialog _progressDialog;
    private SharedPreferences _sharedPreferences;


    boolean _destroyed = false;

    private TextView _tvLastUpdated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        _weatherInfoFragment = (WeatherInfoFragment) getFragmentManager().findFragmentByTag("weather");
        _requestQueue = BaseApplication.getInstance().getRequestQueue();
        _sharedPreferences = BaseApplication.getInstance().getSharedPreference();
        _tvLastUpdated = findViewById(R.id.tvLastUpdated);



        if(NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            getCityByLocation();
        } else {
            loadCache();
            updateLastUpdateTime();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_refresh) {
            getCityByLocation();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _destroyed = true;

        if (_locationManager != null) {
            try {
                _locationManager.removeUpdates(MainActivity.this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Loads the weather data to the UI
     */

    private void loadWeatherToFragment(Weather weather) {

        _weatherInfoFragment.setWeatherData(weather);


    }

    private void loadCache() {

        SharedPreferences sp = BaseApplication.getInstance().getSharedPreference();

        if(sp.getBoolean(Constants.CACHED,false)) {

            Weather weather = new Weather();

            weather.setCity((sp.getString((Constants.CITY), "")));
            weather.setCountry((sp.getString((Constants.COUNTRY), "")));
            weather.setTemperature((sp.getFloat((Constants.TEMPERATURE), 0)));
            weather.setHumidity((sp.getFloat((Constants.HUMIDITIY), 0)));
            weather.setWeatherIcon((sp.getString((Constants.WEATHER_ICON), "")));
            weather.setWeatherDescription((sp.getString((Constants.WEATHER_DESCRIPTION), "")));
            weather.setWeather((sp.getString((Constants.WEATHER_INFO), "")));

            loadWeatherToFragment(weather);



        } else {

            Toast.makeText(this, "No connection/offline data available.",  Toast.LENGTH_SHORT).show();
            System.exit(0);

        }


    }

    private void getCityByLocation() {
        _locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //We can customize the message if needed.

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            }

        } else if (_locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                _locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            _progressDialog = new ProgressDialog(this);
            _progressDialog.setMessage(getString(R.string.getting_location));
            _progressDialog.setCancelable(false);
            _progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        _locationManager.removeUpdates(MainActivity.this);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            });
            _progressDialog.show();
            if (_locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                _locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
            if (_locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                _locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        } else {
            showLocationSettingsDialog();
        }
    }

    private void showLocationSettingsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.location_settings);
        alertDialog.setMessage(R.string.location_settings_message);
        alertDialog.setPositiveButton(R.string.location_settings_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCityByLocation();
                }
            }
        }
    }

    /**
     * Handles Location Changes
     */

    @Override
    public void onLocationChanged(Location location) {
        _progressDialog.hide();
        try {
            _locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            Log.e("LocationManager", "Error while trying to stop listening for location updates. This is probably a permissions issue", e);
        }
        Log.i("LOCATION (" + location.getProvider().toUpperCase() + ")", location.getLatitude() + ", " + location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        getWeatherForCity(latitude, longitude);

    }

    private void getWeatherForCity(String latitude, String longitude) {

        Request request = WeatherApi.getWeather(RC_GET_CITY_WEATHER, latitude, longitude, this);
        _requestQueue.add(request);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    /**
     * Handles webservice response
     */


    @Override
    public void onSuccess(int requestCode, Object object) {

        switch (requestCode) {
            case RC_GET_CITY_WEATHER:

                Weather weather = new Weather(getApplicationContext(), (WeatherResponse) object);
                loadWeatherToFragment(weather);

                saveLastUpdateTime(_sharedPreferences);
                updateLastUpdateTime();

                break;
        }

    }



    @Override
    public void onFailed(int requestCode, String message) {

        _progressDialog.hide();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }



    public static long saveLastUpdateTime(SharedPreferences sp) {
        Calendar now = Calendar.getInstance();
        sp.edit().putLong("lastUpdate", now.getTimeInMillis()).apply();
        return now.getTimeInMillis();
    }

    private void updateLastUpdateTime() {
        updateLastUpdateTime(
                PreferenceManager.getDefaultSharedPreferences(this).getLong("lastUpdate", -1)
        );
    }

    private void updateLastUpdateTime(long timeInMillis) {
        if (timeInMillis < 0) {
            // No time
            _tvLastUpdated.setText("");
        } else {
            _tvLastUpdated.setText(getString(R.string.last_update, DateTimeUtil.formatTimeWithDayIfNotToday(this, timeInMillis)));
        }
    }

}
