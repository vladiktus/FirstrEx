package com.tus.springcourse.firstrex.repository;

import com.tus.springcourse.firstrex.model.People;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PeopleRepository {

    private final DataSource dataSource;

    public PeopleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    public People addPeople(People people){
//        try(Connection connection = dataSource.getConnection()) {
//            String sql = "INSERT INTO artists (name, birth_date, gender) VALUES (?, ?, ?)";
//            PreparedStatement pst = connection.prepareStatement(sql);
//            pst.setString(1, people.getName());
//            pst.setInt(2, people.getBirthDate());
//            pst.setString(3, people.getGender());
//            pst.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }

//    public List<People> getPeopleList() {
//        List<People> peopleList = new ArrayList<>();
//        try (Connection connection = dataSource.getConnection()) {
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * From artists order by id");
//            while (rs.next()) {
//                People people = People.builder().id(rs.getInt("id"))
//                        .name(rs.getString("name"))
//                        .birthDate("2020.01.01")
//                        .gender(rs.getString("gender"))
//                        .build();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return new ArrayList<>(peopleList);
//    }

}
