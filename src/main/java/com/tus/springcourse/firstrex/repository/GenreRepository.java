package com.tus.springcourse.firstrex.repository;

import com.tus.springcourse.firstrex.model.Genre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreRepository {

    private final DataSource dataSource;

    public GenreRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Genre> getGenreList() {
        List<Genre> genreList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * From genre order by id");
            while (rs.next()) {
                Genre genre = Genre.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                genreList.add(genre);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(genreList);
    }

    public Genre addGenre(Genre genre) {
//        idGenre++;
//        genre.setId(idGenre);
//        genreList.add(genre);
//        return genre;
        return null;
    }

    public Genre getGenreById(int id) {
//        for (Genre genre : genreList) {
//            if (genre.getId() == id) {
//                return genre;
//            }
//        }
//        return null;
        return null;
    }

    public List<Genre> searchGenreByName(String name) {
//        List<Genre> matches = new ArrayList<>();
//        for (Genre searchGenre : genreList) {
//            if (searchGenre.getName().toLowerCase().contains(name.toLowerCase())) {
//                matches.add(searchGenre);
//            }
//        }
//        return matches;
        return null;
    }

    public void deleteGenreById(int id) {
//        for (Genre genre : genreList) {
//            if (genre.getId() == id) {
//                genreList.remove(genre);
//            }
//        }
    }

    public Genre updateGenre(Genre genre) {
//        for (Genre setNewGenreName : genreList) {
//            if (genre.getId() == setNewGenreName.getId()) {
//                setNewGenreName.setName(genre.getName());
//            }
//        }
//        return genre;
        return null;
    }
}
