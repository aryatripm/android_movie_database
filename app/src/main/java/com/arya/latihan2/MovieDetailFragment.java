package com.arya.latihan2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arya.latihan2.api.ApiClient;
import com.arya.latihan2.api.ApiService;
import com.arya.latihan2.databinding.FragmentDetailBinding;
import com.arya.latihan2.entity.Movies;
import com.arya.latihan2.entity.Tv;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailFragment extends Fragment implements View.OnClickListener {

    private FragmentDetailBinding binding;
    private static MovieDetailFragment movieDetailFragment;
    private ApiService api;

    private Movies movie;

    private MovieDetailFragment() {}

    public static MovieDetailFragment newInstance() {
        if (movieDetailFragment == null) {
            movieDetailFragment = new MovieDetailFragment();
        }
        return movieDetailFragment;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null && getArguments().containsKey(Movies.MOVIE_PARCEL)) {
            int movie_id = getArguments().getInt(Movies.MOVIE_PARCEL);

            callDetailedMovie(movie_id).enqueue(new Callback<Movies>() {
                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {
                    movie = response.body();
                    setMovieData(movie);
                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ApiClient.getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnMovie) {
            Toast.makeText(this.getContext(), "Berhasil share!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setMovieData(Movies movie) {
        binding.loadingDetails.setVisibility(View.GONE);
        binding.scrollView.setVisibility(View.VISIBLE);
        binding.imagePosterBack.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load("https://www.themoviedb.org/t/p/w220_and_h330_face" + movie.getPoster_path()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.imagePosterBack);
        Glide.with(getContext()).load("https://www.themoviedb.org/t/p/original" + movie.getBackdrop_path()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.imageBackdrop);
        Glide.with(getContext()).load("https://www.themoviedb.org/t/p/w220_and_h330_face" + movie.getPoster_path()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.imagePoster);
        binding.textTitle.setText(movie.getOriginal_title());
        binding.textRuntime.setText("60 Minutes");
        binding.textReleaseDate.setText("Release Date : " + movie.getRelease_date());
        binding.textOverview.setText(movie.getOverview());
        binding.textlanguage.setText("English");
        binding.textStatus.setText("Released");
        binding.textBudgetOrSeasons.setText("161900000 USD");
        binding.textRevenueOrEpisodes.setText("93967605 USD");
        binding.textPopularity.setText("Popularity : " + movie.getPopularity());
        binding.textTagline.setText("Tagline Example");
        binding.textVoteAverage.setText("Rating : " + movie.getVote_average());
        binding.textVoteCount.setText("1987 Voters");
        binding.textHomepage.setText("Genre : " + movie.getGenres().toString()
                .replace("[", "")
                .replace("]", "")
                .trim());
//        binding.imageBackDetails.setOnClickListener(view -> getActivity().onBackPressed());
    }

    private Call<Movies> callDetailedMovie(int movie_id) {
        return api.getDetailedMovie(movie_id,"API_KEY","en-US");
    }

}
