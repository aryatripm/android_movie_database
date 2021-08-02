package com.arya.latihan2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arya.latihan2.R;
import com.arya.latihan2.SearchActivity;
import com.arya.latihan2.databinding.SearchItemBinding;
import com.arya.latihan2.entity.SearchResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private List<SearchResult> results;
    private ResultListener listener;

    public SearchAdapter(List<SearchResult> results, ResultListener listener) {
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(results.get(position));
        holder.itemView.setOnClickListener(view -> {listener.ResultClicked(results.get(position));});
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private SearchItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SearchItemBinding.bind(itemView);
        }

        public void setData(SearchResult result){
            String text;
            String img;

            if (result.getMedia_type() == "person"){
                text = result.getName();
                img = result.getProfile_path();
            } else if (result.getMedia_type() == "movie") {
                text = result.getTitle();
                img = result.getPoster_path();
            } else {
                text = result.getName();
                img = result.getPoster_path();
            }

            binding.textView.setText(text);
            Glide.with(this.itemView)
                    .load("https://www.themoviedb.org/t/p/original" + img)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.imageView);

        }

    }

    public interface ResultListener {
        void ResultClicked(SearchResult result);
    }
}
