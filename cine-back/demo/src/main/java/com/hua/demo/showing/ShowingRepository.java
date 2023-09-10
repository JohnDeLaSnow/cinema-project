package com.hua.demo.showing;

import com.hua.demo.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Integer> {
    @Query("SELECT show FROM showing show WHERE show.state=?1")
    public List<Showing> getShowingByState (State state);

    @Query("SELECT show FROM showing show WHERE show.showingMovie=?1 AND show.state=?2")
    public List<Showing> getMovieShowings (Movie movie,State state);
}
