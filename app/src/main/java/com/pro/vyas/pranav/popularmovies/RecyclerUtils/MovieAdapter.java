package com.pro.vyas.pranav.popularmovies.RecyclerUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.vyas.pranav.popularmovies.Models.MovieModel;
import com.pro.vyas.pranav.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pro.vyas.pranav.popularmovies.MainActivity.base_url;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>
{

    private static final String TAG = "MovieAdapter";
    Context ct;
    List<String> titles;
    List<MovieModel> movie;
    private static final String baseUrlPoster = "http://image.tmdb.org/t/p/w185/";

    public MovieAdapter(Context ct,List<MovieModel> movie) {
        this.ct = ct;
        this.movie = movie;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.movie_single_holder,parent,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        //holder.tv.setText(titles.get(position));
        holder.tvTitle.setText(movie.get(position).getTitle());
        holder.tvGenre.setText("");
        for (int i = 0; i < movie.get(position).getGenre_ids().size(); i++){
            holder.tvGenre.append(movie.get(position).getGenre_ids().get(i) + " ,");
            Log.d(TAG, "onBindViewHolder: Size of genre is "+movie.get(position).getGenre_ids().get(i));
        }
        Log.d(TAG, "onBindViewHolder: Poster PAth is "+baseUrlPoster+movie.get(position).getPoster_path());
        Picasso.get()
                .load(baseUrlPoster+movie.get(position).getPoster_path())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_ticketlower)
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvGenre;
        ImageView ivPoster;
        public MovieHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.text_title_recycler);
            tvGenre = itemView.findViewById(R.id.text_genre_recycler);
            ivPoster = itemView.findViewById(R.id.image_movie_poster_recycler);
        }
    }
}
