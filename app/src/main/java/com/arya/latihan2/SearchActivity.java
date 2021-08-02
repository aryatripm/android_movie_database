package com.arya.latihan2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.arya.latihan2.adapter.SearchAdapter;
import com.arya.latihan2.api.ApiClient;
import com.arya.latihan2.api.ApiService;
import com.arya.latihan2.databinding.ActivitySearchBinding;
import com.arya.latihan2.entity.SearchResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private ApiService api;
    private String query;

    private List<SearchResult> searchResults = new ArrayList<>();

    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = ApiClient.getClient().create(ApiService.class);


        handleIntent(getIntent());


        adapter = new SearchAdapter(searchResults, result -> getDetailed(result));
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.rvSearch.setLayoutManager(manager);
        binding.rvSearch.setAdapter(adapter);

        fetchSearchResult(query);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    private void getDetailed(SearchResult result) {
        String text = "";
        if (result.getMedia_type().equals("person")){
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            if (result.getMedia_type().equals("movie")) {
                intent.putExtra("movie", result.getId());
            } else if (result.getMedia_type().equals("tv")) {
                intent.putExtra("tv", result.getId());
            }
            startActivity(intent);
        }
    }

    private void fetchSearchResult(String query) {
        callSearchResult(query).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                searchResults.addAll(response.body().getResults());
                adapter.notifyItemChanged(searchResults.size());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(SearchActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private Call<SearchResult> callSearchResult(String query) {
        return api.getSearch("API_KEY","en-US",query,1);
    }
}