package com.hua.demo.showing;

import java.util.List;

public interface ShowingService {
    Showing getShowingById(int id);
    List<Showing> getAllShowings();
    Showing saveShowing(Showing showing);
    Showing editShowing(Showing showing);
    void deleteShowing(int id);
    Showing addMovieToShowing (int id,String title);
    List<Showing> getShowingsByState(State state);
}
