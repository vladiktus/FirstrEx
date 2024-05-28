package com.tus.springcourse.firstrex.converter;

import java.util.ArrayList;
import java.util.List;

import com.tus.springcourse.firstrex.model.Movie;
import com.tus.springcourse.firstrex.model.db.ArtistDb;
import com.tus.springcourse.firstrex.model.dto.ArtistDto;
import com.tus.springcourse.firstrex.model.dto.MovieDto;

public class MovieConverter {

    public static MovieDto toMovieDto(Movie movie) {
        List<ArtistDto> directors = new ArrayList<>();
        List<ArtistDto> writers = new ArrayList<>();
        List<ArtistDto> actors = new ArrayList<>();

        for (ArtistDb artistDb : movie.getArtists()) {
            switch (artistDb.getRole()) {
                case DIRECTOR:
                    directors.add(ArtistConverter.toArtistDto(artistDb));
                    break;
                case WRITER:
                    writers.add(ArtistConverter.toArtistDto(artistDb));
                    break;
                case ACTOR:
                    actors.add(ArtistConverter.toArtistDto(artistDb));
                    break;
            }
        }
        return MovieDto.builder().id(movie.getId())
            .title(movie.getTitle())
            .country(movie.getCountry())
            .duration(movie.getDuration())
            .genres(movie.getGenres())
            .releasedYear(movie.getReleasedYear())
            .directors(directors)
            .writers(writers)
            .actors(actors)
            .build();
    }
}
