package com.hua.demo.movie;

import com.hua.demo.exceptions.MovieNotFoundException;
import com.hua.demo.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {
    public final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok().body(movieService.getAllMovies());
    }

    @GetMapping("/{title}")
    public Object getMovieByTitle(@PathVariable("title") String title) {
        try {
            return ResponseEntity.ok().body(movieService.getMovieByTitle(title));
        } catch (MovieNotFoundException e) {
            log.error("Could not find Movie with title '{}' in the database", title);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Movie not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

    @GetMapping("{title}/showings")
    public Object getMovieShowings(@PathVariable("title") String title) {
        try {
            return ResponseEntity.ok().body(movieService.getMovieShowings(title));
        } catch (MovieNotFoundException e) {
            log.error("Could not find movie with title '{}' in the database", title);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Movie not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Movie> saveMovie (@RequestBody Movie movie){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/movies/save").toUriString());
        return ResponseEntity.created(uri).body(movieService.saveMovie(movie));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<?>deleteMovie(@PathVariable("title") String title){
        try {
            movieService.deleteMovie(title);
            return ResponseEntity.ok().build();
        } catch (MovieNotFoundException e) {
            log.error("Could not find movie with title '{}' in the database", title);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Movie not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

}
