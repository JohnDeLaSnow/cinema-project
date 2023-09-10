package com.hua.demo.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hua.demo.movie.Movie;
import com.hua.demo.showing.Showing;
import com.hua.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity(name="reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @ManyToOne
    @JoinColumn(name = "fk_showing")
    private Showing reservedShowing;
    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User reservedUser;
}
