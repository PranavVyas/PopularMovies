package com.pro.vyas.pranav.popularmovies.RecyclerUtils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.vyas.pranav.popularmovies.DetailActivity;
import com.pro.vyas.pranav.popularmovies.Models.MovieModel;
import com.pro.vyas.pranav.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>
{

    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_TITLE = "title";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_VOTE_AVERAGE = "votes";
    public static final String KEY_RATING = "rating";
    public static final String KEY_OVERVIEW = "synposis";
    public static final String KEY_RELEASE_DATE = "releaseDate";


    private static final String TAG = "MovieAdapter";
    private Context ct;
    private List<MovieModel> movie;
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
        }
        Picasso.get()
                .load(baseUrlPoster+movie.get(position).getPoster_path())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_ticketlower)
                .into(holder.ivPoster);

        final int pos = position;

        holder.ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, DetailActivity.class);
                intent.putExtra(KEY_POSTER_PATH,baseUrlPoster+movie.get(pos).getPoster_path());
                intent.putExtra(KEY_TITLE,movie.get(pos).getTitle());
                intent.putExtra(KEY_GENRE,movie.get(pos).getGenre_ids().toArray());
                intent.putExtra(KEY_RELEASE_DATE,movie.get(pos).getRelease_date());
                Log.d(TAG, "onClick: Release Date is "+movie.get(pos).getRelease_date());
                //Log.d(TAG, "onClick: Genre is ");
                intent.putExtra(KEY_VOTE_AVERAGE,movie.get(pos).getVote_average());
                intent.putExtra(KEY_RATING,movie.get(pos).getVote_count());
                intent.putExtra(KEY_OVERVIEW,movie.get(pos).getOverview());
                ct.startActivity(intent);
            }
        });

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
