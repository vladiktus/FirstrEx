package com.tus.springcourse.firstrex.model;

import java.time.LocalDate;
import java.util.List;

import com.tus.springcourse.firstrex.model.db.ArtistDb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class Movie {

    private final int id;
    private final String title;
    private final LocalDate releasedYear;
    private final String country;
    private final int duration;
    private final List<ArtistDb> artists;
    private final List<Genre> genres;

}
