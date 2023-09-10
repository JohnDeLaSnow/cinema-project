package com.hua.demo;

import com.hua.demo.movie.Movie;
import com.hua.demo.movie.MovieServiceImpl;
import com.hua.demo.reservation.ReservationServiceImpl;
import com.hua.demo.showing.Showing;
import com.hua.demo.showing.ShowingServiceImpl;
import com.hua.demo.showing.State;
import com.hua.demo.user.Role;
import com.hua.demo.user.User;
import com.hua.demo.user.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserServiceImpl userService, MovieServiceImpl movieService, ShowingServiceImpl showingService, ReservationServiceImpl reservationService) {
        return args -> {
            //Demo Users
            userService.saveUser(new User("admin","john","doe","pass123",Role.ADMIN,null));
            userService.saveUser(new User("jamesdoe@hotmail.com","james","doe","1234",Role.USER,null));
            userService.saveUser(new User("jean@hotmail.com","jean","doe","1234",Role.USER,null));
            userService.saveUser(new User("jimdoe@hotmail.com","jim","doe","1234",Role.EMPLOYEE,null));
            userService.saveUser(new User("jack@hotmail.com","jack","doe","1234",Role.EMPLOYER,null));
            //Demo Movies
            movieService.saveMovie(new Movie("AVATAR","/images/AVATAR_poster.jpg","James Cameron",162,"A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.\n" +
                    "\n" +
                    "When his brother is killed in a robbery, paraplegic Marine Jake Sully decides to take his place in a mission on the distant world of Pandora.","Sci-Fi, Adventure, Action, Fantasy",null));
            movieService.saveMovie(new Movie("TITANIC","/images/TITANIC_poster.jpg","James Cameron",194,"Titanic is an epic, action-packed romance set against the ill-fated maiden voyage of the R.M.S. Titanic. She was the most luxurious liner of her era 'the ship of dreams' which carried over 1,500 people to their death in the ice cold waters of the North Atlantic.","Drama, Romance",null));
            movieService.saveMovie(new Movie("TERMINATOR","/images/TERMINATOR_poster.jpg","James Cameron",102,"Disguised as a human, a cyborg assassin known as a Terminator (Arnold Schwarzenegger) travels from 2029 to 1984 to kill Sarah Connor (Linda Hamilton). Sent to protect Sarah is Kyle Reese (Michael Biehn), who divulges the coming of Skynet, an artificial intelligence system that will spark a nuclear holocaust. Sarah is targeted because Skynet knows that her unborn son will lead the fight against them. With the virtually unstoppable Terminator in hot pursuit, she and Kyle attempt to escape.","Sci-Fi, Action, Mystery and Thriller",null));
            movieService.saveMovie(new Movie("AVENGERS: ENDGAME","/images/AVENGERS_ENDGAME_poster.jpg","Russo Brothers",143,"Adrift in space with no food or water, Tony Stark sends a message to Pepper Potts as his oxygen supply starts to dwindle. Meanwhile, the remaining Avengers -- Thor, Black Widow, Captain America and Bruce Banner -- must figure out a way to bring back their vanquished allies for an epic showdown with Thanos -- the evil demigod who decimated the planet and the universe.","Action, Adventure, Fantasy, Sci-Fi",null));
            movieService.saveMovie(new Movie("UP","/images/UP_poster.jpg","Pete Docter",96,"Carl Fredricksen (Ed Asner), a 78-year-old balloon salesman, is about to fulfill a lifelong dream. Tying thousands of balloons to his house, he flies away to the South American wilderness. But curmudgeonly Carl's worst nightmare comes true when he discovers a little boy named Russell is a stowaway aboard the balloon-powered house.","Kids & Family",null));
            movieService.saveMovie(new Movie("TOP GUN: MAVERICK","/images/TOP_GUN_MAVERICK_poster.jpg","Joseph Kosinski",131,"After more than thirty years of service as one of the Navy’s top aviators, Pete 'Maverick' Mitchell (Tom Cruise) is where he belongs, pushing the envelope as a courageous test pilot and dodging the advancement in rank that would ground him. When he finds himself training a detachment of Top Gun graduates for a specialized mission the likes of which no living pilot has ever seen, Maverick encounters Lt. Bradley Bradshaw (Miles Teller), call sign: 'Rooster', the son of Maverick’s late friend and Radar Intercept Officer Lt. Nick Bradshaw, aka 'Goose'. Facing an uncertain future and confronting the ghosts of his past, Maverick is drawn into a confrontation with his own deepest fears, culminating in a mission that demands the ultimate sacrifice from those who will be chosen to fly it.","Action, Adventure, Drama",null));
            //Demo Showings
            showingService.saveShowing(new Showing(0,State.FINISHED,"03/16/2023 20:30:00",1,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"03/16/2023 20:30:00",2,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"03/16/2023 20:30:00",3,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"03/16/2023 20:30:00",4,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"03/16/2023 20:30:00",5,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"03/16/2023 20:30:00",6,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"04/16/2023 20:30:00",1,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"04/16/2023 20:30:00",2,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"04/16/2023 20:30:00",3,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"04/16/2023 20:30:00",4,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"04/16/2023 20:30:00",5,null,null));
            showingService.saveShowing(new Showing(0,State.SCHEDULED,"04/16/2023 20:30:00",6,null,null));
            //Adding movies to showings
            showingService.addMovieToShowing(1,"AVATAR");
            showingService.addMovieToShowing(2,"TITANIC");
            showingService.addMovieToShowing(3,"TERMINATOR");
            showingService.addMovieToShowing(4,"AVENGERS: ENDGAME");
            showingService.addMovieToShowing(5,"TOP GUN: MAVERICK");
            showingService.addMovieToShowing(6,"UP");
            showingService.addMovieToShowing(7,"AVATAR");
            showingService.addMovieToShowing(8,"TITANIC");
            showingService.addMovieToShowing(9,"TERMINATOR");
            showingService.addMovieToShowing(10,"AVENGERS: ENDGAME");
            showingService.addMovieToShowing(11,"TOP GUN: MAVERICK");
            showingService.addMovieToShowing(12,"UP");
            //Demo Reservations
            reservationService.saveReservation("jean@hotmail.com",1);
            reservationService.saveReservation("jean@hotmail.com",3);
            reservationService.saveReservation("jean@hotmail.com",5);
            reservationService.saveReservation("jean@hotmail.com",7);
            reservationService.saveReservation("jamesdoe@hotmail.com",1);
            reservationService.saveReservation("jamesdoe@hotmail.com",2);
            reservationService.saveReservation("jamesdoe@hotmail.com",4);
            reservationService.saveReservation("jamesdoe@hotmail.com",6);
            reservationService.saveReservation("jamesdoe@hotmail.com",8);

        };
    }

}
