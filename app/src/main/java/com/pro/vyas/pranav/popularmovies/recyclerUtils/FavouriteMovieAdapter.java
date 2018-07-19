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

import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.DetailActivity;
import com.pro.vyas.pranav.popularmovies.R;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;
import com.robertlevonyan.views.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPoster;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.movieHolder>{

    Context context;
    List<MovieEntry> movieList;

    public FavouriteMovieAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public movieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_single_holder,parent,false);
        return new movieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieHolder holder, int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());

        Picasso.get()
                .load(baseUrlPoster+movieList.get(position).getPoster_path())
                .placeholder(R.drawable.loading_new)
                .error(R.drawable.ic_loading)
                .into(holder.ivPoster);

        final int pos = position;
        holder.flowGenre.removeAllViews();
        Chip chip = new Chip(context);
        chip.setChipText(movieList.get(position).getVote_average()+"/10");
        chip.setHasIcon(true);
        chip.setChipIcon(context.getResources().getDrawable(R.drawable.ic_star_rounded));
        chip.setTextColor(R.color.colorPrimary_text);
        chip.setStrokeColor(R.color.colorBlue);
        chip.setStrokeSize(4);
        chip.changeBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        holder.flowGenre.addView(chip);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, DetailActivity.class);
                Gson gson= new Gson();
                intent.putExtra("MovieJSONString",gson.toJson(movieList.get(pos)));
                intent.putExtra("LoadTrailers",DetailActivity.DONT_LOAD_TRAILER);
                context.startActivity(intent);
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
        if (movieList == null){ return 0;}
        else { return movieList.size(); }
    }

    class movieHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        final FlowLayout flowGenre;
        ImageView ivPoster;
        CardView ivmovieUppar;
        CardView ivmovieLower;
        ImageView ivPosterBack;

        public movieHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.text_title_recycler);
            flowGenre = itemView.findViewById(R.id.flowlayout_genre_recycler);
            ivPoster = itemView.findViewById(R.id.image_movie_poster_recycler);
            ivmovieLower = itemView.findViewById(R.id.image_movie_lower_recycler);
            ivmovieUppar = itemView.findViewById(R.id.image_movie_uppar_recycler);
            ivPosterBack = itemView.findViewById(R.id.image_backdrop_detail);
        }
    }

    public void setFavouriteMovies(List<MovieEntry> movieModelList){
        this.movieList = movieModelList;
        notifyDataSetChanged();
    }
}
