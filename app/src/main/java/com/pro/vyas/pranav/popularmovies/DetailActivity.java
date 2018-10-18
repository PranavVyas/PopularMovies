package com.pro.vyas.pranav.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;
import com.pro.vyas.pranav.popularmovies.asyncTaskUtils.LoadReviewsAsyncTask;
import com.pro.vyas.pranav.popularmovies.asyncTaskUtils.LoadVideosAsyncTask;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieDatabase;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;
import com.pro.vyas.pranav.popularmovies.extraUtils.AlwaysMarqueeTextView;
import com.pro.vyas.pranav.popularmovies.extraUtils.AppExecutors;
import com.pro.vyas.pranav.popularmovies.models.DetailMovieModel;
import com.pro.vyas.pranav.popularmovies.models.DetailMovieReviewModel;
import com.pro.vyas.pranav.popularmovies.models.MovieModel;
import com.pro.vyas.pranav.popularmovies.models.ReviewsModel;
import com.pro.vyas.pranav.popularmovies.models.VideosModel;
import com.pro.vyas.pranav.popularmovies.recyclerUtils.ReviewsAdapter;
import com.pro.vyas.pranav.popularmovies.recyclerUtils.TrailerAdapter;
import com.pro.vyas.pranav.popularmovies.viewModelUtils.FavouriteMovieViewModelFactory;
import com.pro.vyas.pranav.popularmovies.viewModelUtils.OneAtTimeFavouriteMovieViewModel;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPoster;
import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.baseUrlPosterBackground;

public class DetailActivity extends AppCompatActivity implements LoadVideosAsyncTask.LoadTrailerAsyncTaskCallback, LoadReviewsAsyncTask.LoadReviewsCallBack {

    private static final String TAG = "DetailActivity";

    @BindView(R.id.text_title_detail)
    AlwaysMarqueeTextView tvTitle;
    @BindView(R.id.text_genre_detail)
    TextView tvGenre;
    @BindView(R.id.text_rating_detail)
    TextView tvRating;
    @BindView(R.id.text_release_detail)
    TextView tvRelease;
    @BindView(R.id.text_synopsis_detail)
    TextView tvSynopsis;
    @BindView(R.id.text_total_votes_detail)
    TextView tvTotalVotes;
    @BindView(R.id.image_poster_detail)
    ImageView ivPoster;
    @BindView(R.id.image_backdrop_detail)
    ImageView ivPosterBackground;
    @BindView(R.id.toolbar_detail)
    Toolbar toolbarDetail;
    @BindView(R.id.text_toolbar_title_detail)
    AlwaysMarqueeTextView tvToolbarTitleDetail;
    //@BindView(R.id.chip_favourite_detail) TextView btnAddFavourites;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.flow_genre_detail)
    FlowLayout flowLayout;
    @BindView(R.id.rv_videos_detail)
    RecyclerView rvTrailer;
    @BindView(R.id.loading_indicator_reviews)
    AVLoadingIndicatorView loadingReviews;
    @BindView(R.id.rv_reviews_detail)
    RecyclerView rvReviews;
    @BindView(R.id.tag_videos_detail)
    TextView tvVideos;
    @BindView(R.id.tag_reviews_detail)
    TextView tvReviews;

    static boolean isAdded = false;
    Intent intent;
    MovieModel modelForFavourite;
    private MovieDatabase mDb;
    private TrailerAdapter mTrailerAdapter;
    private ReviewsAdapter mReviewsAdapter;
    private AppExecutors mExecutors;

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

