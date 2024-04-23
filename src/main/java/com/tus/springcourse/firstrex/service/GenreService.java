package com.tus.springcourse.firstrex.service;

import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;


    public Genre addGenre(Genre genre) {
        List<Genre> listCheck = genreRepository.getGenreList();
        for (Genre genreCheck : listCheck) {
            if (genreCheck.getName().toLowerCase().equals(genre.getName().toLowerCase())) {
                return null;
            }
        }
        return  genreRepository.addGenre(genre);
    }

    public Genre getGenreById(int id) {
        return genreRepository.getGenreById(id);
    }

    public List<Genre> searchGenreByName(String name) {
        return genreRepository.searchGenreByName(name);
    }

    public void deleteGenreById(int id) {
        genreRepository.deleteGenreById(id);
    }

    public List<Genre> getListGenre() {
        return genreRepository.getGenreList();
    }

    public Genre updateGenre(Genre genre) {
        List<Genre> listCheck = genreRepository.getGenreList();
        for (Genre genreCheck : listCheck) {
            if (genreCheck.getName().toLowerCase().equals(genre.getName().toLowerCase())) {
                return null;
            }
        }
        return genreRepository.updateGenre(genre);
    }
}
