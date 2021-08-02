package com.arya.latihan2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arya.latihan2.adapter.MovieAdapter;
import com.arya.latihan2.api.ApiClient;
import com.arya.latihan2.api.ApiService;
import com.arya.latihan2.databinding.FragmentMainBinding;
import com.arya.latihan2.entity.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieFragment extends Fragment {

    private FragmentMainBinding binding;
    private ArrayList<Movies> movies = new ArrayList<>();
    private static MovieFragment movieFragment;
    private ApiService api;

    private MovieAdapter adapter;
    private Call<Movies> moviesCall;

    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 20;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int pageNum = 1;

    private MovieFragment() {

    }

    public static MovieFragment newInstance() {
        if (movieFragment == null) {
            movieFragment = new MovieFragment();
        }
        return movieFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = ApiClient.getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new MovieAdapter(movies, movie -> MovieDetails(movie));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding = FragmentMainBinding.inflate(inflater,container, false);
        binding.rvMovie.setLayoutManager(manager);
        binding.rvMovie.setAdapter(adapter);

        binding.refresh.setOnRefreshListener(() -> {
            binding.refresh.setRefreshing(false);
        });

        binding.rvMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    pastVisiblesItems = manager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }

                    if (!loading && (totalItemCount - visibleItemCount) <= (pastVisiblesItems + visibleThreshold)) {
                        // End has been reached

                        Log.i("Yaeye!", "end called");

                        // Do something
                        pageNum++;
                        loadMovies(adapter);

                        loading = true;
                    }
                }
            }
        });

        if (movies.isEmpty()){
            loadMovies(adapter);
        }

        return binding.getRoot();

    }


    private void MovieDetails (Movies movie) {

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("movie", movie.getId());
        getActivity().startActivity(intent);


    }

    private void loadMovies(MovieAdapter adapter){
        callPopularMovies().enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                List<Movies> result = fetchMovies(response);

                int old = movies.size();
                movies.addAll(result);
                adapter.notifyItemChanged(old, movies.size());


                }

                @Override
            public void onFailure(Call<Movies> call, Throwable t) {
            }
        });
    }

    private List<Movies> fetchMovies (Response<Movies> response) {
        Movies movie = response.body();
        return movie.getResult();
    }

    private Call<Movies> callPopularMovies(){
        return api.getPopularMovies("API_KEY","en-US",pageNum);
    }

    private Call<Movies> callTopRatedMovies() {
        return api.getTopRatedMovies("APIKEY","en-US",pageNum);
    }
}
