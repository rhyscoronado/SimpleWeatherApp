package com.rhyscoronado.weatherapp.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhyscoronado.weatherapp.R;
import com.rhyscoronado.weatherapp.adapter.WeatherListAdapter;
import com.rhyscoronado.weatherapp.constants.Constants;
import com.rhyscoronado.weatherapp.model.Weather;
import com.rhyscoronado.weatherapp.model.WeatherMain;
import com.rhyscoronado.weatherapp.util.UnitConvertor;
import com.rhyscoronado.weatherapp.util.WeatherUtil;

/**
 * Created by rhysc on 3/12/18.
 */

public class WeatherInfoFragment extends Fragment {

    private View rootView;
    private WeatherMain weather;

    private RecyclerView recyclerView;
//    private ImageView ivWeatherImage;
//    private TextView tvCityCountry;
//    private TextView tvTemperature;
//    private TextView tvWeatherDescription;
//    private TextView tvHumidity;
//    private TextView tvSunrise;
//    private TextView tvSunset;


    public void setWeatherData(WeatherMain weather) {

        this.weather = weather;
        showWeatherData();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_weather_list, container, false);

        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(rootView);

    }

    private void initViews(View rootView) {

        recyclerView = rootView.findViewById(R.id.rvWeatherList);

//        ivWeatherImage = rootView.findViewById(R.id.ivWeatherImage);
//        tvCityCountry = rootView.findViewById(R.id.tvCityCountry);
//        tvHumidity = rootView.findViewById(R.id.tvHumidity);
//        tvTemperature = rootView.findViewById(R.id.tvTemperature);
//        tvWeatherDescription = rootView.findViewById(R.id.tvWeatherDescription);

    }


    private void showWeatherData() {

        WeatherListAdapter weatherListAdapter = new WeatherListAdapter(getActivity(), weather);
        final LinearLayoutManager weatherLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(weatherLayoutManager);
        recyclerView.setAdapter(weatherListAdapter);

//        ivWeatherImage.setImageBitmap(WeatherUtil.getWeatherIcon(weather.getList().getWeather().get(0).getIcon(), getActivity()));
//        tvCityCountry.setText(String.format("%s, %s", weather.getCity().getName(), weather.getCity().getCountry()));
//        tvTemperature.setText(String.format("%s °C", String.valueOf(Math.round(UnitConvertor.convertTemperature(weather.getList().getMain().getTemp().floatValue())))));
//        tvHumidity.setText(String.format("Humidity: %d%s", Math.round(weather.getList().getMain().getHumidity()), "%"));
//
//        String description = weather.getList().getWeather().get(0).getDescription();
//        description = description = description.substring(0,1).toUpperCase() + description.substring(1);
//        tvWeatherDescription.setText(description);

//        saveLastWeatherInfo(weather);



    }

    /**
     * Save last downloaded weather data for offline mode
     */

    private void saveLastWeatherInfo(WeatherMain weather){

//        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
//        editor.putBoolean(Constants.CACHED, true);
//        editor.putString(Constants.CITY, weather.getCity().getName());
//        editor.putString(Constants.COUNTRY, weather.getCity().getCountry());
//        editor.putFloat(Constants.TEMPERATURE, weather.getList().getMain().getTemp().floatValue());
//        editor.putString(Constants.WEATHER_INFO, weather.getList().getWeather().get(0).getMain());
//        editor.putString(Constants.WEATHER_DESCRIPTION, weather.getList().getWeather().get(0).getDescription());
//        editor.putString(Constants.WEATHER_ICON, weather.getList().getWeather().get(0).getIcon());
//        editor.putFloat(Constants.HUMIDITIY, (float) weather.getList().getMain().getHumidity());
//        editor.apply();
    }


}
