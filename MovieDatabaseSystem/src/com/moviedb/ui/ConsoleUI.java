package com.moviedb.ui;

import com.moviedb.model.Movie;
import com.moviedb.service.MovieService;
import java.io.IOException;
import java.util.*;

public class ConsoleUI {
    private final MovieService service;
    private final Scanner scanner;

    public ConsoleUI() {
        this.service = new MovieService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n========================================");
        System.out.println("   🎬 MOVIE DATABASE SYSTEM");
        System.out.println("========================================\n");

        while (true) {
            displayMenu();
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> showAllMovies();
                case "2" -> searchMovie();
                case "3" -> filterByGenre();
                case "4" -> sortByRating();
                case "5" -> viewStatistics();
                case "6" -> watchMovie();
                case "7" -> addNewMovie();
                case "8" -> deleteMovie();
                case "9" -> editMovie();
                case "10" -> saveToFile();
                case "11" -> exportToCSV();
                case "12" -> advancedSearch();
                case "0" -> {
                    System.out.println("\n👋 Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void displayMenu() {
        System.out.println("\n📋 MAIN MENU");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("1. 📺 Show All Movies");
        System.out.println("2. 🔍 Search Movie by Title");
        System.out.println("3. 🎭 Filter by Genre");
        System.out.println("4. ⭐ Sort by IMDb Rating");
        System.out.println("5. 📊 View Statistics");
        System.out.println("6. 🎬 Watch Movie");
        System.out.println("7. ➕ Add New Movie");
        System.out.println("8. 🗑️ Delete Movie");
        System.out.println("9. ✏️ Edit Movie");
        System.out.println("10. 💾 Save to File");
        System.out.println("11. 📤 Export to CSV");
        System.out.println("12. 🔍 Advanced Search");
        System.out.println("0. 🚪 Exit");
        System.out.print("Enter your choice: ");
    }

    private void showAllMovies() {
        List<Movie> movies = service.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("\n📭 No movies in database!");
            return;
        }
        printTableHeader();
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Total: " + movies.size() + " movies");
    }

    private void searchMovie() {
        System.out.print("Enter movie title to search: ");
        String title = scanner.nextLine();
        List<Movie> results = service.searchByTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("\n❌ No movies found with title containing: " + title);
        } else {
            System.out.println("\n✅ Found " + results.size() + " movies:");
            printTableHeader();
            for (Movie movie : results) {
                System.out.println(movie);
            }
        }
    }

    private void filterByGenre() {
        System.out.print("Enter genre (Action, Drama, Comedy, etc.): ");
        String genre = scanner.nextLine();
        List<Movie> results = service.filterByGenre(genre);
        
        if (results.isEmpty()) {
            System.out.println("\n❌ No movies found in genre: " + genre);
        } else {
            System.out.println("\n✅ Found " + results.size() + " movies in " + genre + ":");
            printTableHeader();
            for (Movie movie : results) {
                System.out.println(movie);
            }
        }
    }

    private void sortByRating() {
        System.out.print("Sort by Highest Rating? (y/n): ");
        String input = scanner.nextLine();
        boolean ascending = !input.toLowerCase().startsWith("y");
        
        List<Movie> sorted = service.sortByRating(ascending);
        System.out.println("\n✅ Movies sorted by " + (ascending ? "Lowest" : "Highest") + " Rating:");
        printTableHeader();
        for (Movie movie : sorted) {
            System.out.println(movie);
        }
    }

    private void viewStatistics() {
        Map<String, Object> stats = service.getStatistics();
        System.out.println("\n📊 DATABASE STATISTICS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            System.out.printf("%-20s: %s%n", entry.getKey(), entry.getValue());
        }
    }

    private void watchMovie() {
        System.out.print("Enter Movie ID (e.g., MV-0001): ");
        String id = scanner.nextLine().toUpperCase().trim();
        
        Movie movie = service.getMovieById(id);
        if (movie == null) {
            System.out.println("\n❌ Movie with ID " + id + " not found!");
            return;
        }
        
        System.out.println("\n🎬 " + movie.getTitle() + " (" + movie.getYear() + ")");
        System.out.println("Rating: ⭐ " + movie.getImdbRating());
        System.out.println("Watch Link: " + movie.getTrailerLink());
        System.out.println("\n💡 The link will open in your browser automatically!");
        
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String link = movie.getTrailerLink();
            
            if (os.contains("win")) {
                Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", link});
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec(new String[]{"open", link});
            } else {
                Runtime.getRuntime().exec(new String[]{"xdg-open", link});
            }
            System.out.println("✅ Browser opened successfully!");
            
        } catch (IOException e) {
            System.out.println("⚠️ IO Error: Could not open browser.");
            System.out.println("📋 Please copy this link manually: " + movie.getTrailerLink());
        } catch (SecurityException e) {
            System.out.println("⚠️ Security Error: Permission denied to open browser.");
            System.out.println("📋 Please copy this link manually: " + movie.getTrailerLink());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error: " + e.getMessage());
            System.out.println("📋 Please copy this link manually: " + movie.getTrailerLink());
        }
    }

