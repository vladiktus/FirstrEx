package com.tus.springcourse.firstrex.repository;

import com.tus.springcourse.firstrex.model.Genre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
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
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO genre (name) VALUES (?)";
            Statement st = connection.createStatement();
//            st.executeUpdate()
//            PreparedStatement pst = connection.prepareStatement(sql);
//            pst.setString(1, genre.getName());
//            ResultSet rs = pst.executeQuery();
//            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        idGenre++;
//        genre.setId(idGenre);
//        genreList.add(genre);
//        return genre;
        return null;
    }

    public Genre getGenreById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM genre WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Genre genre = Genre.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                return genre;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Genre> searchGenreByName(String name) {
        List<Genre> matchesList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM genre WHERE name = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                    if (rs.getString("name").toLowerCase().contains(name.toLowerCase())) {
                        Genre genre = Genre.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
                        matchesList.add(genre);
                    }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        List<Genre> matches = new ArrayList<>();
//        for (Genre searchGenre : genreList) {
//            if (searchGenre.getName().toLowerCase().contains(name.toLowerCase())) {
//                matches.add(searchGenre);
//            }
//        }
//        return matches;
        return new ArrayList<>(matchesList);
    }

    public void deleteGenreById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM genre WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            pst.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
