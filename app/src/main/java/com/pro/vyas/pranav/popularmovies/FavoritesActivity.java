package com.pro.vyas.pranav.popularmovies;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieDatabase;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;
import com.pro.vyas.pranav.popularmovies.models.MovieModel;
import com.pro.vyas.pranav.popularmovies.recyclerUtils.FavouriteMovieAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class FavoritesActivity extends AppCompatActivity {

    @BindView(R.id.rv_favourites) RecyclerView rvFavourites;
    @BindView(R.id.toolbar_favourites) Toolbar toolbar;
    FavouriteMovieAdapter mAdapter;
    MovieDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mAdapter = new FavouriteMovieAdapter(FavoritesActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT ? 2 : 3);
        //RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(context.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT ? 2 : 3,StaggeredGridLayoutManager.VERTICAL);
        rvFavourites.setAdapter(mAdapter);
        rvFavourites.setHasFixedSize(true);
        rvFavourites.setLayoutManager(layoutManager);
        mDb = MovieDatabase.getInstance(FavoritesActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retriveData();
    }

    public void retriveData(){
        List<MovieEntry> movies = mDb.movieDao().getAllMovies();
        mAdapter.setFavouriteMovies(movies);
    }
}
