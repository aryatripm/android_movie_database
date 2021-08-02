package com.arya.latihan2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arya.latihan2.R;
import com.arya.latihan2.databinding.TvItemBinding;
import com.arya.latihan2.entity.Tv;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private List<Tv> tvs;
    private TvItemListener listener;


    public TvAdapter(List<Tv> tvs, TvItemListener listener){
        this.tvs = tvs;
        this.listener = listener;
    }

    public void setItems(List<Tv> tvs) { this.tvs = tvs;}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.SetTvData(tvs.get(position));
        holder.itemView.setOnClickListener(view -> {listener.TvClicked(tvs.get(position));});
    }

    @Override
    public int getItemCount() {
        return tvs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TvItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TvItemBinding.bind(itemView);
        }

        public void SetTvData(Tv tv){
            binding.textView2.setText(tv.getName());
            Glide.with(this.itemView.getContext())
                    .load("https://www.themoviedb.org/t/p/w92" + tv.getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.imageView2);
        }
    }

    public interface TvItemListener {
        void TvClicked(Tv tv);
    }
}