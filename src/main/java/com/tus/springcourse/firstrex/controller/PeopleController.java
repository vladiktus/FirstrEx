package com.tus.springcourse.firstrex.controller;

import com.tus.springcourse.firstrex.model.Genre;
import com.tus.springcourse.firstrex.model.People;
import com.tus.springcourse.firstrex.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    List<People> peopleList = new ArrayList<>();

    @PostMapping("")
    public ResponseEntity<People> addPeople(@RequestBody People people) {
        People response = peopleService.addPeople(people);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<People>> getListOfPeople() {
        List<People> responce = peopleService.getListPeople();
        return ResponseEntity.status(HttpStatus.OK).body(responce);
    }

}
