package com.rhyscoronado.weatherapp.responsemodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rhysc on 3/14/18.
 */

public class CoordinatesResponse implements Parcelable{

    private double lon;
    private double lat;

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {

        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public CoordinatesResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lon);
        dest.writeDouble(this.lat);
    }

    protected CoordinatesResponse(Parcel in) {
        this.lon = in.readDouble();
        this.lat = in.readDouble();
    }

    public static final Creator<CoordinatesResponse> CREATOR = new Creator<CoordinatesResponse>() {
        @Override
        public CoordinatesResponse createFromParcel(Parcel source) {
            return new CoordinatesResponse(source);
        }

        @Override
        public CoordinatesResponse[] newArray(int size) {
            return new CoordinatesResponse[size];
        }
    };
}
