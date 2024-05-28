package com.tus.springcourse.firstrex.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tus.springcourse.firstrex.model.Artist;
import com.tus.springcourse.firstrex.model.Movie;
import com.tus.springcourse.firstrex.util.JsonConverter;

@Repository
public class MovieRepository {

    private final JdbcTemplate jdbcTemplate;

    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final MovieMapper movieMapper = new MovieMapper();
    private final static String GET_MOVIE_LIST = """
        SELECT
            movies.id,
            movies.title,
            movies.release_year,
            movies.rating,
            movies.country,
            movies.duration,
            json_arrayagg( json_object('id', genre.id, 'name', genre.name)) AS genres,
        	json_arrayagg(json_object('id', artists.id, 'name', artists.name, 'role', movies_artists.role )) AS artists
        FROM
            kino_view.movies
        LEFT JOIN
            movies_genres ON movies.id = movies_genres.movie_id
        LEFT JOIN
        	 genre ON genre.id = movies_genres.genre_id
        LEFT JOIN
            movies_artists ON movies.id = movies_artists.movie_id
        LEFT JOIN
            artists ON artists.id = movies_artists.artist_id
        WHERE
            movies.id = 1
        GROUP BY
            movies.id;
        """;

    public List<Movie> getMovieList() {
        return jdbcTemplate.query(GET_MOVIE_LIST, movieMapper);
    }

    public static class MovieMapper implements RowMapper<Movie> {

        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Movie.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .country(rs.getString("country"))
                .releasedYear(rs.getDate("release_year").toLocalDate())
                .genres(JsonConverter.getListOfGenres(rs.getString("genres")))
                .artists(JsonConverter.getListOfArtistDb(rs.getString("artists")))
                .build();
        }
    }

}
