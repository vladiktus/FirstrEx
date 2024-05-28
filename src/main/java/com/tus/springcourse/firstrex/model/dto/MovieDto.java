package com.tus.springcourse.firstrex.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.tus.springcourse.firstrex.model.Genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Builder
@Getter
@Setter
public class MovieDto {

    private final int id;
    private final String title;
    private final LocalDate releasedYear;
    private final String country;
    private final int duration;
    private final List<ArtistDto> directors;
    private final List<ArtistDto> writers;
    private final List<ArtistDto> actors;
    private final List<Genre> genres;

}
