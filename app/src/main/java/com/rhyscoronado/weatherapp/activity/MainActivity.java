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

import com.android.volley.RequestQueue;
import com.rhyscoronado.weatherapp.R;
import com.rhyscoronado.weatherapp.api.WeatherApi;
import com.rhyscoronado.weatherapp.business.BaseApplication;
import com.rhyscoronado.weatherapp.constants.Constants;
import com.rhyscoronado.weatherapp.fragment.WeatherInfoFragment;
import com.rhyscoronado.weatherapp.interfaces.ResponseHandler;
import com.rhyscoronado.weatherapp.model.WeatherMain;

import com.rhyscoronado.weatherapp.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import parser.WeatherParser;


/**
 *
 */

public class MainActivity extends AppCompatActivity implements LocationListener, ResponseHandler {

    private static final String TAG = "Weather";
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private static final int RC_GET_CITY_WEATHER = 1001;

    //set naming convention for class variables having underline to prevent confusion for inline variables.

    private WeatherInfoFragment weatherInfoFragment;

    private RequestQueue requestQueue;
    private LocationManager locationManager;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;


    boolean destroyed = false;

    private TextView tvLastUpdated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        weatherInfoFragment = WeatherInfoFragment.newInstance();
        requestQueue = BaseApplication.getInstance().getRequestQueue();
        sharedPreferences = BaseApplication.getInstance().getSharedPreference();
//        tvLastUpdated = findViewById(R.id.tvLastUpdated);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, weatherInfoFragment, TAG)
                .commit();


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
        destroyed = true;

        if (locationManager != null) {
            try {
                locationManager.removeUpdates(MainActivity.this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Loads the weather data to the UI
     */

    private void loadWeatherToFragment(WeatherMain weather) {

        weatherInfoFragment.setWeatherData(weather);


    }

    private void loadCache() {

        SharedPreferences sp = BaseApplication.getInstance().getSharedPreference();

        if(sp.getBoolean(Constants.CACHED,false)) {

            WeatherMain weather = new WeatherMain();


            loadWeatherToFragment(weather);



        } else {

            Toast.makeText(this, "No connection/offline data available.",  Toast.LENGTH_SHORT).show();
            System.exit(0);

        }


    }

    private void getCityByLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //We can customize the message if needed.

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            }

        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.getting_location));
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        locationManager.removeUpdates(MainActivity.this);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            });
            progressDialog.show();
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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
        progressDialog.hide();
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            Log.e("LocationManager", "Error while trying to stop listening for location updates. This is probably a permissions issue", e);
        }
        Log.i("LOCATION (" + location.getProvider().toUpperCase() + ")", location.getLatitude() + ", " + location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        getWeatherForCity(latitude, longitude);

    }

    private void getWeatherForCity(String latitude, String longitude) {

        WeatherApi.FetchWeatherData task = new WeatherApi.FetchWeatherData(latitude, longitude, this);
        task.execute();

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
//        if (timeInMillis < 0) {
//            // No time
//            tvLastUpdated.setText("");
//        } else {
//            tvLastUpdated.setText(getString(R.string.last_update, DateTimeUtil.formatTimeWithDayIfNotToday(this, timeInMillis)));
//        }
    }

    @Override
    public void onResponse(boolean isSuccess, int requestCode, String object) {
        switch (requestCode) {
            case RC_GET_CITY_WEATHER:

                if(isSuccess) {

                    parseObject(object);

//                    saveLastUpdateTime(sharedPreferences);
//                    updateLastUpdateTime();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                break;
        }
    }

    private void parseObject(String object) {

        WeatherMain weatherMain = new WeatherMain();

        try {
            JSONObject weather = new JSONObject(object);
            weatherMain = WeatherParser.parseWeatherForecast(weather);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        loadWeatherToFragment(weatherMain);

    }
}
