package com.tus.springcourse.firstrex.controller;

import com.tus.springcourse.firstrex.model.Artist;
import com.tus.springcourse.firstrex.service.ArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistsController {

    @Autowired
    private ArtistsService artistsService;

    List<Artist> artistList = new ArrayList<>();

    @PostMapping("")
    public ResponseEntity<Artist> addArtists(@RequestBody Artist artist) {
        Artist response = artistsService.addArtist(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Artist>> getListOfArtists() {
        List<Artist> response = artistsService.getListOfArtists();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable int id){
        Artist response = artistsService.getArtistById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Artist>> searchArtistByName(@RequestParam("value") String name){
        List<Artist> response = artistsService.searchArtistByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> deleteArtistById(@PathVariable int id){
        artistsService.deleteArtistById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("")
    public ResponseEntity<Artist> updateArtist(@RequestBody Artist artist){
        Artist response = artistsService.updateArtist(artist);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
