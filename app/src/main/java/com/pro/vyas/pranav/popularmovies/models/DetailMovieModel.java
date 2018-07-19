package com.pro.vyas.pranav.popularmovies.models;

import java.util.List;

public class DetailMovieModel {
    private List<VideosModel> results;

    public DetailMovieModel(List<VideosModel> results) {
        this.results = results;
    }

    public DetailMovieModel() {
    }

    public List<VideosModel> getResults() {
        return results;
    }

    public void setResults(List<VideosModel> results) {
        this.results = results;
    }
}
