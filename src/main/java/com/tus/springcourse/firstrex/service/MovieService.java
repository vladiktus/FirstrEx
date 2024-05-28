package com.tus.springcourse.firstrex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tus.springcourse.firstrex.converter.MovieConverter;
import com.tus.springcourse.firstrex.model.Movie;
import com.tus.springcourse.firstrex.model.dto.MovieDto;
import com.tus.springcourse.firstrex.repository.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;

    }

    public List<MovieDto> getMovieList() {
        return movieRepository.getMovieList().stream().map(MovieConverter::toMovieDto).toList();
    }
}
