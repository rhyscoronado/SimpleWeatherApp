package com.rhyscoronado.weatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhyscoronado.weatherapp.R;
import com.rhyscoronado.weatherapp.model.WeatherMain;
import com.rhyscoronado.weatherapp.util.UnitConvertor;
import com.rhyscoronado.weatherapp.util.WeatherUtil;


/**
 *
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.MyViewHolder> {

    private static final String TAG = "weatheradapter";

    private WeatherMain weatherMain;
    private Context context;

    public WeatherListAdapter(Context context, WeatherMain item) {
        this.weatherMain = item;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_weather, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final com.rhyscoronado.weatherapp.model.List weather = weatherMain.getList().get(position * 6);

        holder.ivWeatherIcon.setTypeface(WeatherUtil.getIcon(context));
        holder.ivWeatherIcon.setText(weather.getWeather().get(0).getIcon());
        holder.tvCountryName.setText(weatherMain.getCity().getName());
        holder.tvDate.setText(weather.getDtTxt());
        holder.tvTemperature.setText(String.format("%s °C", String.valueOf(Math.round(UnitConvertor.convertTemperature(weather.getMain().getTemp().floatValue())))));

    }

    @Override
    public int getItemCount() {
        return (weatherMain.getList().size()/6);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDate, tvCountryName, tvTemperature, ivWeatherIcon;


        public MyViewHolder(View view) {
            super(view);

            tvDate = view.findViewById(R.id.tvDate);
            tvCountryName = view.findViewById(R.id.tvCountryName);
            tvTemperature = view.findViewById(R.id.tvTemp);
            ivWeatherIcon = view.findViewById(R.id.ivImage);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

}

