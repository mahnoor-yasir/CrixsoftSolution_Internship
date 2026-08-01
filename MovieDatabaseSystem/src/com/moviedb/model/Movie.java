package com.moviedb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String title;
    private int year;
    private List<String> genres;
    private String director;
    private List<String> cast;
    private double imdbRating;
    private String trailerLink;
    private String description;
    private String status;

    public Movie() {
        this.genres = new ArrayList<>();
        this.cast = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public List<String> getCast() { return cast; }
    public void setCast(List<String> cast) { this.cast = cast; }

    public double getImdbRating() { return imdbRating; }
    public void setImdbRating(double imdbRating) { this.imdbRating = imdbRating; }

    public String getTrailerLink() { return trailerLink; }
    public void setTrailerLink(String trailerLink) { this.trailerLink = trailerLink; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%-10s | %-30s | %-4d | %-20s | %-10s | %-10s",
            id, title, year, genres.toString(), imdbRating, status);
    }
}
