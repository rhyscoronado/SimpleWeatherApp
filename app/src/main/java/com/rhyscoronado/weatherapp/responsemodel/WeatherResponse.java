package com.rhyscoronado.weatherapp.responsemodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhysc on 3/14/18.
 */

public class WeatherResponse<T> implements Parcelable {

    private CoordinatesResponse coord;
    private List<WeatherDataResponse> weather = new ArrayList<>();
    private String base;
    private WeatherMainResponse main;
    private WindResponse wind;
    private CloudResponse clouds;
    private long dt;
    private WeatherSysResponse sys;
    private int id;
    private String name;
    private int cod;


    public CoordinatesResponse getCoord() {
        return coord;
    }

    public void setCoord(CoordinatesResponse coord) {
        this.coord = coord;
    }

    public WeatherDataResponse getWeather() {

        //For now we can return the first result from the list

        return weather.get(0);
    }

    public void setWeather(List<WeatherDataResponse> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public WeatherMainResponse getMain() {
        return main;
    }

    public void setMain(WeatherMainResponse main) {
        this.main = main;
    }

    public WindResponse getWind() {
        return wind;
    }

    public void setWind(WindResponse wind) {
        this.wind = wind;
    }

    public CloudResponse getClouds() {
        return clouds;
    }

    public void setClouds(CloudResponse clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public WeatherSysResponse getSys() {
        return sys;
    }

    public void setSys(WeatherSysResponse sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.coord, flags);
        dest.writeTypedList(this.weather);
        dest.writeString(this.base);
        dest.writeParcelable(this.main, flags);
        dest.writeParcelable(this.wind, flags);
        dest.writeParcelable(this.clouds, flags);
        dest.writeLong(this.dt);
        dest.writeParcelable(this.sys, flags);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.cod);
    }

    public WeatherResponse() {
    }

    protected WeatherResponse(Parcel in) {
        this.coord = in.readParcelable(CoordinatesResponse.class.getClassLoader());
        this.weather = in.createTypedArrayList(WeatherDataResponse.CREATOR);
        this.base = in.readString();
        this.main = in.readParcelable(WeatherMainResponse.class.getClassLoader());
        this.wind = in.readParcelable(WindResponse.class.getClassLoader());
        this.clouds = in.readParcelable(CloudResponse.class.getClassLoader());
        this.dt = in.readLong();
        this.sys = in.readParcelable(WeatherSysResponse.class.getClassLoader());
        this.id = in.readInt();
        this.name = in.readString();
        this.cod = in.readInt();
    }

    public static final Parcelable.Creator<WeatherResponse> CREATOR = new Parcelable.Creator<WeatherResponse>() {
        @Override
        public WeatherResponse createFromParcel(Parcel source) {
            return new WeatherResponse(source);
        }

        @Override
        public WeatherResponse[] newArray(int size) {
            return new WeatherResponse[size];
        }
    };

    public static class WeatherResponseInstance<T> implements InstanceCreator<WeatherResponse<T>> {

        @Override
        public WeatherResponse<T> createInstance(Type type) {
            return new WeatherResponse<T>();
        }
    }
}
