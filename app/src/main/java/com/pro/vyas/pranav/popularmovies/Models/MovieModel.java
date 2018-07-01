package com.pro.vyas.pranav.popularmovies.Models;

import java.util.List;

public class MovieModel {

    private String id;
    private String vote_count,vote_average,title,popularity,poster_path,overview,release_date,backdrop_path;
    private List<String> genre_ids;

    public MovieModel() {
    }

    public MovieModel(String id, String vote_count, String vote_average, String title, String popularity, String poster_path, String overview, String release_date, String backdrop_path, List<String> genre_ids) {
        this.id = id;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<String> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<String> genre_ids) {
        this.genre_ids = genre_ids;
    }
}
