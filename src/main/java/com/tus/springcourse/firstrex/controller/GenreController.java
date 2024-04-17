package com.tus.springcourse.firstrex.controller;

import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private List<Genre> genreList = new ArrayList<>();

    @Autowired
    private GenreService genreService;

    public GenreController() {
        Genre genre1 = Genre.builder().id(1).name("Drama").build();
        Genre genre2 = Genre.builder().id(2).name("Action").build();
        genreList.add(genre1);
        genreList.add(genre2);
    }

    @PostMapping("")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre, @RequestHeader("Authorization") String authorization) {
        Genre response = genreService.addGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public List<Genre> getListGenre() {
        return genreList;
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable int id) {
        for (Genre genre : genreList) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        return null;
    }

    @GetMapping("/search")
    public Genre getGenreById(@RequestParam("value") String name) {
        for (Genre genre : genreList) {
            if (genre.getName().equals(name)) {
                return genre;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteGenreById(@PathVariable int id) {
        for (Genre genre : genreList) {
            if (genre.getId() == id) {
                genreList.remove(genre);
            }
        }
    }

    @PutMapping("")
    public Genre updateGenre(@RequestBody Genre genre) {
        genreList.set(3, genre);
        return genre;
    }

}
