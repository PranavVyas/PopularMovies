package com.pro.vyas.pranav.popularmovies.databaseUtils;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.pro.vyas.pranav.popularmovies.models.MovieModel;

@Entity(tableName = "Movies")
public class MovieEntry {
    @PrimaryKey @NonNull
    String movie_id;
    String title;
    String genre;
    String poster_path;
    String backddrop_path;
    String overview;
    String rating;
    String release_date;
    String vote_count;
    String tag_Genre;

    public MovieEntry(MovieModel model){
        this.movie_id = model.getId();
        this.title = model.getTitle();
        this.tag_Genre = model.getTag_Genre();
        this.poster_path = model.getPoster_path();
        this.backddrop_path = model.getBackdrop_path();
        this.overview = model.getOverview();
        this.rating = model.getVote_average();
        this.release_date = model.getRelease_date();
        this.vote_count = model.getVote_count();
    }

    public MovieEntry() {
    }

    @NonNull
    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(@NonNull String movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackddrop_path() {
        return backddrop_path;
    }

    public void setBackddrop_path(String backddrop_path) {
        this.backddrop_path = backddrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getTag_Genre() {
        return tag_Genre;
    }

    public void setTag_Genre(String tag_Genre) {
        this.tag_Genre = tag_Genre;
    }
}
