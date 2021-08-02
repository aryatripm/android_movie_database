package com.arya.latihan2.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private int id;
    @SerializedName("original_title")
    private String title;
    @SerializedName("name")
    private String name;
    private String poster_path;
    private String profile_path;
    private String media_type;

    private List<SearchResult> results = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", media_type='" + media_type + '\'' +
                ", results=" + results +
                '}';
    }
}
