package com.tus.springcourse.firstrex.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tus.springcourse.firstrex.model.Movie;
import com.tus.springcourse.firstrex.model.dto.MovieDto;
import com.tus.springcourse.firstrex.service.MovieService;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public ResponseEntity<List<MovieDto>> getMovieList() {
        List<MovieDto> responce = movieService.getMovieList();
        return ResponseEntity.status(HttpStatus.OK).body(responce);
    }

}
