package com.tus.springcourse.firstrex.service;

import com.tus.springcourse.firstrex.model.People;
import com.tus.springcourse.firstrex.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public People addPeople(People people) {
//        List<People> listCheck = peopleRepository.getPeopleList();
//        for (People peopleCheck : listCheck) {
//            if (peopleCheck.getName().toLowerCase().equals(people.getName().toLowerCase())) {
//                return null;
//            }
//        }
        return null;
//        return  peopleRepository.addPeople(people);
    }

    public List<People> getListPeople() {
        return null;
//        return peopleRepository.getPeopleList();
    }

//    public Genre getGenreById(int id) {
//        return peopleRepository.getGenreById(id);
//    }

}
