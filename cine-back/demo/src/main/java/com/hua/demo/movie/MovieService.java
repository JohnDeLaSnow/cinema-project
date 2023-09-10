package com.hua.demo.movie;

import java.util.List;
import com.hua.demo.showing.Showing;

public interface MovieService {
    Movie getMovieByTitle(String title);
    List<Movie> getAllMovies();
    List<Showing> getMovieShowings(String title);
    Movie saveMovie(Movie movie);
    Movie editMovie(Movie movie);
    void deleteMovie(String title);
}
