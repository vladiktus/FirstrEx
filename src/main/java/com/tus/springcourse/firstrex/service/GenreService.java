package com.tus.springcourse.firstrex.service;

import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre addGenre(Genre genre) {
        genreRepository.addGenre(genre);

    }

}
