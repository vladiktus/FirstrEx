package com.tus.springcourse.firstrex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Artist {

    private int id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private List<RoleOld> roles;

}
