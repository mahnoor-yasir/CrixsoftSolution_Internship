package com.moviedb.service;

import com.moviedb.model.Movie;
import com.moviedb.utils.DataGenerator;
import java.io.*;
import java.util.*;

public class MovieService {
    private final List<Movie> movies;

    public MovieService() {
        this.movies = DataGenerator.generateMovies();
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public Movie getMovieById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        for (Movie movie : movies) {
            if (movie.getId().equalsIgnoreCase(id.trim())) {
                return movie;
            }
        }
        return null;
    }

    public List<Movie> searchByTitle(String title) {
        List<Movie> results = new ArrayList<>();
        if (title == null || title.isEmpty()) {
            return results;
        }
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(movie);
            }
        }
        return results;
    }

    public List<Movie> filterByGenre(String genre) {
        List<Movie> results = new ArrayList<>();
        if (genre == null || genre.isEmpty()) {
            return results;
        }
        for (Movie movie : movies) {
            for (String g : movie.getGenres()) {
                if (g.equalsIgnoreCase(genre)) {
                    results.add(movie);
                    break;
                }
            }
        }
        return results;
    }

    public List<Movie> sortByRating(boolean ascending) {
        List<Movie> sorted = new ArrayList<>(movies);
        if (ascending) {
            Collections.sort(sorted, Comparator.comparingDouble(Movie::getImdbRating));
        } else {
            Collections.sort(sorted, Comparator.comparingDouble(Movie::getImdbRating).reversed());
        }
        return sorted;
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("Total Movies", movies.size());
        
        double totalRating = 0;
        String highestRated = "N/A";
        double highestRating = 0;
        
        for (Movie movie : movies) {
            totalRating += movie.getImdbRating();
            if (movie.getImdbRating() > highestRating) {
                highestRating = movie.getImdbRating();
                highestRated = movie.getTitle();
            }
        }
        
        double average = !movies.isEmpty() ? totalRating / movies.size() : 0;
        stats.put("Average Rating", average);
        stats.put("Highest Rated", highestRated);
        stats.put("Highest Rating", highestRating);
        
        return stats;
    }

    public List<Movie> getMoviesByDirector(String director) {
        List<Movie> results = new ArrayList<>();
        if (director == null || director.isEmpty()) {
            return results;
        }
        for (Movie movie : movies) {
            if (movie.getDirector().equalsIgnoreCase(director)) {
                results.add(movie);
            }
        }
        return results;
    }

    // ========== NEW METHODS (FIXING THE ERRORS) ==========

    /**
     * Add a new movie to the database
     */
    public boolean addMovie(Movie movie) {
        if (movie == null) {
            return false;
        }
        // Check if movie with same ID already exists
        for (Movie existing : movies) {
            if (existing.getId().equalsIgnoreCase(movie.getId())) {
                return false;
            }
        }
        movies.add(movie);
        return true;
    }

    /**
     * Generate the next auto-incremented Movie ID
     */
    public String generateNextId() {
        int maxId = 0;
        for (Movie movie : movies) {
            String id = movie.getId();
            if (id != null && id.startsWith("MV-")) {
                try {
                    int num = Integer.parseInt(id.substring(3));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid IDs
                }
            }
        }
        return String.format("MV-%04d", maxId + 1);
    }

    /**
     * Delete a movie by ID
     */
    public boolean deleteMovie(String id) {
        Movie movie = getMovieById(id);
        if (movie != null) {
            movies.remove(movie);
            return true;
        }
        return false;
    }

    /**
     * Advanced search with multiple filters
     */
    public List<Movie> advancedSearch(String title, String genre, String director, 
                                       Double minRating, Integer year) {
        List<Movie> results = new ArrayList<>(movies);
        
        if (title != null && !title.isEmpty()) {
            results.removeIf(m -> !m.getTitle().toLowerCase().contains(title.toLowerCase()));
        }
        if (genre != null && !genre.isEmpty()) {
            results.removeIf(m -> !m.getGenres().stream()
                .anyMatch(g -> g.equalsIgnoreCase(genre)));
        }
        if (director != null && !director.isEmpty()) {
            results.removeIf(m -> !m.getDirector().equalsIgnoreCase(director));
        }
        if (minRating != null) {
            results.removeIf(m -> m.getImdbRating() < minRating);
        }
        if (year != null) {
            results.removeIf(m -> m.getYear() != year);
        }
        return results;
    }

    /**
     * Save movies to a file
     */
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(movies);
        }
    }

    /**
     * Export movies to CSV
     */
    public void exportToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("ID,Title,Year,Genres,Director,IMDb Rating,Status");
            for (Movie m : movies) {
                writer.printf("%s,\"%s\",%d,\"%s\",\"%s\",%.1f,%s%n",
                    m.getId(), m.getTitle(), m.getYear(),
                    String.join("|", m.getGenres()),
                    m.getDirector(), m.getImdbRating(), m.getStatus());
            }
        }
    }

    /**
     * Get all unique genres
     */
    public Set<String> getAllGenres() {
        Set<String> genreSet = new HashSet<>();
        for (Movie movie : movies) {
            genreSet.addAll(movie.getGenres());
        }
        return genreSet;
    }
}
