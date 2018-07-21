package com.pro.vyas.pranav.popularmovies;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieDatabase;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;
import com.pro.vyas.pranav.popularmovies.extraUtils.AlwaysMarqueeTextView;
import com.pro.vyas.pranav.popularmovies.models.MovieModel;
import com.pro.vyas.pranav.popularmovies.recyclerUtils.ReviewsAdapter;
import com.pro.vyas.pranav.popularmovies.recyclerUtils.TrailerAdapter;
import com.pro.vyas.pranav.popularmovies.viewModelUtils.FavouriteMovieViewModelFactory;
import com.pro.vyas.pranav.popularmovies.viewModelUtils.OneAtTimeFavouriteMovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPoster;
import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPosterBackground;


public class FavouritesDetailActivity extends AppCompatActivity {


    private static final String TAG = "FavouritesDetailActivit";

    @BindView(R.id.text_title_detail_favourites)
    AlwaysMarqueeTextView tvTitle;
    @BindView(R.id.text_genre_detail_favourites)
    TextView tvGenre;
    @BindView(R.id.text_rating_detail_favourites)
    TextView tvRating;
    @BindView(R.id.text_release_detail_favourites)
    TextView tvRelease;
    @BindView(R.id.text_synopsis_detail_favourites)
    TextView tvSynopsis;
    @BindView(R.id.text_total_votes_detail_favourites)
    TextView tvTotalVotes;
    @BindView(R.id.image_poster_detail_favourites)
    ImageView ivPoster;
    @BindView(R.id.image_backdrop_detail_favourites)
    ImageView ivPosterBackground;
    @BindView(R.id.toolbar_favourites_detail)
    Toolbar toolbarDetail;
    @BindView(R.id.text_toolbar_title_favourites_detail)
    AlwaysMarqueeTextView tvToolbarTitleDetail;
    //@BindView(R.id.chip_favourite_detail) TextView btnAddFavourites;
    @BindView(R.id.bottom_navigation_favourites)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.flow_genre_detail_favourites)
    FlowLayout flowLayout;

    static boolean isAdded = false;
    private Intent intent;
    private MovieModel modelForFavourite;
    private MovieDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_detail);
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

    private void preLoadItems() {
        tvTitle.setAlwaysMarquee(true);
        tvToolbarTitleDetail.setAlwaysMarquee(true);
        mDb = MovieDatabase.getInstance(this);
    }

    public void initBottomNavigationView(Intent intent) {
        bottomNavigation.setItemIconTintList(null);
        MenuItem menuItemFavourite = bottomNavigation.getMenu().getItem(0);
        menuItemFavourite.setCheckable(false);

        if (intent.hasExtra("MovieJSONString")) {
            Gson gson = new Gson();
            modelForFavourite = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
        }
        //isAdded = ;
        checkAlreadyFavourite();
        //Init Bottom Navigation View now
//        if (isAdded) {
//            menuItemFavourite.setTitle("Remove From Favourite");
//            menuItemFavourite.setIcon(getResources().getDrawable(R.drawable.ic_unfavourite_heart_filled_red));
//        } else {
//            menuItemFavourite.setIcon(getResources().getDrawable(R.drawable.ic_favourite_heart_fillled_red));
//            menuItemFavourite.setTitle("Add to Favourite");
//        }
        //ON Click Listener for Bottom Navigatioj View
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.favourite_bottom_detail:
                        if (isAdded) {
                            removeFromFavourite(modelForFavourite);
                            item.setIcon(getResources().getDrawable(R.drawable.ic_favourite_heart_fillled_red));
                            item.setTitle("Add to Favourite");
                        } else {
                            addTofavourites(modelForFavourite);
                            item.setIcon(getResources().getDrawable(R.drawable.ic_unfavourite_heart_filled_red));
                            item.setTitle("Remove from Favourite");
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void bindUI(Intent intent) {
        ActionBar actionBar = getSupportActionBar();
        if (intent.hasExtra("MovieJSONString")) {
            Gson gson = new Gson();
            MovieModel model = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
            tvRating.setText("IMDB Rating :\n" + model.getVote_average());
            tvRelease.setText(model.getRelease_date());
            tvTotalVotes.setText("Total Votes :\n" + model.getVote_count());
            tvTitle.setText(model.getTitle());
            tvSynopsis.setText(model.getOverview());
            Picasso.get()
                    .load(baseUrlPoster + model.getPoster_path())
                    .placeholder(R.drawable.loading_new)
                    .error(R.drawable.ic_loading)
                    .into(ivPoster);
            Picasso.get()
                    .load(baseUrlPosterBackground + model.getBackdrop_path())
                    .placeholder(R.drawable.loading_new)
                    .error(R.drawable.ic_loading)
                    .into(ivPosterBackground);
            tvToolbarTitleDetail.setText(model.getTitle());
            loadGenre(model.getTag_Genre());
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Objects.requireNonNull(actionBar).setDisplayShowTitleEnabled(false);
    }

    private void loadGenre(String tag_genre) {
        //Toast.makeText(this, "Genre is "+tag_genre, Toast.LENGTH_SHORT).show();
        flowLayout.removeAllViews();
        List<String> strings = new ArrayList<>(Arrays.asList(tag_genre.split(",")));
        for (String x : strings) {
            TextView chip = new TextView(this);
            //chip.setAsChip(true);
            chip.setText(x);
            chip.setTextColor(Color.BLACK);
            chip.setBackground(this.getResources().getDrawable(R.drawable.button_rounded_gray));
            chip.setPadding(25,25,25,25);
            chip.setTextSize(12);
            flowLayout.addView(chip);
            flowLayout.setChildSpacing(12);
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

    private void addTofavourites(MovieModel movie) {
        isAdded = true;
        MovieEntry movieToAdd = new MovieEntry(movie);
        mDb.movieDao().insertMovie(movieToAdd);
        Toast.makeText(this, "Sucessfully added to favourites : " + movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void removeFromFavourite(MovieModel movie) {
        isAdded = false;
        MovieEntry movieToDelete = new MovieEntry(movie);
        mDb.movieDao().deleteMovie(movieToDelete);
        Toast.makeText(this, "Sucessfully removed from Favourites : " + movie.getTitle(), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void checkAlreadyFavourite() {
        FavouriteMovieViewModelFactory factory = new FavouriteMovieViewModelFactory(mDb,modelForFavourite.getId());
        OneAtTimeFavouriteMovieViewModel ViewModel = ViewModelProviders.of(this,factory).get(OneAtTimeFavouriteMovieViewModel.class);
        ViewModel.getMovie().observe(this, new Observer<MovieEntry>() {
            @Override
            public void onChanged(@Nullable MovieEntry movieEntry) {
                MenuItem menuItemFavourite = bottomNavigation.getMenu().getItem(0);
                //Toast.makeText(FavouritesDetailActivity.this, "Movie Checking for favourites", Toast.LENGTH_SHORT).show();
                if (movieEntry == null) {
                    //Toast.makeText(this, "Not Favourite", Toast.LENGTH_SHORT).show();
                    isAdded = false;
                    menuItemFavourite.setIcon(getResources().getDrawable(R.drawable.ic_favourite_heart_fillled_red));
                    menuItemFavourite.setTitle("Add to Favourite");

                } else {
                    Toast.makeText(FavouritesDetailActivity.this, "Favourite Already", Toast.LENGTH_SHORT).show();
                    isAdded = true;
                    menuItemFavourite.setTitle("Remove From Favourite");
                    menuItemFavourite.setIcon(getResources().getDrawable(R.drawable.ic_unfavourite_heart_filled_red));
                }
            }
        });
        //Toast.makeText(this, "Movie is not Favourite", Toast.LENGTH_SHORT).show();
    }
}
