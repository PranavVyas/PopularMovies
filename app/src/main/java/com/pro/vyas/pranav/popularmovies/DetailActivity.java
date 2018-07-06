package com.pro.vyas.pranav.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.asyncTaskUtils.LoadGenreAsyncTask;
import com.pro.vyas.pranav.popularmovies.models.MovieModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPoster;
import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPosterBackground;

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
    @BindView(R.id.toolbar_detail) Toolbar toolbarDetail;
    @BindView(R.id.text_toolbar_title_detail) TextView tvToolbarTitleDetail;
    @BindView(R.id.chip_favourite_detail) TextView btnAddFavourites;

    FlowLayout flowLayout;
    static boolean isAdded = true;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        intent = getIntent();
        flowLayout = findViewById(R.id.flow_genre_detail);
        setSupportActionBar(toolbarDetail);
        bindUI(intent);
        if (isAdded){
            btnAddFavourites.setCompoundDrawablePadding(8);
            btnAddFavourites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_unfavourite_heart_filled_red, 0, 0, 0);
            btnAddFavourites.setBackground(this.getResources().getDrawable(R.drawable.button_unfavourite));
            btnAddFavourites.setText("Remove from Favourite");
        }else{
            btnAddFavourites.setCompoundDrawablePadding(8);
            btnAddFavourites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favourite_heart_fillled_red, 0, 0, 0);
            btnAddFavourites.setBackground(this.getResources().getDrawable(R.drawable.button_favourite));
            btnAddFavourites.setText("Add to Favourites");
        }
    }



    public void bindUI(Intent intent){
        ActionBar actionBar = getSupportActionBar();

        if (intent.hasExtra("MovieJSONString")){
            Gson gson = new Gson();
            MovieModel model = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
            tvRating.setText("IMDB Rating :\n"+model.getVote_average());
            tvRelease.setText(model.getRelease_date());
            tvTotalVotes.setText("Total Votes :\n"+model.getVote_count());
            tvTitle.setText(model.getTitle());
            tvSynopsis.setText(model.getOverview());
            LoadGenreAsyncTask loadGenreAsyncTask = new LoadGenreAsyncTask(this,model.getGenre_ids(),flowLayout);
            loadGenreAsyncTask.execute();

            Picasso.get()
                    .load(baseUrlPoster+model.getPoster_path())
                    .placeholder(R.drawable.loading_new)
                    .error(R.drawable.ic_loading)
                    .into(ivPoster);
            Picasso.get()
                    .load(baseUrlPosterBackground+model.getBackdrop_path())
                    .placeholder(R.drawable.loading_new)
                    .error(R.drawable.ic_loading)
                    .into(ivPosterBackground);
            
            ivPoster.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(DetailActivity.this, "Long Clicked Movie...", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            tvToolbarTitleDetail.setText(model.getTitle());
        }
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void favourite(View view) {
        MovieModel model = null;
        if (intent.hasExtra("MovieJSONString")) {
            Gson gson = new Gson();
            model = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
        }
        //SharedPreferences mainPrefs = getSharedPreferences("moviePrefs",Context.MODE_PRIVATE);
        //SharedPrefenceUtils sharedPrefenceUtils = new SharedPrefenceUtils(mainPrefs);
        if (isAdded){
            btnAddFavourites.setText("Add to Favourite");
            Toast.makeText(this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
            isAdded = false;
            addTofavourites(model);
            btnAddFavourites.setBackground(this.getResources().getDrawable(R.drawable.button_favourite));
            btnAddFavourites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favourite_heart_fillled_red, 0, 0, 0);

        }else{
            btnAddFavourites.setText("Remove from Favourites");
            Toast.makeText(this, "Added to Favourites", Toast.LENGTH_SHORT).show();
            isAdded = true;
            removeFromFavourite(model);
            btnAddFavourites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_unfavourite_heart_filled_red, 0, 0, 0);
            btnAddFavourites.setBackground(this.getResources().getDrawable(R.drawable.button_unfavourite));

        }
    }

    public void addTofavourites(MovieModel movie){

    }

    public void removeFromFavourite(MovieModel movie){

    }

}
