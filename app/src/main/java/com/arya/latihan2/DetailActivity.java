package com.arya.latihan2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.arya.latihan2.api.ApiClient;
import com.arya.latihan2.api.ApiService;
import com.arya.latihan2.databinding.ActivityDetailBinding;
import com.arya.latihan2.entity.Movies;
import com.arya.latihan2.entity.Tv;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private ApiService api;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = ApiClient.getClient().create(ApiService.class);

        Bundle bundle = new Bundle();

        int movieID = getIntent().getIntExtra("movie",1);
        int TvID = getIntent().getIntExtra("tv",1);

        TvDetailFragment tvDetailFragment = TvDetailFragment.newInstance();
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


        if (movieID != 1) {
            bundle.putInt(Movies.MOVIE_PARCEL, movieID);
            movieDetailFragment.setArguments(bundle);
            fragmentTransaction.replace(binding.activityDetail.getId(), movieDetailFragment);
        } else if (TvID != 1) {
            bundle.putInt(Tv.TV_PARCEL, TvID);
            tvDetailFragment.setArguments(bundle);
            fragmentTransaction.replace(binding.activityDetail.getId(), tvDetailFragment);
        }

        fragmentTransaction.commit();

//        callDetailedMovie(movieID).enqueue(new Callback<Movies>() {
//            @Override
//            public void onResponse(Call<Movies> call, Response<Movies> response) {
//
//                Movies movie = response.body();
//
//                Glide.with(DetailActivity.this)
//                    .load("https://www.themoviedb.org/t/p/w342" + movie.getBackdrop_path())
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .into(binding.detailMovieImg);
//                binding.detailMovieTitle.setText(movie.getOriginal_title());
//                binding.detailMoviePopularity.setText("Popularity : " + movie.getPopularity());
//                binding.detailMovieReleaseDate.setText("Release Date : " + movie.getRelease_date());
//                binding.detailMovieVote.setText("Rating : " + movie.getVote_average());
//                binding.detailMovieOverview.setText(movie.getOverview());
//                binding.detailMovieGenre.setText("Genre : " + movie.getGenres().toString()
//                        .replace("[", "")
//                        .replace("]", "")
//                        .trim());
//                binding.btnDetail.setOnClickListener(view -> {
//                    Toast.makeText(getBaseContext(), "Id : " + response.body().toString(), Toast.LENGTH_LONG).show();
//                });
//            }
//
//            @Override
//            public void onFailure(Call<Movies> call, Throwable t) {
//
//            }
//        });




//        Bundle bundle  =new Bundle();
//        bundle.putParcelable(Movies.MOVIE_PARCEL, value);
//        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance();
//        movieDetailFragment.setArguments(bundle);
////
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(binding.activityMain.getId(), movieDetailFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

    }
}