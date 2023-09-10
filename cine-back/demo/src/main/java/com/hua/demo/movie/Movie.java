package com.hua.demo.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hua.demo.showing.Showing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private String title;
    private String poster;
    private String director;
    //TODO add the cast
    private int duration;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String genre;
    @JsonIgnore
    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "showingMovie")
    private Collection<Showing> showings = new ArrayList<>();

}
