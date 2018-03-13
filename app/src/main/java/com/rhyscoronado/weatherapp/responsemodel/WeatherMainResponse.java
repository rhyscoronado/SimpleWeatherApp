package com.rhyscoronado.weatherapp.responsemodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rhysc on 3/14/18.
 */

public class WeatherMainResponse implements Parcelable{

    private float temp;
    private double pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;
    private double sea_leavel;
    private double grnd_level;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getSea_leavel() {
        return sea_leavel;
    }

    public void setSea_leavel(double sea_leavel) {
        this.sea_leavel = sea_leavel;
    }

    public double getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(double grnd_level) {
        this.grnd_level = grnd_level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.temp);
        dest.writeDouble(this.pressure);
        dest.writeInt(this.humidity);
        dest.writeDouble(this.temp_min);
        dest.writeDouble(this.temp_max);
        dest.writeDouble(this.sea_leavel);
        dest.writeDouble(this.grnd_level);
    }

    public WeatherMainResponse() {
    }

    protected WeatherMainResponse(Parcel in) {
        this.temp = in.readFloat();
        this.pressure = in.readDouble();
        this.humidity = in.readInt();
        this.temp_min = in.readDouble();
        this.temp_max = in.readDouble();
        this.sea_leavel = in.readDouble();
        this.grnd_level = in.readDouble();
    }

    public static final Creator<WeatherMainResponse> CREATOR = new Creator<WeatherMainResponse>() {
        @Override
        public WeatherMainResponse createFromParcel(Parcel source) {
            return new WeatherMainResponse(source);
        }

        @Override
        public WeatherMainResponse[] newArray(int size) {
            return new WeatherMainResponse[size];
        }
    };
}
