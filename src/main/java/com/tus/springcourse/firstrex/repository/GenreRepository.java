package com.tus.springcourse.firstrex.repository;

import com.tus.springcourse.firstrex.exception.EntityNotFound;
import com.tus.springcourse.firstrex.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreRepository {

    private final JdbcTemplate template;

    private final DataSource dataSource;

    public GenreRepository(JdbcTemplate template, DataSource dataSource) {
        this.template = template;
        this.dataSource = dataSource;
    }

    public List<Genre> getGenreList() {
        String sql = "SELECT * From genre order by id";
        List<Genre> genreList = template.queryForList(sql, Genre.class);

//        List<Genre> genreList = new ArrayList<>();
//        try (Connection connection = dataSource.getConnection()) {
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * From genre order by id");
//            while (rs.next()) {
//                Genre genre = Genre.builder()
//                        .id(rs.getInt("id"))
//                        .name(rs.getString("name"))
//                        .build();
//                genreList.add(genre);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return genreList;
    }

    public Genre addGenre(Genre genre) {
//        String sql = "INSERT INTO genre (name) VALUES (?)";
//        template.update(sql, );
//        try (Connection connection = dataSource.getConnection()) {
//            String sql = "INSERT INTO genre (name) VALUES (?)";
//            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            pst.setString(1, genre.getName());
//            pst.executeUpdate();
//            ResultSet rs = pst.getGeneratedKeys();
//            int autoIncKeyFromApi = -1;
//            if (rs.next()) {
//                autoIncKeyFromApi = rs.getInt(1);
//                genre.setId(autoIncKeyFromApi);
//            }
//            System.out.println("Key returned from getGeneratedKeys():"
//                    + autoIncKeyFromApi);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        idGenre++;
//        genre.setId(idGenre);
//        genreList.add(genre);
//        return genre;
        return genre;
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
            throw new RuntimeException("Troubles with db");
        }
        return null;
    }

    public List<Genre> searchGenreByName(String name) {
        List<Genre> matchesList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "select * from genre where `name` like ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Genre genre = Genre.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                matchesList.add(genre);
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
            String sql = "DELETE FROM genre WHERE id = " + id;
            PreparedStatement pst = connection.prepareStatement(sql);
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
        String sql= " Update genre set name = ? where id = (?)";
        template.update(sql, genre.getId());
//        try (Connection connection = dataSource.getConnection()) {
////            String sql = "Update genre set name = '" + genre.getName() + "' WHERE id = " + genre.getId();
////            PreparedStatement pst = connection.prepareStatement(sql);
//////            pst.setString(1, genre.getName());
//////            pst.setInt(2, genre.getId() );
////            pst.executeUpdate(sql);
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
//////        for (Genre setNewGenreName : genreList) {
//////            if (genre.getId() == setNewGenreName.getId()) {
//////                setNewGenreName.setName(genre.getName());
//////            }
//////        }
        return genre;
    }

    public boolean exist(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "Select count(*) from genre where id = " + id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int i = rs.getInt(1);
                if(i == 1){
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

}
