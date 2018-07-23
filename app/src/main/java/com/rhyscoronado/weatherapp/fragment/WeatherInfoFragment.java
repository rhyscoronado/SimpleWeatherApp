package com.rhyscoronado.weatherapp.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import com.rhyscoronado.weatherapp.interfaces.RecyclerItemClickListener;
import com.rhyscoronado.weatherapp.model.List;
import com.rhyscoronado.weatherapp.model.Weather;
import com.rhyscoronado.weatherapp.model.WeatherMain;
import com.rhyscoronado.weatherapp.util.UnitConvertor;
import com.rhyscoronado.weatherapp.util.WeatherUtil;

import java.util.Calendar;

/**
 * Created by rhysc on 3/12/18.
 */

public class WeatherInfoFragment extends Fragment {

    private View rootView;
    private WeatherMain weather;

    private RecyclerView recyclerView;
    private TextView ivWeatherImage;
    private TextView tvCityCountry;
    private TextView tvTemperature;
    private TextView tvWeatherDescription;


    public void setWeatherData(WeatherMain weather) {

        this.weather = weather;
        showWeatherData();
    }



    /**
     * Instantiation method
     * @param
     * @return Fragment instance
     */
    public static WeatherInfoFragment newInstance() {
        WeatherInfoFragment fragment = new WeatherInfoFragment();

        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
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

        ivWeatherImage = rootView.findViewById(R.id.ivWeatherImage);
        tvCityCountry = rootView.findViewById(R.id.tvCityCountry);
        tvTemperature = rootView.findViewById(R.id.tvTemperature);
        tvWeatherDescription = rootView.findViewById(R.id.tvWeatherDescription);

    }


    private void showWeatherData() {

        WeatherListAdapter weatherListAdapter = new WeatherListAdapter(getActivity(), weather);
        final LinearLayoutManager weatherLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(weatherLayoutManager);
        recyclerView.setAdapter(weatherListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, WeatherInfoDataFragment.newInstance(weather.getList().get(position)))
                        .addToBackStack("WEATHER")
                        .commit();
            }
        }));

        ivWeatherImage.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"));
        ivWeatherImage.setText(WeatherUtil.setWeatherIcon(weather.getList().get(0).getWeather().get(0).getId(), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), getActivity()));
        tvCityCountry.setText(String.format("%s, %s", weather.getCity().getName(), weather.getCity().getCountry()));
        tvTemperature.setText(String.format("%s °C", String.valueOf(Math.round(UnitConvertor.convertTemperature(weather.getList().get(0).getMain().getTemp().floatValue())))));

//        tvHumidity.setText(String.format("Humidity: %d%s", Math.round(weather.getList().getMain().getHumidity()), "%"));
//
        String description = weather.getList().get(0).getWeather().get(0).getDescription();
        description = description = description.substring(0,1).toUpperCase() + description.substring(1);
        tvWeatherDescription.setText(description);

    }




}
