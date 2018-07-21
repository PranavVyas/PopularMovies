package com.pro.vyas.pranav.popularmovies.viewModelUtils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Database;
import android.widget.Toast;

import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieDatabase;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;

import java.util.List;

public class OneAtTimeFavouriteMovieViewModel extends ViewModel {

    LiveData<MovieEntry> movie;

    public OneAtTimeFavouriteMovieViewModel(MovieDatabase mDb, String mId) {
        movie = mDb.movieDao().getMovieById(mId);
    }

    public LiveData<MovieEntry> getMovie() {
        return movie;
    }

    public void setMovie(LiveData<MovieEntry> movie) {
        this.movie = movie;
    }
}
