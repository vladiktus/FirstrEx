package com.tus.springcourse.firstrex.service;

import com.tus.springcourse.firstrex.exception.EntityNotFound;
import com.tus.springcourse.firstrex.exception.SqlErrorException;
import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.repository.GenreRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class GenreServiceTest {


    private static GenreRepository mockRepository;
    private static GenreService genreService;


    @BeforeAll
    static void setUp() {
        mockRepository = Mockito.mock(GenreRepository.class);
        genreService = new GenreService(mockRepository);
    }


    @Test
    @DisplayName("Get genre by id with successful result")
    public void getGenreByIdSuccess() {
        Genre expectedValue =
            Genre
                .builder().id(2)
                .name("Test")
                .build();
        when(mockRepository.getGenreById(2)).thenReturn(
            Genre
                .builder()
                .id(2)
                .name("Test")
                .build());
        Genre actualValue = genreService.getGenreById(2);

        assertEquals(expectedValue.getId(), actualValue.getId());
        assertEquals(expectedValue.getName(), actualValue.getName());
    }

    @Test
    public void getGenreByIdNotFoundException() {
        when(mockRepository.getGenreById(1)).thenThrow(new EntityNotFound());
        assertThrows(EntityNotFound.class, () -> genreService.getGenreById(1));
    }

    @Test
    public void searchGenreByNameSuccess() {
        List<Genre> expectedValue = List.of(
            Genre
                .builder()
                .id(2)
                .name("GenreName")
                .build(),
            Genre
                .builder()
                .id(3)
                .name("GenreName1").build());
        when(mockRepository.searchGenreByName("Test")).thenReturn(
            List.of(
                Genre
                    .builder()
                    .id(3)
                    .name("GenreName1")
                    .build(),
                Genre
                    .builder()
                    .id(2)
                    .name("GenreName")
                    .build())
        );
        List<Genre> actualValue = genreService.searchGenreByName("Test");
        assertThat(expectedValue).hasSameElementsAs(actualValue);
    }

    @Test
    public void searchGenreByNameNotFoundException() {
        when(mockRepository.searchGenreByName("GenreName")).thenThrow(new EntityNotFound());
        assertThrows(EntityNotFound.class, () -> genreService.searchGenreByName("GenreName"));
    }

    @Test
    public void getListOfGenre() {
        List<Genre> expectedValue = List.of(
            Genre
                .builder()
                .id(1)
                .name("GenreName")
                .build(),
            Genre
                .builder()
                .id(2)
                .name("GenreName1")
                .build(),
            Genre
                .builder()
                .id(3)
                .name("GenreName2")
                .build());
        when(mockRepository.getGenreList()).thenReturn(List.of(
            Genre
                .builder()
                .id(1)
                .name("GenreName")
                .build(),
            Genre
                .builder()
                .id(2)
                .name("GenreName1")
                .build(),
            Genre
                .builder()
                .id(3)
                .name("GenreName2").build()));

        List<Genre> actualValue = genreService.getListGenre();
        assertThat(expectedValue).hasSameElementsAs(actualValue);
    }


    @Test
    public void addGenreSuccess() {
        Genre expectedValue =
            Genre
                .builder()
                .id(1)
                .name("TestGenre")
                .build();
        when(mockRepository.addGenre(
            Genre
                .builder()
                .name("TestGenre")
                .build()))
            .thenReturn(
                Genre
                    .builder()
                    .id(1)
                    .name("TestGenre")
                    .build());

        Genre actualValue = genreService.addGenre(
            Genre
                .builder()
                .name("TestGenre")
                .build());

        assertEquals(expectedValue.getId(), actualValue.getId());
        assertEquals(expectedValue.getName(), actualValue.getName());
    }

    @Test
    public void addGenreSqlErrorException() {
        when(mockRepository.addGenre(
            Genre
                .builder()
                .name("TestGenre")
                .build()))
            .thenThrow(new SqlErrorException());
        assertThrows(SqlErrorException.class, () -> genreService.addGenre(
            Genre
                .builder()
                .name("TestGenre")
                .build()));
    }

    @Test
    public void deleteGenreByIdSuccess() {

    }

    @Test
    public void updateGenreSuccess() {
        Genre expectedValue = Genre
            .builder()
            .id(1)
            .name("TestGenre")
            .build();
        when(mockRepository.exist(1)).thenReturn(true);
        when(mockRepository.updateGenre(
            Genre
                .builder()
                .id(1)
                .name("TestGenre")
                .build()))
            .thenReturn(
                Genre
                    .builder()
                    .id(1)
                    .name("TestGenre")
                    .build());

        Genre actualValue = genreService.updateGenre(
            Genre
                .builder()
                .id(1)
                .name("TestGenre")
                .build());
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void updateGenreSqlErrorException() {
        when(mockRepository.updateGenre(Genre
            .builder()
            .id(1)
            .name("TestGenre")
            .build()))
            .thenThrow(new SqlErrorException());
        assertThrows(SqlErrorException.class, () -> genreService
            .updateGenre(
                Genre
                    .builder()
                    .id(1)
                    .name("TestGenre")
                    .build()));
    }

}