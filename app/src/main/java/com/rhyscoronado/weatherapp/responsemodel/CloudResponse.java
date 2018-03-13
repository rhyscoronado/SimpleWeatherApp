package com.rhyscoronado.weatherapp.responsemodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rhysc on 3/14/18.
 */

public class CloudResponse implements Parcelable {
    private double all;


    public double getAll() {
        return all;
    }

    public void setAll(double all) {
        this.all = all;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.all);
    }

    public CloudResponse() {
    }

    protected CloudResponse(Parcel in) {
        this.all = in.readDouble();
    }

    public static final Parcelable.Creator<CloudResponse> CREATOR = new Parcelable.Creator<CloudResponse>() {
        @Override
        public CloudResponse createFromParcel(Parcel source) {
            return new CloudResponse(source);
        }

        @Override
        public CloudResponse[] newArray(int size) {
            return new CloudResponse[size];
        }
    };
}
