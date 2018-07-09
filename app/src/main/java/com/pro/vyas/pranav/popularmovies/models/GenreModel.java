package com.pro.vyas.pranav.popularmovies.models;

public class GenreModel {
    private String id;
    private String name;

    public GenreModel() {
    }

    public GenreModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
