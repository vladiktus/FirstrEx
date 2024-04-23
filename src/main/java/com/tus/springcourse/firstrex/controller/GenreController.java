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
    public ResponseEntity<List<Genre>> getListGenre() {
        List<Genre> response = genreService.getListGenre();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable int id) {
        Genre response = genreService.getGenreById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Genre>> searchGenreByName(@RequestParam("value") String name) {
       List<Genre> response = genreService.searchGenreByName(name);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenreById(@PathVariable int id) {
      genreService.deleteGenreById(id);
//      return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("")
    public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
        Genre response = genreService.updateGenre(genre);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
