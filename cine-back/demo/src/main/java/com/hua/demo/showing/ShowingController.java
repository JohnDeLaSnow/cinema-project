package com.hua.demo.showing;

import com.hua.demo.exceptions.MovieNotFoundException;
import com.hua.demo.exceptions.ShowingNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/showings")
@RequiredArgsConstructor
@Slf4j
public class ShowingController {

    public final ShowingService showingService;

    @GetMapping
    public ResponseEntity<List<Showing>> getAllShowings() {
        return ResponseEntity.ok().body(showingService.getAllShowings());
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Showing>> getShowingByState(@PathVariable("state") State state) {
        return ResponseEntity.ok().body(showingService.getShowingsByState(state));
    }

    @GetMapping("/{id}")
    public Object getShowingById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok().body(showingService.getShowingById(id));
        } catch (ShowingNotFoundException e) {
            log.error("Could not find showing with id '{}' in the database", id);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Showing not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Showing> saveShowing (@RequestBody Showing showing) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/showings/save").toUriString());
        return ResponseEntity.created(uri).body(showingService.saveShowing(showing));
    }

    @PutMapping("/{id}/{title}")
    public Object addMovieToShowing (@PathVariable int id,@PathVariable String title) {
        try {
            return ResponseEntity.ok().body(showingService.addMovieToShowing(id,title));
        } catch (ShowingNotFoundException e) {
            log.error("Could not find showing with id '{}' in the database", id);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Showing not found");
            return new ResponseEntity<>(error, null, 404);
        } catch (MovieNotFoundException e){
            log.error("Could not find movie with title '{}' in the database", title);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Movie not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteShowing(@PathVariable("id") int id){
        try {
            showingService.deleteShowing(id);
            return ResponseEntity.ok().build();
        } catch (ShowingNotFoundException e) {
            log.error("Could not find showing with id '{}' in the database", id);
            HashMap<String, String> error = new HashMap<>();
            error.put("error", "Showing not found");
            return new ResponseEntity<>(error, null, 404);
        }
    }

}
