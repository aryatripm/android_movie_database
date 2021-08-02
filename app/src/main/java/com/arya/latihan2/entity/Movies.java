package com.arya.latihan2.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Movies implements Parcelable {

    public static final String MOVIE_PARCEL = "movie";

    private int id;
    private String original_title;
    private double popularity;
    private String overview;
    private String release_date;
    private double vote_average;
    private String poster_path;
    private String backdrop_path;
    private List<Genres> genres;

    private List<Movies> results = new ArrayList<>();


    protected Movies(Parcel in) {
        id = in.readInt();
        original_title = in.readString();
        popularity = in.readDouble();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        poster_path = in.readString();
        backdrop_path = in.readString();
        genres = in.readParcelable(Genres.class.getClassLoader());
        results = in.createTypedArrayList(Movies.CREATOR);
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Genres> getGenres() { return genres; }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + id +
                ", original_title='" + original_title + '\'' +
                ", popularity=" + popularity +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average=" + vote_average +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", genres=" + genres +
                ", results=" + results +
                '}';
    }



    public List<Movies> getResult() {
        return results;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(original_title);
        parcel.writeDouble(popularity);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeDouble(vote_average);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeTypedList(genres);
        parcel.writeTypedList(results);
    }
}
