package com.tus.springcourse.firstrex.controller;

import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.service.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Operation(summary = "Adding genre",
        security = {@SecurityRequirement(name = "auth")},
        responses = {@ApiResponse(responseCode = "201", description = "OK", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))
        })})
    @PostMapping("")
    public ResponseEntity<Genre> addGenre(
        @Parameter(name = "genre", description = "New genre", required = true) @RequestBody Genre genre,
        @Parameter(name = "authorization", description = "Authorization token", required = true, hidden = true) @RequestHeader("Authorization") String authorization) {
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("")
    public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
        Genre response = genreService.updateGenre(genre);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
