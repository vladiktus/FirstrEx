package com.tus.springcourse.firstrex.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@Setter

public class People {

    private int id;
    private String name;
    private String gender;
    private LocalDate birthDate;

}
