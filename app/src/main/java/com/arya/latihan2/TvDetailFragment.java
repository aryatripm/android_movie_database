package com.arya.latihan2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class TvDetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private static TvDetailFragment tvDetailFragment;
    private ApiService api;

    private Tv tv;

    private TvDetailFragment() {}

    public static TvDetailFragment newInstance() {
        if (tvDetailFragment == null) {
            tvDetailFragment = new TvDetailFragment();
        }
        return tvDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ApiClient.getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments().containsKey(Tv.TV_PARCEL)) {
            int tv_id = getArguments().getInt(Tv.TV_PARCEL);

            callDetailedTv(tv_id).enqueue(new Callback<Tv>() {
                @Override
                public void onResponse(Call<Tv> call, Response<Tv> response) {
                    setTvData(response.body());
                }

                @Override
                public void onFailure(Call<Tv> call, Throwable t) {

                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTvData(Tv tv){
        binding.loadingDetails.setVisibility(View.GONE);
        binding.scrollView.setVisibility(View.VISIBLE);
        binding.imagePosterBack.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load("https://www.themoviedb.org/t/p/w220_and_h330_face" + tv.getPoster_path()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.imagePosterBack);
        Glide.with(getContext()).load("https://www.themoviedb.org/t/p/original" + tv.getBackdrop_path()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.imageBackdrop);
        Glide.with(getContext()).load("https://www.themoviedb.org/t/p/w220_and_h330_face" + tv.getPoster_path()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(binding.imagePoster);
        binding.textTitle.setText(tv.getName());
        binding.textRuntime.setText("60 Minutes");
        binding.textReleaseDate.setText("Release Date : ");
        binding.textOverview.setText(tv.getOverview());
        binding.textlanguage.setText("English");
        binding.textStatus.setText("Released");
        binding.textBudgetOrSeasons.setText("161900000 USD");
        binding.textRevenueOrEpisodes.setText("93967605 USD");
        binding.textPopularity.setText("Popularity : " + tv.getPoster_path());
        binding.textTagline.setText("Tagline Example");
        binding.textVoteAverage.setText("Rating : " + tv.getVote_average());
        binding.textVoteCount.setText("1987 Voters");
        binding.textHomepage.setText("Genre : " + tv.getGenres().toString()
                .replace("[", "")
                .replace("]", "")
                .trim());
//        binding.imageBackDetails.setOnClickListener(view -> getActivity().onBackPressed());
    }

    private Call<Tv> callDetailedTv(int id) {
        return api.getDetailedTv(id, "API_KEY","en-US");
    }
}