    private void addNewMovie() {
        System.out.println("\n➕ ADD NEW MOVIE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        String newId = service.generateNextId();
        System.out.println("📌 Auto-generated ID: " + newId);
        
        Movie movie = new Movie();
        movie.setId(newId);
        
        System.out.print("📝 Enter Movie Title: ");
        String title = scanner.nextLine();
        if (title.isEmpty()) {
            System.out.println("❌ Title cannot be empty!");
            return;
        }
        movie.setTitle(title);
        
        System.out.print("📅 Enter Release Year: ");
        try {
            int year = Integer.parseInt(scanner.nextLine());
            if (year < 1888 || year > 2100) {
                System.out.println("❌ Invalid year! (1888-2100)");
                return;
            }
            movie.setYear(year);
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
            return;
        }
        
        System.out.print("🎭 Enter Genres (comma separated, e.g., Action,Drama): ");
        String genresInput = scanner.nextLine();
        if (genresInput.isEmpty()) {
            System.out.println("❌ Genres cannot be empty!");
            return;
        }
        List<String> genres = new ArrayList<>();
        for (String g : genresInput.split(",")) {
            genres.add(g.trim());
        }
        movie.setGenres(genres);
        
        System.out.print("🎬 Enter Director Name: ");
        String director = scanner.nextLine();
        if (director.isEmpty()) {
            System.out.println("❌ Director cannot be empty!");
            return;
        }
        movie.setDirector(director);
        
        System.out.print("👥 Enter Cast (comma separated, e.g., Actor1,Actor2): ");
        String castInput = scanner.nextLine();
        if (castInput.isEmpty()) {
            System.out.println("❌ Cast cannot be empty!");
            return;
        }
        List<String> cast = new ArrayList<>();
        for (String c : castInput.split(",")) {
            cast.add(c.trim());
        }
        movie.setCast(cast);
        
        System.out.print("⭐ Enter IMDb Rating (0.0 - 10.0): ");
        try {
            double rating = Double.parseDouble(scanner.nextLine());
            if (rating < 0 || rating > 10) {
                System.out.println("❌ Rating must be between 0.0 and 10.0!");
                return;
            }
            movie.setImdbRating(rating);
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
            return;
        }
        
        System.out.print("🔗 Enter YouTube Full Movie Link: ");
        String link = scanner.nextLine();
        if (link.isEmpty()) {
            System.out.println("❌ Link cannot be empty!");
            return;
        }
        movie.setTrailerLink(link);
        
        System.out.print("📝 Enter Description (optional): ");
        String description = scanner.nextLine();
        movie.setDescription(description.isEmpty() ? "No description provided" : description);
        
        movie.setStatus("Released");
        
        if (service.addMovie(movie)) {
            System.out.println("\n✅ Movie added successfully!");
            System.out.println("📌 New Movie ID: " + movie.getId());
            System.out.println("🎬 Title: " + movie.getTitle());
            System.out.println("⭐ Rating: " + movie.getImdbRating());
        } else {
            System.out.println("\n❌ Failed to add movie.");
        }
    }

    private void deleteMovie() {
        System.out.print("Enter Movie ID to delete: ");
        String id = scanner.nextLine().toUpperCase().trim();
        
        Movie movie = service.getMovieById(id);
        if (movie == null) {
            System.out.println("\n❌ Movie with ID " + id + " not found!");
            return;
        }
        
        System.out.println("\n📌 Movie found: " + movie.getTitle() + " (" + movie.getYear() + ")");
        System.out.print("Are you sure you want to delete this movie? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.toLowerCase().startsWith("y")) {
            if (service.deleteMovie(id)) {
                System.out.println("\n✅ Movie deleted successfully!");
            } else {
                System.out.println("\n❌ Failed to delete movie.");
            }
        } else {
            System.out.println("\n❌ Deletion cancelled.");
        }
    }

    private void editMovie() {
        System.out.print("Enter Movie ID to edit: ");
        String id = scanner.nextLine().toUpperCase().trim();
        
        Movie movie = service.getMovieById(id);
        if (movie == null) {
            System.out.println("\n❌ Movie with ID " + id + " not found!");
            return;
        }
        
        System.out.println("\n✏️ EDITING MOVIE: " + movie.getTitle());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("(Press Enter to keep current value)");
        
        System.out.print("Title (" + movie.getTitle() + "): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) movie.setTitle(title);
        
        System.out.print("Year (" + movie.getYear() + "): ");
        String yearStr = scanner.nextLine();
        if (!yearStr.isEmpty()) {
            try {
                movie.setYear(Integer.parseInt(yearStr));
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid year, keeping current value.");
            }
        }
        
        System.out.print("Director (" + movie.getDirector() + "): ");
        String director = scanner.nextLine();
        if (!director.isEmpty()) movie.setDirector(director);
        
        System.out.print("IMDb Rating (" + movie.getImdbRating() + "): ");
        String ratingStr = scanner.nextLine();
        if (!ratingStr.isEmpty()) {
            try {
                double rating = Double.parseDouble(ratingStr);
                if (rating >= 0 && rating <= 10) {
                    movie.setImdbRating(rating);
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid rating, keeping current value.");
            }
        }
        
        System.out.print("YouTube Link (" + movie.getTrailerLink() + "): ");
        String link = scanner.nextLine();
        if (!link.isEmpty()) movie.setTrailerLink(link);
        
        System.out.println("\n✅ Movie updated successfully!");
    }

    // FIXED: Save to File with proper exception handling
    private void saveToFile() {
        System.out.print("Enter filename to save (e.g., movies.dat): ");
        String filename = scanner.nextLine();
        if (filename.isEmpty()) {
            System.out.println("❌ Filename cannot be empty!");
            return;
        }
        
        try {
            service.saveToFile(filename);
            System.out.println("\n✅ Movies saved to " + filename + " successfully!");
        } catch (IOException e) {
            System.out.println("\n❌ IO Error saving to file: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("\n❌ Security Error: Permission denied to save file.");
            System.out.println("   Please check file permissions.");
        } catch (Exception e) {
            System.out.println("\n❌ Unexpected error saving to file: " + e.getMessage());
        }
    }

    // FIXED: Export to CSV with proper exception handling
    private void exportToCSV() {
        System.out.print("Enter CSV filename (e.g., movies.csv): ");
        String filename = scanner.nextLine();
        if (filename.isEmpty()) {
            System.out.println("❌ Filename cannot be empty!");
            return;
        }
        
        try {
            service.exportToCSV(filename);
            System.out.println("\n✅ Movies exported to " + filename + " successfully!");
        } catch (IOException e) {
            System.out.println("\n❌ IO Error exporting to CSV: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("\n❌ Security Error: Permission denied to export file.");
            System.out.println("   Please check file permissions.");
        } catch (Exception e) {
            System.out.println("\n❌ Unexpected error exporting to CSV: " + e.getMessage());
        }
    }

    private void advancedSearch() {
        System.out.println("\n🔍 ADVANCED SEARCH");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("(Press Enter to skip any filter)");
        
        System.out.print("Title contains: ");
        String title = scanner.nextLine();
        title = title.isEmpty() ? null : title;
        
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        genre = genre.isEmpty() ? null : genre;
        
        System.out.print("Director: ");
        String director = scanner.nextLine();
        director = director.isEmpty() ? null : director;
        
        System.out.print("Minimum IMDb Rating: ");
        String ratingStr = scanner.nextLine();
        Double minRating = ratingStr.isEmpty() ? null : Double.valueOf(ratingStr);
        
        System.out.print("Year: ");
        String yearStr = scanner.nextLine();
        Integer year = yearStr.isEmpty() ? null : Integer.valueOf(yearStr);
        
        List<Movie> results = service.advancedSearch(title, genre, director, minRating, year);
        
        if (results.isEmpty()) {
            System.out.println("\n❌ No movies found matching your criteria!");
        } else {
            System.out.println("\n✅ Found " + results.size() + " movies:");
            printTableHeader();
            for (Movie movie : results) {
                System.out.println(movie);
            }
        }
    }

    private void printTableHeader() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("%-10s | %-30s | %-4s | %-20s | %-10s | %-10s%n", 
            "ID", "Title", "Year", "Genres", "Rating", "Status");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
