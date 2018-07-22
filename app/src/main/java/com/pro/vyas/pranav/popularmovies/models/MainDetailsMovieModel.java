package com.pro.vyas.pranav.popularmovies.models;

public class MainDetailsMovieModel {
    DetailMovieModel videos;

    public MainDetailsMovieModel(DetailMovieModel videos) {
        this.videos = videos;
    }

    public MainDetailsMovieModel() {
    }

    public DetailMovieModel getVideos() {
        return videos;
    }

    public void setVideos(DetailMovieModel videos) {
        this.videos = videos;
    }

}
