package com.tus.springcourse.firstrex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tus.springcourse.firstrex.exception.EntityNotFound;
import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.repository.GenreRepository;

/**
 * This class represents a service layer for managing operations related to genres.
 * It serves as an intermediary between the application's business logic and the data access layer
 * (implemented by GenreRepository).
 */
@Service
public class GenreService {

    /**
     * Repository for managing genre-related database operations.
     */
    private final GenreRepository genreRepository;

    /**
     * Constructs a new GenreService with the specified GenreRepository.
     *
     * @param genreRepository The GenreRepository to be used by this service.
     */
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * Adds a new genre.
     *
     * @param genre The genre to be added.
     * @return The added genre.
     */
    public Genre addGenre(Genre genre) {
        return genreRepository.addGenre(genre);
    }

    /**
     * Retrieves a genre by its ID.
     *
     * @param id The ID of the genre to retrieve.
     * @return The genre with the specified ID.
     */
    public Genre getGenreById(int id) {
        return genreRepository.getGenreById(id);
    }

    /**
     * Searches for genres by name.
     *
     * @param name The name to search for.
     * @return A list of genres matching the specified name.
     */
    public List<Genre> searchGenreByName(String name) {
        return genreRepository.searchGenreByName(name);
    }

    /**
     * Deletes a genre by its ID.
     *
     * @param id The ID of the genre to delete.
     */
    public void deleteGenreById(int id) {
        genreRepository.deleteGenreById(id);
    }

    /**
     * Retrieves a list of all genres.
     *
     * @return A list of all genres.
     */
    public List<Genre> getListGenre() {
        return genreRepository.getGenreList();
    }

    /**
     * Updates a genre.
     *
     * @param genre The genre to update.
     * @return The updated genre.
     * @throws EntityNotFound If the genre does not exist.
     */
    public Genre updateGenre(Genre genre) throws EntityNotFound {
        if (!genreRepository.exist(genre.getId())) {
            throw new EntityNotFound();
        }
        return genreRepository.updateGenre(genre);
    }
}




