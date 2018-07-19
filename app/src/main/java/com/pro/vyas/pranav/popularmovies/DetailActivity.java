package com.pro.vyas.pranav.popularmovies;

import android.arch.persistence.room.Delete;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.asyncTaskUtils.LoadGenreAsyncTask;
import com.pro.vyas.pranav.popularmovies.asyncTaskUtils.LoadVideosAsyncTask;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieDatabase;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;
import com.pro.vyas.pranav.popularmovies.extraUtils.AlwaysMarqueeTextView;
import com.pro.vyas.pranav.popularmovies.models.DetailMovieModel;
import com.pro.vyas.pranav.popularmovies.models.MainDetailsMovieModel;
import com.pro.vyas.pranav.popularmovies.models.MovieModel;
import com.pro.vyas.pranav.popularmovies.models.VideosModel;
import com.pro.vyas.pranav.popularmovies.recyclerUtils.TrailerAdapter;
import com.robertlevonyan.views.chip.Chip;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPoster;
import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPosterBackground;
import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.youtubeBaseUrl;

public class DetailActivity extends AppCompatActivity implements LoadVideosAsyncTask.LoadTrailerAsyncTaskCallback{

    private static final String TAG = "DetailActivity";

    @BindView(R.id.text_title_detail) AlwaysMarqueeTextView tvTitle;
    @BindView(R.id.text_genre_detail) TextView tvGenre;
    @BindView(R.id.text_rating_detail) TextView tvRating;
    @BindView(R.id.text_release_detail) TextView tvRelease;
    @BindView(R.id.text_synopsis_detail) TextView tvSynopsis;
    @BindView(R.id.text_total_votes_detail) TextView tvTotalVotes;
    @BindView(R.id.image_poster_detail) ImageView ivPoster;
    @BindView(R.id.image_backdrop_detail) ImageView ivPosterBackground;
    @BindView(R.id.toolbar_detail) Toolbar toolbarDetail;
    @BindView(R.id.text_toolbar_title_detail) AlwaysMarqueeTextView tvToolbarTitleDetail;
    //@BindView(R.id.chip_favourite_detail) TextView btnAddFavourites;
    @BindView(R.id.bottom_navigation)BottomNavigationView bottomNavigation;
    @BindView(R.id.flow_genre_detail) FlowLayout flowLayout;
    @BindView(R.id.rv_videos_detail) RecyclerView rvTrailer;
    @BindView(R.id.loading_indicator_genre) AVLoadingIndicatorView loadingGenre;

    static boolean isAdded = false;
    static boolean isSaved = false;
    Intent intent;
    MovieModel modelForFavourite;
    private MovieDatabase mDb;
    private TrailerAdapter mTrailerAdapter;

    public static final int DEFAULT_LOAD_TRAILER = -1;
    public static final int DONT_LOAD_TRAILER = 0;
    public static final int DEFAULT_LOAD_GENRE = 12;
    public static final int LOAD_GENRE_FROM_DB = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarDetail);
        intent = getIntent();
        bindUI(intent);
        preLoadItems();
        initBottomNavigationView(intent);

//        if (isAdded){
//            btnAddFavourites.setCompoundDrawablePadding(8);
//            btnAddFavourites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_unfavourite_heart_filled_red, 0, 0, 0);
//            btnAddFavourites.setBackground(this.getResources().getDrawable(R.drawable.button_unfavourite));
//            btnAddFavourites.setText("Remove from Favourite");
//
//        }else{
//            btnAddFavourites.setCompoundDrawablePadding(8);
//            btnAddFavourites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favourite_heart_fillled_red, 0, 0, 0);
//            btnAddFavourites.setBackground(this.getResources().getDrawable(R.drawable.button_favourite));
//            btnAddFavourites.setText("Add to Favourites");
//
//        }
    }

    private void preLoadItems(){
        tvTitle.setAlwaysMarquee(true);
        tvToolbarTitleDetail.setAlwaysMarquee(true);
        mTrailerAdapter = new TrailerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTrailer.setLayoutManager(layoutManager);
        rvTrailer.setAdapter(mTrailerAdapter);
        loadingGenre.smoothToShow();
        mDb = MovieDatabase.getInstance(DetailActivity.this);
    }

    private void loadTrailers(String movieId) {
        LoadVideosAsyncTask asyncTask = new LoadVideosAsyncTask(this,this);
        asyncTask.loadMovieId(movieId);
        asyncTask.execute();
    }

    public void initBottomNavigationView(Intent intent){
        bottomNavigation.setItemIconTintList(null);
        MenuItem menuItemFavourite = bottomNavigation.getMenu().getItem(0);
        menuItemFavourite.setCheckable(false);

        if(intent.hasExtra("MovieJSONString")){
            Gson gson = new Gson();
            modelForFavourite = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
        }
        isAdded = checkAlreadyFavourite();
        //Init Bottom Navigation View now
        if(isAdded){
            menuItemFavourite.setTitle("Remove From Favourite");
            menuItemFavourite.setIcon(getResources().getDrawable(R.drawable.ic_unfavourite_heart_filled_red));
        }else{
            menuItemFavourite.setIcon(getResources().getDrawable(R.drawable.ic_favourite_heart_fillled_red));
            menuItemFavourite.setTitle("Add to Favourite");
        }
        MenuItem menuItemShare = bottomNavigation.getMenu().getItem(1);
        menuItemShare.setCheckable(false);
        //ON Click Listener for Bottom Navigatioj View
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.favourite_bottom_detail:
                        if (isAdded){
                            removeFromFavourite(modelForFavourite);
                            item.setIcon(getResources().getDrawable(R.drawable.ic_favourite_heart_fillled_red));
                            item.setTitle("Add to Favourite");
                        }else{
                            addTofavourites(modelForFavourite);
                            item.setIcon(getResources().getDrawable(R.drawable.ic_unfavourite_heart_filled_red));
                            item.setTitle("Remove from Favourite");
                        }
                        break;

                    case R.id.share_bottom_detail:

                        break;
                }
                return true;
            }
        });
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
            tvToolbarTitleDetail.setText(model.getTitle());
            loadGenre(model.getTag_Genre());
            if(intent.getIntExtra("LoadTrailers",DEFAULT_LOAD_TRAILER) == DEFAULT_LOAD_TRAILER){
                //Load Trailers
                rvTrailer.setVisibility(View.VISIBLE);
                loadTrailers(model.getId());
            }else{
                //Remove Trailers
                rvTrailer.setVisibility(View.GONE);
            }
        }
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Objects.requireNonNull(actionBar).setDisplayShowTitleEnabled(false);
    }

    private void loadGenre(String tag_genre) {
        Toast.makeText(this, "Genre is "+tag_genre, Toast.LENGTH_SHORT).show();
        loadingGenre.smoothToHide();
        flowLayout.removeAllViews();
        List<String> strings = new ArrayList<>(Arrays.asList(tag_genre.split(",")));
        for (String x: strings){
            Chip chip = new Chip(this);
            chip.setChipText(x);
            chip.changeBackgroundColor(getResources().getColor(R.color.colorWhite));
            chip.setStrokeColor(getResources().getColor(R.color.colorBlue));
            chip.setStrokeSize(3);
            flowLayout.addView(chip);
        }
    }

