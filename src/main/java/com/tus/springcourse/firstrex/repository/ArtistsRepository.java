package com.tus.springcourse.firstrex.repository;

import com.tus.springcourse.firstrex.exception.EntityAlreadyExist;
import com.tus.springcourse.firstrex.exception.EntityNotFound;
import com.tus.springcourse.firstrex.exception.SqlErrorException;
import com.tus.springcourse.firstrex.model.Artist;
import com.tus.springcourse.firstrex.util.JsonConverter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;


@Repository
public class ArtistsRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_ARTISTS = "SELECT \n" +
            "    artists.id, \n" +
            "    artists.name, \n" +
            "    artists.birth_date,\n" +
            "    artists.gender,\n" +
            "    json_arrayagg(json_object('id', roles.id, 'name', roles.name)) AS roles\n" +
            "FROM \n" +
            "    kino_view.artists \n" +
            "LEFT JOIN \n" +
            "    artists_roles ON artists.id = artists_roles.artists_id \n" +
            "LEFT JOIN \n" +
            "    roles ON artists_roles.roles_id = roles.id \n" +
            "GROUP BY \n" +
            "    artists.id, artists.name \n" +
            "ORDER BY \n" +
            "    artists.id ";

    private static final String EXIST_ARTISTS = "SELECT count(*) FROM artists WHERE id = ?";

    private static final String SEARCH_ATRIST_BY_NAME = """
            SELECT\s
                artists.id,\s
                artists.name,\s
                artists.birth_date,\s
                artists.gender,\s
                JSON_ARRAYAGG(JSON_OBJECT('id', roles.id, 'name', roles.name)) AS roles
            FROM\s
                artists\s
            LEFT JOIN\s
                artists_roles ON artists.id = artists_roles.artists_id\s
            LEFT JOIN\s
                roles ON artists_roles.roles_id = roles.id\s
            WHERE\s
                artists.name LIKE "Jason Statham"
            GROUP BY\s
                artists.id, artists.name, artists.birth_date, artists.gender;
            """;

    private static final String UPDATE_ARTIST = """
            UPDATE artists
            SET name = ?, birth_date = ?, gender = ?
            WHERE id = ?;          
            """;
    private static final String ADD_ARTIST_ROLE = "INSERT INTO artists_roles (artists_id, roles_id) VALUES (?, ?)";

    private static final String DELETE_ARTIST_BY_ID = "DELETE FROM artists WHERE id = ?";
    private static final String DELETE_ARTIST_ROLES = "DELETE FROM artists_roles WHERE artist_id = ?";
    private static final String GET_ARTIST_BY_ID = "SELECT \n" +
            "    artists.id, \n" +
            "    artists.name, \n" +
            "    artists.birth_date, \n" +
            "    artists.gender, \n" +
            "    JSON_ARRAYAGG(JSON_OBJECT('id', roles.id, 'name', roles.name)) AS roles\n" +
            "FROM \n" +
            "    artists \n" +
            "LEFT JOIN \n" +
            "    artists_roles ON artists.id = artists_roles.artists_id \n" +
            "LEFT JOIN \n" +
            "    roles ON artists_roles.roles_id = roles.id \n" +
            "WHERE \n" +
            "    artists.id = ?\n" +
            "GROUP BY \n" +
            "    artists.id, artists.name, artists.birth_date, artists.gender;\n";

    private static final String ADD_ARTIST = "INSERT INTO artists (name, birth_date, gender)\n" +
            "VALUES (?,?,?);";

    private final ArtistsMapper artistsMapper = new ArtistsMapper();

    public ArtistsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Artist> getArtistList() {
        return jdbcTemplate.query(GET_ALL_ARTISTS, artistsMapper);
    }

    public void deleteArtistById(int id) {
        int count = jdbcTemplate.update(DELETE_ARTIST_BY_ID, id);
        if (count != 1) {
            throw new SqlErrorException();
        }
    }

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
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            throw new EntityAlreadyExist();
        }
    }

    public void deleteArtistRole(int artistId) {
        jdbcTemplate.update(DELETE_ARTIST_ROLES, artistId);
    }

    public void addArtistRole(int artistId, int roleId) {
        jdbcTemplate.update(ADD_ARTIST_ROLE, artistId, roleId);
    }

    public Artist getArtistById(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_ARTIST_BY_ID, new Object[]{id}, artistsMapper);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new EntityNotFound();
        }
    }

    public List<Artist> searchArtistByName(String name) {
        return jdbcTemplate.query(SEARCH_ATRIST_BY_NAME, new Object[]{name}, artistsMapper);
    }

    public boolean exist(int id) {
        int count = jdbcTemplate.queryForObject(EXIST_ARTISTS, new Object[]{id}, Integer.class);
        return count == 1;
    }

    public Artist updateArtist(Artist artist) {
        int count = jdbcTemplate.update(UPDATE_ARTIST, artist.getName(), Date.valueOf(artist.getBirthDate()), artist.getGender(), artist.getRoles());
        if (count != 1) {
            throw new SqlErrorException();
        }
        return artist;
    }

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
