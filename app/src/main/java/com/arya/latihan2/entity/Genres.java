package com.arya.latihan2.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Genres implements Parcelable {

    private int id;
    private String name;
    private List<Genres> results = new ArrayList<>();

    protected Genres(Parcel in) {
        id = in.readInt();
        name = in.readString();
        results = in.createTypedArrayList(Genres.CREATOR);
    }

    public static final Creator<Genres> CREATOR = new Creator<Genres>() {
        @Override
        public Genres createFromParcel(Parcel in) {
            return new Genres(in);
        }

        @Override
        public Genres[] newArray(int size) {
            return new Genres[size];
        }
    };

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

    public List<Genres> getResult() {
        return results;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
