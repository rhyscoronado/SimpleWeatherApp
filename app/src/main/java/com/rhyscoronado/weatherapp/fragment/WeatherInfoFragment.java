package com.rhyscoronado.weatherapp.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhyscoronado.weatherapp.R;
import com.rhyscoronado.weatherapp.constants.Constants;
import com.rhyscoronado.weatherapp.model.Weather;
import com.rhyscoronado.weatherapp.util.UnitConvertor;
import com.rhyscoronado.weatherapp.util.WeatherUtil;

/**
 * Created by rhysc on 3/12/18.
 */

public class WeatherInfoFragment extends Fragment {

    private View _rootView;
    private Weather _weather;

    private ImageView _ivWeatherImage;
    private TextView _tvCityCountry;
    private TextView _tvTemperature;
    private TextView _tvWeatherDescription;
    private TextView _tvHumidity;
    private TextView _tvSunrise;
    private TextView _tvSunset;


    public void setWeatherData(Weather weather) {

        _weather = weather;
        showWeatherData();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (_rootView == null) {
            _rootView = inflater.inflate(R.layout.fragment_weather_info, container, false);
        }

        return _rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(_rootView);

    }

    private void initViews(View rootView) {

        _ivWeatherImage = rootView.findViewById(R.id.ivWeatherImage);
        _tvCityCountry = rootView.findViewById(R.id.tvCityCountry);
        _tvHumidity = rootView.findViewById(R.id.tvHumidity);
        _tvTemperature = rootView.findViewById(R.id.tvTemperature);
        _tvWeatherDescription = rootView.findViewById(R.id.tvWeatherDescription);

    }


    private void showWeatherData() {

        _ivWeatherImage.setImageBitmap(WeatherUtil.getWeatherIcon(_weather.getWeatherIcon(), getActivity()));
        _tvCityCountry.setText(String.format("%s, %s", _weather.getCity(), _weather.getCountry()));
        _tvTemperature.setText(String.format("%s °C", String.valueOf(Math.round(UnitConvertor.convertTemperature(_weather.getTemperature())))));
        _tvHumidity.setText(String.format("Humidity: %d%s", Math.round(_weather.getHumidity()), "%"));

        String description = _weather.getWeatherDescription();
        description = description = description.substring(0,1).toUpperCase() + description.substring(1);
        _tvWeatherDescription.setText(description);

        saveLastWeatherInfo(_weather);



    }

    /**
     * Save last downloaded weather data for offline mode
     */

    private void saveLastWeatherInfo(Weather weather){

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        editor.putBoolean(Constants.CACHED, true);
        editor.putString(Constants.CITY, weather.getCity());
        editor.putString(Constants.COUNTRY, weather.getCountry());
        editor.putFloat(Constants.TEMPERATURE, weather.getTemperature());
        editor.putString(Constants.WEATHER_INFO, weather.getWeather());
        editor.putString(Constants.WEATHER_DESCRIPTION, weather.getWeatherDescription());
        editor.putString(Constants.WEATHER_ICON, weather.getWeatherIcon());
        editor.putFloat(Constants.HUMIDITIY, (float) weather.getHumidity());
        editor.apply();
    }


}