//    public void favourite() {
//        MovieModel model = null;
//        if (intent.hasExtra("MovieJSONString")) {
//            Gson gson = new Gson();
//            model = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
//        }
//        MenuItem menuItem = bottomNavigation.getMenu().getItem(0);
//        //SharedPreferences mainPrefs = getSharedPreferences("moviePrefs",Context.MODE_PRIVATE);
//        //SharedPrefenceUtils sharedPrefenceUtils = new SharedPrefenceUtils(mainPrefs);
//        if (isAdded){
//            //btnAddFavourites.setText("Add to Favourite");
//            menuItem.setTitle("Remove From Favourite");
//            menuItem.setIcon(getResources().getDrawable(R.drawable.ic_favourite_heart_fillled_red));
//            Toast.makeText(this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
//            isAdded = false;
//            addTofavourites(model);
//            //btnAddFavourites.setBackground(this.getResources().getDrawable(R.drawable.button_favourite));
//            //btnAddFavourites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favourite_heart_fillled_red, 0, 0, 0);
//
//        }else{
//            //btnAddFavourites.setText("Remove from Favourites");
//            menuItem.setIcon(getResources().getDrawable(R.drawable.ic_unfavourite_heart_filled_red));
//            menuItem.setTitle("Add to Favourite");
//            Toast.makeText(this, "Added to Favourites", Toast.LENGTH_SHORT).show();
//            isAdded = true;
//            removeFromFavourite(model);
//            //btnAddFavourites.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_unfavourite_heart_filled_red, 0, 0, 0);
//            //btnAddFavourites.setBackground(this.getResources().getDrawable(R.drawable.button_unfavourite));
//        }
//    }

    public void addTofavourites(MovieModel movie){
        isAdded = true;
        MovieEntry movieToAdd = new MovieEntry(movie);
        mDb.movieDao().insertMovie(movieToAdd);
        Toast.makeText(this, "Sucessfully added to favourites : "+movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public void removeFromFavourite(MovieModel movie){
        isAdded = false;
        MovieEntry movieToDelete = new MovieEntry(movie);
//        if(genreString.length() == 0){
//            Toast.makeText(this, "Genre is not loaded yet", Toast.LENGTH_SHORT).show();
//        }else{
//            movieToDelete.setGenre(genreString);
//            mDb.movieDao().deleteMovie(movieToDelete);
//            Toast.makeText(this, "Sucessfully removed from Favourites : "+movie.getTitle(), Toast.LENGTH_SHORT).show();
//        }
        mDb.movieDao().deleteMovie(movieToDelete);
        Toast.makeText(this, "Sucessfully removed from Favourites : "+movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public boolean checkAlreadyFavourite(){
        MovieEntry model = mDb.movieDao().getMovieById(modelForFavourite.getId());
        if(model == null){
            //Toast.makeText(this, "Not Favourite", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            //Toast.makeText(this, "Favourite Already", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void onComplete(DetailMovieModel detailMovieModel) {
        List<VideosModel> trailerModel = detailMovieModel.getResults();
        mTrailerAdapter.setTrailerList(trailerModel);
    }
}
