package com.pro.vyas.pranav.popularmovies;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.AsyncTaskUtils.LoadGenreAsyncTask;
import com.pro.vyas.pranav.popularmovies.ExtraUtils.Converter;
import com.pro.vyas.pranav.popularmovies.Models.MovieModel;
import com.robertlevonyan.views.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pro.vyas.pranav.popularmovies.ConstantUtils.Constants.baseUrlPoster;
import static com.pro.vyas.pranav.popularmovies.ConstantUtils.Constants.baseUrlPosterBackground;
import static com.pro.vyas.pranav.popularmovies.RecyclerUtils.MovieAdapter.*;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @BindView(R.id.text_title_detail) TextView tvTitle;
    @BindView(R.id.text_genre_detail) TextView tvGenre;
    @BindView(R.id.text_rating_detail) TextView tvRating;
    @BindView(R.id.text_release_detail) TextView tvRelease;
    @BindView(R.id.text_synopsis_detail) TextView tvSynopsis;
    @BindView(R.id.text_total_votes_detail) TextView tvTotalVotes;
    @BindView(R.id.image_poster_detail) ImageView ivPoster;
    @BindView(R.id.image_backdrop_detail) ImageView ivPosterBackground;

    FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        flowLayout = findViewById(R.id.flow_genre_detail);

        bindUI(intent);

    }

    public void bindUI(Intent intent){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (intent.hasExtra("MovieJSONString")){
            Gson gson = new Gson();
            MovieModel model = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
            tvRating.setText("IMDB Rating :\n"+model.getVote_average());
            tvRelease.setText(model.getRelease_date());
            tvTotalVotes.setText("Total Votes :\n"+model.getVote_count());
            tvTitle.setText(model.getTitle());
            tvSynopsis.setText(model.getOverview());
            String[] genres = new String[model.getGenre_ids().size()];
            LoadGenreAsyncTask loadGenreAsyncTask = new LoadGenreAsyncTask(this,model.getGenre_ids().toArray(genres),flowLayout);
            //Converter converter = new Converter(this);
            loadGenreAsyncTask.execute();
            //converter.Convert(this,model.getGenre_ids().toArray(genres),tvGenre);
            //List<String> genreList = Converter.Convert(this,model.getGenre_ids().toArray(genres),tvGenre);
            //String str = tvGenre.getText().toString();
            //Log.d(TAG, "bindUI: Size of String is "+genreList.size()+"\nString is "+str);

            Picasso.get()
                    .load(baseUrlPoster+model.getPoster_path())
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_loading)
                    .into(ivPoster);
            Picasso.get()
                    .load(baseUrlPosterBackground+model.getBackdrop_path())
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_loading)
                    .into(ivPosterBackground);

            actionBar.setTitle(model.getTitle());

//            for (int i =0; i < genreList.size(); i++){
//                Chip chip = new Chip(this);
//                chip.setChipText(genreList.get(i));
//                chip.changeBackgroundColor(Color.RED);
//                flowLayout.addView(chip);
//            }

        }
    }
}
