package com.pro.vyas.pranav.popularmovies.viewModelUtils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieDatabase;
import com.pro.vyas.pranav.popularmovies.databaseUtils.MovieEntry;

import java.util.List;

public class MainFavouriteMovieViewModel extends AndroidViewModel {

    private LiveData<List<MovieEntry>> movies;
    private MovieDatabase mDb;

    public MainFavouriteMovieViewModel(@NonNull Application application) {
        super(application);
        mDb = MovieDatabase.getInstance(getApplication());
        Toast.makeText(getApplication(), "Asking From View Model", Toast.LENGTH_SHORT).show();
        movies = mDb.movieDao().getAllMovies();
    }

    public LiveData<List<MovieEntry>> getMovies() {
        return movies;
    }

    public void setMovies(LiveData<List<MovieEntry>> movies) {
        this.movies = movies;
    }
}
