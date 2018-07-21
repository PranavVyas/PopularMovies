package com.pro.vyas.pranav.popularmovies.recyclerUtils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.DetailActivity;
import com.pro.vyas.pranav.popularmovies.models.MovieModel;
import com.pro.vyas.pranav.popularmovies.R;
import com.robertlevonyan.views.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.*;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private static final String TAG = "MovieAdapter";
    private Context ct;
    private List<MovieModel> movie;

    public MovieAdapter(Context ct) {
        this.ct = ct;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.movie_single_holder, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.tvTitle.setText(movie.get(position).getTitle());

        Picasso.get()
                .load(baseUrlPoster + movie.get(position).getPoster_path())
                .placeholder(R.drawable.loading_new)
                .error(R.drawable.ic_loading)
                .into(holder.ivPoster);
        final int pos = position;
        holder.flowGenre.removeAllViews();
        Chip chip = new Chip(ct);
        chip.setChipText(movie.get(position).getVote_average() + "/10");
        chip.setHasIcon(true);
        chip.setChipIcon(ct.getResources().getDrawable(R.drawable.ic_star_rounded));
        chip.setTextColor(R.color.colorPrimary_text);
        chip.setStrokeColor(R.color.colorBlue);
        chip.setStrokeSize(4);
        chip.changeBackgroundColor(ct.getResources().getColor(R.color.colorWhite));
        holder.flowGenre.addView(chip);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, DetailActivity.class);
                Gson gson = new Gson();
                intent.putExtra("MovieJSONString", gson.toJson(movie.get(pos)));
                ct.startActivity(intent);
            }
        };

        holder.ivPoster.setOnClickListener(listener);
        holder.tvTitle.setOnClickListener(listener);
        holder.flowGenre.setOnClickListener(listener);
        holder.ivmovieUppar.setOnClickListener(listener);
        holder.ivmovieLower.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        if (movie == null) {
            return 0;
        } else {
            return movie.size();
        }
    }


    class MovieHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        final FlowLayout flowGenre;
        ImageView ivPoster;
        CardView ivmovieUppar;
        CardView ivmovieLower;
        ImageView ivPosterBack;

        MovieHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.text_title_recycler);
            flowGenre = itemView.findViewById(R.id.flowlayout_genre_recycler);
            ivPoster = itemView.findViewById(R.id.image_movie_poster_recycler);
            ivmovieLower = itemView.findViewById(R.id.image_movie_lower_recycler);
            ivmovieUppar = itemView.findViewById(R.id.image_movie_uppar_recycler);
            ivPosterBack = itemView.findViewById(R.id.image_backdrop_detail);
        }
    }

    public void setMovies(List<MovieModel> movieModels) {
        movie = movieModels;
        notifyDataSetChanged();
    }
}
