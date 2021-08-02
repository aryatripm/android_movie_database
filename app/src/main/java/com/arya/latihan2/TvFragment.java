package com.arya.latihan2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arya.latihan2.adapter.TvAdapter;
import com.arya.latihan2.api.ApiClient;
import com.arya.latihan2.api.ApiService;
import com.arya.latihan2.databinding.FragmentTvBinding;
import com.arya.latihan2.entity.Tv;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvFragment extends Fragment {

    private FragmentTvBinding binding;
    private ArrayList<Tv> tvs = new ArrayList<>();
    private ApiService api;

    private TvAdapter adapter;

    private int totalPages = 500;
    private int currentPage = 1;

    private static TvFragment tvFragment;

    private TvFragment() {}
    public static TvFragment newInstance() {
        if (tvFragment == null) {
            tvFragment = new TvFragment();
        }
        return tvFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ApiClient.getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new TvAdapter(tvs, tv -> detailTv(tv));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2, GridLayoutManager.VERTICAL, false);
        binding = FragmentTvBinding.inflate(inflater, container, false);
        binding.dataList.setLayoutManager(gridLayoutManager);

        binding.loadingSpinner.setVisibility(View.VISIBLE);
        loadTvs();
        binding.dataList.setAdapter(adapter);

        binding.dataList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.dataList.canScrollVertically(1)) {
                    binding.loadingSpinner.setVisibility(View.VISIBLE);
                    if (currentPage <= totalPages) {
                        currentPage++;
                        loadTvs();
                    }
                }
            }
        });

        return binding.getRoot();
    }

    private void loadTvs(){
        callPopularTvs().enqueue(new Callback<Tv>() {
            @Override
            public void onResponse(Call<Tv> call, Response<Tv> response) {
                if (response.body() != null){
                    if (response.body().getResult() != null) {
                        binding.loadingSpinner.setVisibility(View.GONE);
                        int oldCount = tvs.size();

                        tvs.addAll(response.body().getResult());
                        adapter.notifyItemChanged(oldCount, tvs.size());
                    }
                }
            }

            @Override
            public void onFailure(Call<Tv> call, Throwable t) {
                binding.loadingSpinner.setVisibility(View.GONE);
                Toast.makeText(getActivity().getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void detailTv(Tv tv){

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("tv", tv.getId());
        getActivity().startActivity(intent);

    }

    private List<Tv> fetchTv (Response<Tv> response) {
        Tv tv = response.body();
        return tv.getResult();
    }

    private Call<Tv> callPopularTvs() {
        return api.getPopularTv("API_KEY", "en-US",currentPage);
    }

}
