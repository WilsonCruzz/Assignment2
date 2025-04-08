package com.example.assignment2.model;

import java.util.List;

public class UserModel {

    private String uid;
    private String email;
    private List<MovieModel> favoriteMovies;

    public UserModel() {}
    public UserModel(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public UserModel(String uid, List<MovieModel> favoriteMovies) {
        this.uid = uid;
        this.favoriteMovies = favoriteMovies;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MovieModel> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(List<MovieModel> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }
}

