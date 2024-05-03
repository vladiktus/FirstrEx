package com.tus.springcourse.firstrex.repository;

import com.tus.springcourse.firstrex.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreRepository {


    private final JdbcTemplate template;

    private final DataSource dataSource;

    private GenreMapper genreMapper = new GenreMapper();

    public GenreRepository(JdbcTemplate template, DataSource dataSource) {
        this.template = template;
        this.dataSource = dataSource;
    }


    public List<Genre> getGenreList() {
        String sql = "SELECT * From genre order by id";
        return template.query(sql, genreMapper);
    }

    public Genre addGenre(Genre genre) {
        String sql = "INSERT INTO genre (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, genre.getName());
            return ps;
        }, keyHolder);

        genre.setId(keyHolder.getKeyAs(BigInteger.class).intValue());
        return genre;
    }

    public Genre getGenreById(int id) {
        String sql =  "SELECT * FROM genre WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, genreMapper );
    }

    public List<Genre> searchGenreByName(String name) {
        String sql = "select * from genre where `name` like ?";
        return template.query(sql, new Object[]{name}, genreMapper);
    }

    public void deleteGenreById(int id) {
        String sql = "DELETE FROM genre WHERE id = ?";
        template.update(sql, id);
    }

    public Genre updateGenre(Genre genre) {
        String sql = " Update genre set name = ? where id = (?)";
        template.update(sql, genre.getId());
        return genre;
    }

    public boolean exist(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "Select count(*) from genre where id = " + id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int i = rs.getInt(1);
                if (i == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException("Troubles with db");
        }
        return false;
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


