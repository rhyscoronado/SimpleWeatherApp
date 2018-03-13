package com.rhyscoronado.weatherapp.responsemodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rhysc on 3/14/18.
 */

public class WindResponse implements Parcelable{

    private double speed;
    private double deg;


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.speed);
        dest.writeDouble(this.deg);
    }

    public WindResponse() {
    }

    protected WindResponse(Parcel in) {
        this.speed = in.readDouble();
        this.deg = in.readDouble();
    }

    public static final Creator<WindResponse> CREATOR = new Creator<WindResponse>() {
        @Override
        public WindResponse createFromParcel(Parcel source) {
            return new WindResponse(source);
        }

        @Override
        public WindResponse[] newArray(int size) {
            return new WindResponse[size];
        }
    };
}
