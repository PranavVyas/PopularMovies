package com.pro.vyas.pranav.popularmovies.viewModelUtils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieDatabase;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;

public class OneAtTimeFavouriteMovieViewModel extends ViewModel {

    private LiveData<MovieEntry> movie;

    OneAtTimeFavouriteMovieViewModel(MovieDatabase mDb, String mId) {
        movie = mDb.movieDao().getMovieById(mId);
    }

    public LiveData<MovieEntry> getMovie() {
        return movie;
    }

    public void setMovie(LiveData<MovieEntry> movie) {
        this.movie = movie;
    }
}
