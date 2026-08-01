package com.moviedb.utils;

import com.moviedb.model.Movie;
import java.util.*;

public class DataGenerator {
    
    private static final Object[][] MOVIE_DATA = {
        {"MV-0001", "The Godfather", 1972, new String[]{"Crime", "Drama"}, "Francis Ford Coppola",
         new String[]{"Marlon Brando", "Al Pacino"}, 9.2, "https://www.youtube.com/watch?v=UaVTIH8mujA",
         "The aging patriarch of an organized crime dynasty transfers control to his son.", "Released"},
         
        {"MV-0002", "The Dark Knight", 2008, new String[]{"Action", "Crime"}, "Christopher Nolan",
         new String[]{"Christian Bale", "Heath Ledger"}, 9.0, "https://www.youtube.com/watch?v=EXeTwQWrcwY",
         "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham.", "Released"},
         
        {"MV-0003", "Pulp Fiction", 1994, new String[]{"Crime", "Drama"}, "Quentin Tarantino",
         new String[]{"John Travolta", "Samuel L. Jackson"}, 8.9, "https://www.youtube.com/watch?v=5Z9z2rkxVZY",
         "The lives of two mob hitmen, a boxer, a gangster's wife, and a pair of diner bandits intertwine.", "Released"},
         
        {"MV-0004", "The Shawshank Redemption", 1994, new String[]{"Drama"}, "Frank Darabont",
         new String[]{"Tim Robbins", "Morgan Freeman"}, 9.3, "https://www.youtube.com/watch?v=6hB3S9bIaco",
         "Two imprisoned men bond over a number of years, finding solace and eventual redemption.", "Released"},
         
        {"MV-0005", "Forrest Gump", 1994, new String[]{"Comedy", "Drama"}, "Robert Zemeckis",
         new String[]{"Tom Hanks", "Robin Wright"}, 8.8, "https://www.youtube.com/watch?v=uPIEn0M8su0",
         "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal.", "Released"},
         
        {"MV-0006", "The Matrix", 1999, new String[]{"Sci-Fi", "Action"}, "Lana Wachowski",
         new String[]{"Keanu Reeves", "Laurence Fishburne"}, 8.7, "https://www.youtube.com/watch?v=2KnZac0YqJ0",
         "A computer hacker learns about the true nature of his reality.", "Released"},
         
        {"MV-0007", "Inception", 2010, new String[]{"Sci-Fi", "Action"}, "Christopher Nolan",
         new String[]{"Leonardo DiCaprio", "Joseph Gordon-Levitt"}, 8.8, "https://www.youtube.com/watch?v=YoHD9XEInc0",
         "A thief who steals corporate secrets through the use of dream-sharing technology.", "Released"},
         
        {"MV-0008", "The Departed", 2006, new String[]{"Crime", "Drama"}, "Martin Scorsese",
         new String[]{"Leonardo DiCaprio", "Matt Damon"}, 8.5, "https://www.youtube.com/watch?v=iojhqm0JTW4",
         "An undercover cop and a mole in the police attempt to identify each other.", "Released"},
         
        {"MV-0009", "Gladiator", 2000, new String[]{"Action", "Drama"}, "Ridley Scott",
         new String[]{"Russell Crowe", "Joaquin Phoenix"}, 8.5, "https://www.youtube.com/watch?v=owK1qxDselE",
         "A former Roman General sets out to exact vengeance against the corrupt emperor.", "Released"},
         
        {"MV-0010", "Interstellar", 2014, new String[]{"Sci-Fi", "Adventure"}, "Christopher Nolan",
         new String[]{"Matthew McConaughey", "Anne Hathaway"}, 8.6, "https://www.youtube.com/watch?v=zSWdZVtXT7E",
         "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.", "Released"}
    };

    public static List<Movie> generateMovies() {
        List<Movie> movies = new ArrayList<>();
        
        for (Object[] data : MOVIE_DATA) {
            Movie movie = new Movie();
            movie.setId((String) data[0]);
            movie.setTitle((String) data[1]);
            movie.setYear((int) data[2]);
            
            String[] genres = (String[]) data[3];
            movie.setGenres(Arrays.asList(genres));
            
            movie.setDirector((String) data[4]);
            
            String[] cast = (String[]) data[5];
            movie.setCast(Arrays.asList(cast));
            
            movie.setImdbRating((double) data[6]);
            movie.setTrailerLink((String) data[7]);
            movie.setDescription((String) data[8]);
            movie.setStatus((String) data[9]);
            
            movies.add(movie);
        }
        
        return movies;
    }
}
