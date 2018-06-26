package com.pro.vyas.pranav.popularmovies.Models;

import java.util.List;

public class MainGenreModel {
    List<GenreModel> genres;

    public MainGenreModel() {
    }

    public MainGenreModel(List<GenreModel> genres) {
        this.genres = genres;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreModel> genres) {
        this.genres = genres;
    }
}
