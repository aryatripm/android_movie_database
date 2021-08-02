package com.arya.latihan2.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tv implements Parcelable {

    public static final String TV_PARCEL = "tv";

    private int id;
    private String name;
    private String overview;
    private double vote_average;
    private String backdrop_path;
    private String poster_path;
    private List<Genres> genres;

    private List<Tv> results = new ArrayList<>();


    protected Tv(Parcel in) {
        id = in.readInt();
        name = in.readString();
        overview = in.readString();
        vote_average = in.readDouble();
        backdrop_path = in.readString();
        poster_path = in.readString();
        genres = in.createTypedArrayList(Genres.CREATOR);
        results = in.createTypedArrayList(Tv.CREATOR);
    }

    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel in) {
            return new Tv(in);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<Genres> getGenres() { return genres; }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<Tv> getResult() {
        return results;
    }

    @Override
    public String toString() {
        return "Tv{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average=" + vote_average +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", genres=" + genres +
                ", results=" + results +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(overview);
        parcel.writeDouble(vote_average);
        parcel.writeString(backdrop_path);
        parcel.writeString(poster_path);
        parcel.writeTypedList(genres);
        parcel.writeTypedList(results);
    }
}
