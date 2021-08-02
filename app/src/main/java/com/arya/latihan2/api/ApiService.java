package com.arya.latihan2.api;

import com.arya.latihan2.entity.Movies;
import com.arya.latihan2.entity.SearchResult;
import com.arya.latihan2.entity.Tv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Call<Movies> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("movie/{id}")
    Call<Movies> getDetailedMovie(
            @Path("id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv/popular")
    Call<Tv> getPopularTv(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("tv/{id}")
    Call<Tv> getDetailedTv(
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("movie/top_rated")
    Call<Movies> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("search/multi")
    Call<SearchResult> getSearch(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page
    );
}
