package com.rhyscoronado.weatherapp.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.rhyscoronado.weatherapp.responsemodel.WeatherResponse;
import com.rhyscoronado.weatherapp.util.WeatherUtil;

import java.util.Calendar;

/**
 * Created by rhysc on 3/13/18.
 */

public class Weather implements Parcelable{

    private String _city;
    private float _temperature;
    private String _weather;
    private String _weatherDescription;
    private String _weatherIcon;
    private String _country;
    private double _humidity;
    private float _sunset;
    private float _sunrise;

    public Weather() {

    }

    public Weather(Context context, WeatherResponse weatherResponse) {

        _city = weatherResponse.getName();
        _temperature = weatherResponse.getMain().getTemp();
        _weather = weatherResponse.getWeather().getMain();
        _weatherDescription = weatherResponse.getWeather().getDescription();

        _country = weatherResponse.getSys().getCountry();
        _humidity = weatherResponse.getMain().getHumidity();
        _sunset = weatherResponse.getSys().getSunset();
        _sunrise = weatherResponse.getSys().getSunset();

        setWeatherIcon(weatherResponse.getWeather().getId(), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), context);

    }

    private void setWeatherIcon(int id, int hourOfDay, Context context) {

        //Not supposed to do methods for model but there are possible exceptions

        _weatherIcon = WeatherUtil.setWeatherIcon(id, hourOfDay, context);


    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        this._city = city;
    }

    public float getTemperature() {
        return _temperature;
    }

    public void setTemperature(float _temperature) {
        this._temperature = _temperature;
    }

    public String getWeather() {
        return _weather;
    }

    public void setWeather(String weather) {
        this._weather = weather;
    }

    public String getWeatherDescription() {
        return _weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this._weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return _weatherIcon;
    }

    public void setWeatherIcon(String _weatherIcon) {
        this._weatherIcon = _weatherIcon;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(String country) {
        this._country = country;
    }

    public double getHumidity() {
        return _humidity;
    }

    public void setHumidity(double humidity) {
        this._humidity = humidity;
    }

    public float getSunset() {
        return _sunset;
    }

    public void setSunset(float sunset) {
        this._sunset = sunset;
    }

    public float getSunrise() {
        return _sunrise;
    }

    public void setSunrise(float sunrise) {
        this._sunrise = sunrise;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._city);
        dest.writeFloat(this._temperature);
        dest.writeString(this._weather);
        dest.writeString(this._weatherDescription);
        dest.writeString(this._weatherIcon);
        dest.writeString(this._country);
        dest.writeDouble(this._humidity);
        dest.writeFloat(this._sunset);
        dest.writeFloat(this._sunrise);
    }

    protected Weather(Parcel in) {
        this._city = in.readString();
        this._temperature = in.readFloat();
        this._weather = in.readString();
        this._weatherDescription = in.readString();
        this._weatherIcon = in.readString();
        this._country = in.readString();
        this._humidity = in.readDouble();
        this._sunset = in.readFloat();
        this._sunrise = in.readFloat();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
