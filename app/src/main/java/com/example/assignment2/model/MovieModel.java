package com.example.assignment2.model;

import java.util.List;

public class MovieModel {
    String title;
    String year;
    String poster;
    String type;
    String Description;


    public MovieModel() {
    }
    public MovieModel(String title) {
        this.title = title;
    }

    public MovieModel(String posterUrl, String title, String year, String type) {
        this.poster = posterUrl;
        this.title = title;
        this.year = year;
        this.type = type;
    }
    public MovieModel(String description, String posterUrl, String title, String year, String type) {
        this.Description = description;
        this.poster = posterUrl;
        this.title = title;
        this.year = year;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}

