package com.tus.springcourse.firstrex.service;

import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
