package com.pro.vyas.pranav.popularmovies.models;

import java.util.List;

public class DetailMovieReviewModel {

    private int id;
    private int page;
    private String imdb_id;
    private List<ReviewsModel> results;
    private String total_pages;
    private String total_results;

    public DetailMovieReviewModel() {
    }

    public DetailMovieReviewModel(int id, int page, List<ReviewsModel> results, String total_pages, String total_results) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public DetailMovieReviewModel(int id, int page, String imdb_id, List<ReviewsModel> results, String total_pages, String total_results) {
        this.id = id;
        this.page = page;
        this.imdb_id = imdb_id;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ReviewsModel> getResults() {
        return results;
    }

    public void setResults(List<ReviewsModel> results) {
        this.results = results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }
}
