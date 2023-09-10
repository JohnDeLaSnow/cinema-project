package com.hua.demo.movie;

import com.hua.demo.exceptions.MovieNotFoundException;
import com.hua.demo.exceptions.ShowingNotFoundException;
import com.hua.demo.reservation.Reservation;
import com.hua.demo.showing.Showing;
import com.hua.demo.showing.ShowingRepository;
import com.hua.demo.showing.State;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService{

    public final MovieRepository movieRepository;
    public final ShowingRepository showingRepository;

    @Override
    public Movie getMovieByTitle(String title) {
        log.info("Fetching movie with title: {}", title);
        return movieRepository.findById(title).orElseThrow(() -> new MovieNotFoundException(title));
    }

    @Override
    public List<Movie> getAllMovies() {
        log.info("Fetching all movies");
        return movieRepository.findAll();
    }

    @Override
    public List<Showing> getMovieShowings(String title) {
        log.info("Fetching movie with title: {}", title);
        Movie movie = movieRepository.findById(title).orElseThrow(() -> new MovieNotFoundException(title));
        log.info("Fetching showings for movie with title: {}", title);
        return showingRepository.getMovieShowings(movie, State.SCHEDULED);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        log.info("Saving new movie with title '{}' to the database", movie.getTitle());
        return movieRepository.save(movie);
    }

    @Override
    public Movie editMovie(Movie movie) {
        //TODO
        return null;
    }

    @Override
    public void deleteMovie(String title) {
        log.info("Deleting movie with title  '{}' from the database", title);
        movieRepository.findById(title).orElseThrow(()->new MovieNotFoundException(title));
        movieRepository.deleteById(title);
    }
}
