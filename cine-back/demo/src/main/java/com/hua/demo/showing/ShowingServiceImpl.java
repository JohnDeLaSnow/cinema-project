package com.hua.demo.showing;

import com.hua.demo.exceptions.MovieNotFoundException;
import com.hua.demo.exceptions.ShowingNotFoundException;
import com.hua.demo.movie.Movie;
import com.hua.demo.movie.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ShowingServiceImpl implements ShowingService{
    public final ShowingRepository showingRepository;
    public final MovieRepository movieRepository;
    @Override
    public Showing getShowingById(int id) {
        log.info("Fetching showing with id: {}", id);
        return showingRepository.findById(id).orElseThrow(() -> new ShowingNotFoundException(id));
    }

    @Override
    public List<Showing> getAllShowings() {
        log.info("Fetching all showings");
        return showingRepository.findAll();
    }

    @Override
    public Showing saveShowing(Showing showing) {
        log.info("Saving new showing with id '{}' to the database", showing.getId());
        return showingRepository.save(showing);
    }

    @Override
    public Showing editShowing(Showing showing) {
        //TODO
        return null;
    }

    @Override
    public void deleteShowing(int id) {
        log.info("Deleting showing with id '{}' from the database", id);
        showingRepository.findById(id).orElseThrow(()-> new ShowingNotFoundException(id));
        showingRepository.deleteById(id);
    }

    @Override
    public Showing addMovieToShowing(int id, String title) {
        Showing showing = showingRepository.findById(id).orElseThrow(()->new ShowingNotFoundException(id));
        Movie movie = movieRepository.findById(title).orElseThrow(()->new MovieNotFoundException(title));
        showing.setShowingMovie(movie);
        return showing;
    }

    @Override
    public List<Showing> getShowingsByState(State state) {
        log.info("Fetching all showings with state '{}'",state);
        return showingRepository.getShowingByState(state);
    }
}
