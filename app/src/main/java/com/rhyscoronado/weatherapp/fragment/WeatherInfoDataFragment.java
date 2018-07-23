package com.rhyscoronado.weatherapp.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rhyscoronado.weatherapp.R;
import com.rhyscoronado.weatherapp.model.List;
import com.rhyscoronado.weatherapp.util.UnitConvertor;
import com.rhyscoronado.weatherapp.util.WeatherUtil;

import java.util.Calendar;

/**
 * Created by rhysc on 3/12/18.
 */

public class WeatherInfoDataFragment extends Fragment {

    private static final String ARGS_LIST = "args_list";

    private View rootView;

    private RecyclerView recyclerView;
    private TextView ivWeatherImage;
    private TextView tvTemperature;
    private TextView tvWeatherDescription;
    private TextView tvHumidity;
    private TextView tvPressure, tvCloudy, tvWind;

    private static List weather;


    /**
     * Instantiation method
     * @param
     * @param list
     * @return Fragment instance
     */
    public static WeatherInfoDataFragment newInstance(List list) {
        WeatherInfoDataFragment fragment = new WeatherInfoDataFragment();

        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        setWeatherData(list);
        return fragment;
    }

    private static void setWeatherData(List list){
        weather = list;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_weather_info, container, false);

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

        ivWeatherImage = rootView.findViewById(R.id.ivWeatherImage);
        tvWeatherDescription = rootView.findViewById(R.id.tvWeatherDescription);
        tvTemperature = rootView.findViewById(R.id.tvTemperature);
        tvCloudy = rootView.findViewById(R.id.tvClouds);
        tvPressure = rootView.findViewById(R.id.tvSunrise);
        tvWind = rootView.findViewById(R.id.tvWind);
        tvHumidity = rootView.findViewById(R.id.tvHumidity);
        showWeatherData();

    }


    private void showWeatherData() {

        ivWeatherImage.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"));
        ivWeatherImage.setText(WeatherUtil.setWeatherIcon(weather.getWeather().get(0).getId(), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), getActivity()));
        tvPressure.setText(String.format("Pressure: %s", String.valueOf(weather.getMain().getPressure())));
        tvTemperature.setText(String.format("%s °C", String.valueOf(Math.round(UnitConvertor.convertTemperature(weather.getMain().getTemp().floatValue())))));
        tvHumidity.setText(String.format("Humidity: %d%s", Math.round(weather.getMain().getHumidity()), "%"));
        tvCloudy.setText(String.format("Cloud: %s", weather.getClouds().getAll()));
        tvWind.setText(String.format("Wind: %s", weather.getWind().getSpeed()));
        String description = weather.getWeather().get(0).getDescription();
        description = description.substring(0,1).toUpperCase() + description.substring(1);
        tvWeatherDescription.setText(description);

    }




}
