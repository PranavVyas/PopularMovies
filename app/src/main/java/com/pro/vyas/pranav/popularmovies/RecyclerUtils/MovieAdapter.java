package com.pro.vyas.pranav.popularmovies.RecyclerUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pro.vyas.pranav.popularmovies.DetailActivity;
import com.pro.vyas.pranav.popularmovies.ExtraUtils.Converter;
import com.pro.vyas.pranav.popularmovies.MainActivity;
import com.pro.vyas.pranav.popularmovies.Models.MovieModel;
import com.pro.vyas.pranav.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pro.vyas.pranav.popularmovies.ConstantUtils.Constants.*;
import static com.pro.vyas.pranav.popularmovies.MainActivity.currPage;
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>
{
    private static final String TAG = "MovieAdapter";
    private Context ct;
    private List<MovieModel> movie;

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
        int count = ((currPage - 1)*10) + position + 1;
        holder.tvCount.setText(count+"");

        Picasso.get()
                .load(baseUrlPoster+movie.get(position).getPoster_path())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_loading)
                .into(holder.ivPoster);

        final int pos = position;

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ct, DetailActivity.class);
                Gson gson= new Gson();
                intent.putExtra("MovieJSONString",gson.toJson(movie.get(pos)));
                ct.startActivity(intent);
            }
        };

        holder.ivPoster.setOnClickListener(listener);
        holder.tvTitle.setOnClickListener(listener);
        holder.tvGenre.setOnClickListener(listener);
        holder.ivmovieUppar.setOnClickListener(listener);
        holder.ivmovieLower.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvGenre;
        ImageView ivPoster;
        TextView tvCount;
        ImageView ivmovieUppar;
        ImageView ivmovieLower;
        ImageView ivPosterBack;

        public MovieHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.text_title_recycler);
            tvGenre = itemView.findViewById(R.id.text_genre_recycler);
            ivPoster = itemView.findViewById(R.id.image_movie_poster_recycler);
            tvCount = itemView.findViewById(R.id.text_count);
            ivmovieLower = itemView.findViewById(R.id.image_movie_lower_recycler);
            ivmovieUppar = itemView.findViewById(R.id.image_movie_uppar_recycler);
            ivPosterBack = itemView.findViewById(R.id.image_backdrop_detail);

        }
    }
}
