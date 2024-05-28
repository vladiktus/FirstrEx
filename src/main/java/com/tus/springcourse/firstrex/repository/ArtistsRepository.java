package com.tus.springcourse.firstrex.repository;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tus.springcourse.firstrex.exception.EntityAlreadyExist;
import com.tus.springcourse.firstrex.exception.EntityNotFound;
import com.tus.springcourse.firstrex.exception.SqlErrorException;
import com.tus.springcourse.firstrex.model.Artist;
import com.tus.springcourse.firstrex.util.JsonConverter;


/**
 * Repository class for managing database operations related to artists.
 */
@Repository
public class ArtistsRepository {

    /**
     * The JdbcTemplate used for interacting with the database.
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * SQL query to retrieve all artists.
     */
    private static final String GET_ALL_ARTISTS = """
        SELECT
            artists.id,
            artists.name,
            artists.birth_date,
            artists.gender,
            json_arrayagg(json_object('id', roles.id, 'name', roles.name)) AS roles
        FROM
            kino_view.artists
        LEFT JOIN
            artists_roles ON artists.id = artists_roles.artist_id
        LEFT JOIN
            roles ON artists_roles.role_id = role.id
        GROUP BY
            artists.id, artists.name
        ORDER BY
            artists.id
        """;

    /**
     * SQL query to check if an artist exists by their ID.
     */
    private static final String EXIST_ARTISTS = "SELECT count(*) FROM artists WHERE id = ?";

    /**
     * SQL query to search for artists by name.
     */
    private static final String SEARCH_ARTIST_BY_NAME = """
        SELECT
        	artists.id,
        	artists.name,
        	artists.birth_date,
        	artists.gender,
        	json_arrayagg(json_object('role', movies_artists.role)) AS roles
        FROM
        	artists
        LEFT JOIN
        	movies_artists ON artists.id = movies_artists.artist_id
        WHERE
        	artists.name LIKE ?
        GROUP BY
        	artists.id;
        """;

    /**
     * SQL query to update an artist.
     */
    private static final String UPDATE_ARTIST = """
        UPDATE artists
        SET name = ?, birth_date = ?, gender = ?
        WHERE id = ?;
        """;

    /**
     * SQL query to delete an artist by their ID.
     */
    private static final String DELETE_ARTIST_BY_ID = "DELETE FROM artists WHERE id = ?";

    /**
     * SQL query to retrieve an artist by their ID.
     */
    private static final String GET_ARTIST_BY_ID = """
        SELECT
           artists.id,
           artists.name,
           artists.birth_date,
           artists.gender,
           json_arrayagg(json_object('role', movies_artists.role)) AS roles
       FROM
           artists
       LEFT JOIN
           movies_artists ON artists.id = movies_artists.artist_id
                
       WHERE
            artists.id = ?
       GROUP BY
            artists.id;
       """;

    /**
     * SQL query to add a new artist.
     */
    private static final String ADD_ARTIST = "INSERT INTO artists (name, birth_date, gender) VALUES (?,?,?);";

    /**
     * Mapper for mapping ResultSet rows to Artist objects.
     */
    private final ArtistsMapper artistsMapper = new ArtistsMapper();

    /**
     * Constructs a new ArtistsRepository with the specified JdbcTemplate.
     *
     * @param jdbcTemplate The JdbcTemplate to be used by this repository.
     */
    public ArtistsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves a list of all artists.
     *
     * @return a list of artists
     */
    public List<Artist> getArtistList() {
        return jdbcTemplate.query(GET_ALL_ARTISTS, artistsMapper);
    }

    /**
     * Deletes an artist by their ID.
     *
     * @param id the ID of the artist to delete
     * @throws SqlErrorException if the delete operation fails
     */
    public void deleteArtistById(int id) {
        int count = jdbcTemplate.update(DELETE_ARTIST_BY_ID, id);
        if (count != 1) {
            throw new SqlErrorException();
        }
    }

    /**
     * Adds a new artist to the repository.
     *
     * @param artist the artist to add
     * @return the added artist with the generated ID
     * @throws EntityAlreadyExist if the artist already exists
     */
    public Artist addArtist(Artist artist) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(ADD_ARTIST, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, artist.getName());
                ps.setDate(2, Date.valueOf(artist.getBirthDate()));
                ps.setString(3, artist.getGender());
                return ps;
            }, keyHolder);

            artist.setId(keyHolder.getKeyAs(BigInteger.class).intValue());
            return artist;
        }
        catch (DuplicateKeyException e) {
            e.printStackTrace();
            throw new EntityAlreadyExist();
        }
    }
    /**
     * Retrieves an artist by their ID.
     *
     * @param id the ID of the artist
     * @return the artist with the given ID
     * @throws EntityNotFound if the artist is not found
     */
    public Artist getArtistById(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_ARTIST_BY_ID, artistsMapper, id);
        }
        catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new EntityNotFound();
        }
    }

    /**
     * Searches for artists by their name.
     *
     * @param name the name to search for
     * @return a list of artists matching the given name
     */
    public List<Artist> searchArtistByName(String name) {
        return jdbcTemplate.query(SEARCH_ARTIST_BY_NAME, artistsMapper, "%" + name + "%");
    }

    /**
     * Checks if an artist exists by their ID.
     *
     * @param id the ID of the artist
     * @return true if the artist exists, false otherwise
     */
    public boolean exist(int id) {
        int count = jdbcTemplate.queryForObject(EXIST_ARTISTS, Integer.class, id);
        return count == 1;
    }

    /**
     * Updates an existing artist.
     *
     * @param artist the artist with updated information
     * @return the updated artist
     * @throws SqlErrorException if the update operation fails
     */
    public Artist updateArtist(Artist artist) {
        int count = jdbcTemplate.update(UPDATE_ARTIST, artist.getName(), Date.valueOf(artist.getBirthDate()), artist.getGender(), artist.getId());
        if (count != 1) {
            throw new SqlErrorException();
        }
        return artist;
    }

    /**
     * RowMapper implementation for mapping ResultSet rows to Artist objects.
     */
    public static class ArtistsMapper implements RowMapper<Artist> {
        @Override
        public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Artist.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .gender(rs.getString("gender"))
                .birthDate(rs.getDate("birth_date").toLocalDate())
                .roles(JsonConverter.getListOfRoles(rs.getString("roles")))
                .build();
        }
    }
}

