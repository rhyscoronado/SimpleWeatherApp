package com.rhyscoronado.weatherapp.responsemodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rhysc on 3/14/18.
 */

public class WeatherSysResponse implements Parcelable {

    private double message;
    private String country;
    private float sunrise;
    private float sunset;


    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getSunrise() {
        return sunrise;
    }

    public void setSunrise(float sunrise) {
        this.sunrise = sunrise;
    }

    public float getSunset() {
        return sunset;
    }

    public void setSunset(float sunset) {
        this.sunset = sunset;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.message);
        dest.writeString(this.country);
        dest.writeFloat(this.sunrise);
        dest.writeFloat(this.sunset);
    }

    public WeatherSysResponse() {
    }

    protected WeatherSysResponse(Parcel in) {
        this.message = in.readDouble();
        this.country = in.readString();
        this.sunrise = in.readFloat();
        this.sunset = in.readFloat();
    }

    public static final Parcelable.Creator<WeatherSysResponse> CREATOR = new Parcelable.Creator<WeatherSysResponse>() {
        @Override
        public WeatherSysResponse createFromParcel(Parcel source) {
            return new WeatherSysResponse(source);
        }

        @Override
        public WeatherSysResponse[] newArray(int size) {
            return new WeatherSysResponse[size];
        }
    };
}
