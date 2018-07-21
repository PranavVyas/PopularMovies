package com.pro.vyas.pranav.popularmovies.viewModelUtils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieDatabase;

public class FavouriteMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MovieDatabase mDb;
    private String mId;

    public FavouriteMovieViewModelFactory(MovieDatabase mDb, String mId) {
        this.mDb = mDb;
        this.mId = mId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OneAtTimeFavouriteMovieViewModel(mDb,mId);
    }
}
