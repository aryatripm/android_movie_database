package com.arya.latihan2.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arya.latihan2.entity.Movies;
import com.arya.latihan2.R;
import com.arya.latihan2.databinding.MovieItemBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movies> movies;
    private MovieItemListener listener;

    public MovieAdapter(List<Movies> movies, MovieItemListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    public void setItems(List<Movies> movies) {
        this.movies = movies;
    }



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.setMovieData(movies.get(position));
        // holder.itemView.setOnClickListener(view -> listener.MovieClicked(movies.get(position)));
        holder.binding.btnMovie.setOnClickListener(view -> listener.MovieClicked(movies.get(position)));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }



    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding binding;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MovieItemBinding.bind(itemView);
        }

        @SuppressLint("SetTextI18n")
        public void setMovieData(Movies movie) {

            Glide.with(this.itemView.getContext())
                    .load("https://www.themoviedb.org/t/p/w92" + movie.getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.movieImg);

            binding.movieTitle.setText(movie.getOriginal_title());
            if (movie.getRelease_date() != null && movie.getRelease_date().length() > 3) {
                binding.movieDesc.setText(movie.getRelease_date().substring(0,4) + ", Rating : " +
                        movie.getVote_average());
            }
        }

    }

    public interface MovieItemListener{
        void MovieClicked(Movies movie);
    }
}
