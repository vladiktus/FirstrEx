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

@Repository
public class GenreRepository {


    private final JdbcTemplate template;


    private static final String GET_ALL_GENRES = "SELECT * FROM genre ORDER BY id";
    private static final String ADD_GENRE = "INSERT INTO genre (name) VALUES (?)";
    private static final String GET_GENRE_BY_ID = "SELECT * FROM genre WHERE id = ?";
    private static final String SEARCH_GENRE_BY_NAME = "select * from genre where `name` like ?";

    private static final String DELETE_GENRE_BY_ID = "DELETE FROM genre WHERE id = ?";

    private static final String UPDATE_GENRE = "UPDATE genre set name = ? where id = ?";
    private static final String EXIST_GENRE = "SELECT count(*) from genre where id = ?";

    private GenreMapper genreMapper = new GenreMapper();

    public GenreRepository(JdbcTemplate template) {
        this.template = template;
    }


    public List<Genre> getGenreList() {
        return template.query(GET_ALL_GENRES, genreMapper);
    }

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

    public Genre getGenreById(int id) {
        try {
            return template.queryForObject(GET_GENRE_BY_ID, new Object[]{id}, genreMapper);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new EntityNotFound();
        }
    }

    public List<Genre> searchGenreByName(String name) {
        return template.query(SEARCH_GENRE_BY_NAME, new Object[]{name}, genreMapper);
    }

    public void deleteGenreById(int id) {
        int count = template.update(DELETE_GENRE_BY_ID, id);
        if (count != 1) {
            throw new SqlErrorException();
        }
    }

    public Genre updateGenre(Genre genre) {
        int count = template.update(UPDATE_GENRE, genre.getName(), genre.getId());
        if (count != 1) {
            throw new SqlErrorException();
        }
        return genre;
    }

    public boolean exist(int id) {
        int count = template.queryForObject(EXIST_GENRE, new Object[]{id}, Integer.class);
        if (count != 1) {
            return false;
        }
        return true;
    }

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


