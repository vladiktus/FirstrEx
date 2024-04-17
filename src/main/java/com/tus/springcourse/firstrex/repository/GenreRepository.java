package com.tus.springcourse.firstrex.repository;

import com.tus.springcourse.firstrex.model.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreRepository {
    private List<Genre> genreList = new ArrayList<>();


    public GenreRepository() {
        Genre genre1 = Genre.builder().id(1).name("Drama").build();
        Genre genre2 = Genre.builder().id(2).name("Action").build();
        genreList.add(genre1);
        genreList.add(genre2);
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    // Метод на добавление
    // добавление неименяемого списка

    public Genre addGenre(Genre genre){
        genreList.add(genre);
        return genre;
    }

}
