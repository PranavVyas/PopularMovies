package com.pro.vyas.pranav.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;
import com.pro.vyas.pranav.popularmovies.recyclerUtils.FavouriteMovieAdapter;
import com.pro.vyas.pranav.popularmovies.viewModelUtils.MainFavouriteMovieViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class FavoritesActivity extends AppCompatActivity {

    @BindView(R.id.rv_favourites)
    RecyclerView rvFavourites;
    @BindView(R.id.toolbar_favourites)
    Toolbar toolbar;
    @BindView(R.id.text_toolbar_favourites)
    TextView tvToolbar;
    @BindView(R.id.text_error_favourites)
    TextView tvError;
    private FavouriteMovieAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mAdapter = new FavouriteMovieAdapter(FavoritesActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT ? 2 : 3);
        //RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(context.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT ? 2 : 3,StaggeredGridLayoutManager.VERTICAL);
        rvFavourites.setAdapter(mAdapter);
        rvFavourites.setHasFixedSize(true);
        rvFavourites.setLayoutManager(layoutManager);
        tvToolbar.setText(R.string.TEXT_DEFAULT_TOOLBAR_FAVOURITE);
        retriveData();
    }

    public void retriveData() {
//      List<MovieEntry> movies = mDb.movieDao().getAllMovies();
        MainFavouriteMovieViewModel viewModel = ViewModelProviders.of(this).get(MainFavouriteMovieViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntry> movieEntries) {
                if(movieEntries.size() == 0){
                    Toast.makeText(FavoritesActivity.this, "Empty Favourites NOw Bro Add some na", Toast.LENGTH_SHORT).show();
                    tvError.setVisibility(View.VISIBLE);
                    rvFavourites.setVisibility(View.GONE);
                }else {
                    tvError.setVisibility(View.GONE);
                    rvFavourites.setVisibility(View.VISIBLE);
                    mAdapter.setFavouriteMovies(movieEntries);
                }
            }
        });
        //movies.removeObservers(this);
    }
}
