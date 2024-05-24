package com.tus.springcourse.firstrex.repository;

import com.tus.springcourse.firstrex.exception.EntityAlreadyExist;
import com.tus.springcourse.firstrex.exception.EntityNotFound;
import com.tus.springcourse.firstrex.exception.SqlErrorException;
import com.tus.springcourse.firstrex.model.Genre;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * This class provides data access methods for managing genres in the database.
 */
@Repository
public class GenreRepository {


    private final JdbcTemplate template;


    private static final String GET_ALL_GENRES = "SELECT * FROM genre ORDER BY id";
    private static final String ADD_GENRE = "INSERT INTO genre (name) VALUES (?)";
    private static final String GET_GENRE_BY_ID = "SELECT * FROM genre WHERE id = ?";
    private static final String SEARCH_GENRE_BY_NAME = "SELECT * FROM genre WHERE `name` LIKE ?";

    private static final String DELETE_GENRE_BY_ID = "DELETE FROM genre WHERE id = ?";

    private static final String UPDATE_GENRE = "UPDATE genre SET name = ? WHERE id = ?";
    private static final String EXIST_GENRE = "SELECT count(*) FROM genre WHERE id = ?";

    private final GenreMapper genreMapper = new GenreMapper();

    /**
     * Constructs a new GenreRepository with the specified JdbcTemplate.
     *
     * @param template The JdbcTemplate to be used by this repository.
     */
    public GenreRepository(JdbcTemplate template) {
        this.template = template;
    }

    /**
     * Retrieves a list of all genres.
     *
     * @return A list of all genres.
     */
    public List<Genre> getGenreList() {
        return template.query(GET_ALL_GENRES, genreMapper);
    }

    /**
     * Adds a new genre to the database.
     *
     * @param genre The genre to be added.
     * @return The added genre.
     * @throws EntityAlreadyExist If a genre with the same name already exists.
     */
    public Genre addGenre(Genre genre) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(con -> {
                PreparedStatement ps = con.prepareStatement(ADD_GENRE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, genre.getName());
                return ps;
            }, keyHolder);

            genre.setId(keyHolder.getKeyAs(BigInteger.class).intValue());
            return genre;
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            throw new EntityAlreadyExist();
        }
    }

    /**
     * Retrieves a genre by its ID from the database.
     *
     * @param id The ID of the genre to retrieve.
     * @return The genre with the specified ID.
     * @throws EntityNotFound If no genre with the specified ID exists.
     */
    public Genre getGenreById(int id) {
        try {
            return template.queryForObject(GET_GENRE_BY_ID, new Object[]{id}, genreMapper);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new EntityNotFound();
        }
    }

    /**
     * Searches for genres by name in the database.
     *
     * @param name The name to search for.
     * @return A list of genres matching the specified name.
     */
    public List<Genre> searchGenreByName(String name) {
        return template.query(SEARCH_GENRE_BY_NAME, new Object[]{name}, genreMapper);
    }

    /**
     * Deletes a genre from the database by its ID.
     *
     * @param id The ID of the genre to delete.
     * @throws SqlErrorException If an error occurs while deleting the genre.
     */
    public void deleteGenreById(int id) {
        int count = template.update(DELETE_GENRE_BY_ID, id);
        if (count != 1) {
            throw new SqlErrorException();
        }
    }

    /**
     * Updates a genre in the database.
     *
     * @param genre The genre to update.
     * @return The updated genre.
     * @throws SqlErrorException If an error occurs while updating the genre.
     */
    public Genre updateGenre(Genre genre) {
        int count = template.update(UPDATE_GENRE, genre.getName(), genre.getId());
        if (count != 1) {
            throw new SqlErrorException();
        }
        return genre;
    }

    /**
     * Checks if a genre exists in the database by its ID.
     *
     * @param id The ID of the genre to check.
     * @return True if the genre exists, false otherwise.
     */
    public boolean exist(int id) {
        int count = template.queryForObject(EXIST_GENRE, new Object[]{id}, Integer.class);
        return count == 1;
    }

    /**
     * Mapper class for mapping ResultSet rows to Genre objects.
     */
    public static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Genre.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }

}


