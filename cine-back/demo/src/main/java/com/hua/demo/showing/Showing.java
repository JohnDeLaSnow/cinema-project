package com.hua.demo.showing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hua.demo.movie.Movie;
import com.hua.demo.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.Collection;

@Entity(name="showing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Showing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private State state;
    private String showingDate;
    private int showingRoom;
    @ManyToOne
    @JoinColumn(name = "fk_movie")
    private Movie showingMovie;
    @JsonIgnore
    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "reservedShowing")
    private Collection<Reservation> reservations = new ArrayList<>();

}