    private void preLoadItems() {

        tvTitle.setAlwaysMarquee(true);
        tvToolbarTitleDetail.setAlwaysMarquee(true);
        mTrailerAdapter = new TrailerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTrailer.setLayoutManager(layoutManager);
        rvTrailer.setAdapter(mTrailerAdapter);
        mReviewsAdapter = new ReviewsAdapter(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvReviews.setLayoutManager(layoutManager1);
        rvReviews.setAdapter(mReviewsAdapter);
        mDb = MovieDatabase.getInstance(DetailActivity.this);
        mExecutors = AppExecutors.getInstance();
    }

    private void loadTrailers(String movieId) {
        LoadVideosAsyncTask asyncTask = new LoadVideosAsyncTask(this, this);
        asyncTask.loadMovieId(movieId);
        asyncTask.execute();
    }

    private void loadReviews(String movieId) {
        LoadReviewsAsyncTask asyncTask = new LoadReviewsAsyncTask(this, this);
        asyncTask.loadMovieId(movieId);
        asyncTask.execute();
    }

    private void initBottomNavigationView(Intent intent) {
        bottomNavigation.setItemIconTintList(null);
        MenuItem menuItemFavourite = bottomNavigation.getMenu().getItem(0);
        menuItemFavourite.setCheckable(false);

        if (intent.hasExtra("MovieJSONString")) {
            Gson gson = new Gson();
            modelForFavourite = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
        }
        checkAlreadyFavourite();
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

    public void bindUI(Intent intent) {
        ActionBar actionBar = getSupportActionBar();
        if (intent.hasExtra("MovieJSONString")) {
            Gson gson = new Gson();
            MovieModel model = gson.fromJson(intent.getStringExtra("MovieJSONString"), MovieModel.class);
            tvRating.setText(new StringBuilder().append("IMDB Rating :\n").append(model.getVote_average()).toString());
            tvRelease.setText(model.getRelease_date());
            tvTotalVotes.setText(new StringBuilder().append("Total Votes :\n").append(model.getVote_count()).toString());
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
            if (checkNetwork()) {
                loadTrailers(model.getId());
                loadReviews(model.getId());
            } else {
                showTrailers(false);
                showReviews(false);
            }
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Objects.requireNonNull(actionBar).setDisplayShowTitleEnabled(false);
    }

    private void showReviews(boolean b) {
        if (!b) {
            loadingReviews.smoothToHide();
            rvReviews.setVisibility(View.GONE);
            tvReviews.setVisibility(View.GONE);
            loadingReviews.setVisibility(View.GONE);
            showSnackbar("Please Connect to internet to see Trailer and Reviews\nRefresh After you are connected again!", tvTitle);
        }else{
            loadingReviews.smoothToShow();
        }
    }

    private void showSnackbar(String message, View view) {
        Snackbar sbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        sbar.show();
    }

    private void showTrailers(boolean b) {
        if (!b) {
            rvTrailer.setVisibility(View.GONE);
            tvVideos.setVisibility(View.GONE);
        }
    }

    private boolean checkNetwork() {
        boolean flag = false;
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr != null) {
            if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                flag = true;
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            } else if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
                flag = false;
                Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
            } else {
                flag = false;
            }
        }
        return flag;
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
            chip.setPadding(25, 25, 25, 25);
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

    public void addTofavourites(final MovieModel movie) {
        isAdded = true;
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                MovieEntry movieToAdd = new MovieEntry(movie);
                mDb.movieDao().insertMovie(movieToAdd);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "Sucessfully added to favourites : " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void removeFromFavourite(final MovieModel movie) {
        isAdded = false;
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                MovieEntry movieToDelete = new MovieEntry(movie);
                mDb.movieDao().deleteMovie(movieToDelete);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "Sucessfully removed from Favourites : " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void checkAlreadyFavourite() {
        FavouriteMovieViewModelFactory factory = new FavouriteMovieViewModelFactory(mDb, modelForFavourite.getId());
        OneAtTimeFavouriteMovieViewModel ViewModel = ViewModelProviders.of(this, factory).get(OneAtTimeFavouriteMovieViewModel.class);
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
                    Toast.makeText(DetailActivity.this, "Favourite Already", Toast.LENGTH_SHORT).show();
                    isAdded = true;
                    menuItemFavourite.setTitle("Remove From Favourite");
                    menuItemFavourite.setIcon(getResources().getDrawable(R.drawable.ic_unfavourite_heart_filled_red));
                }
            }
        });
        //Toast.makeText(this, "Movie is not Favourite", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete(DetailMovieModel detailMovieModel) {
        List<VideosModel> trailerModel = detailMovieModel.getResults();
        mTrailerAdapter.setTrailerList(trailerModel);
    }

    @Override
    public void onCompleteReviews(DetailMovieReviewModel reviewsModels) {
        List<ReviewsModel> reviewModel;
        if (reviewsModels.getResults().size() == 0) {
            reviewModel = new ArrayList<>();
            ReviewsModel tempModel = new ReviewsModel("", "\n\n\nNo One Reviewed Yet\n\n\n", null, null);
            reviewModel.add(tempModel);
        } else {
            reviewModel = reviewsModels.getResults();
        }
        mReviewsAdapter.setReviews(reviewModel);
        loadingReviews.smoothToHide();
    }
}
