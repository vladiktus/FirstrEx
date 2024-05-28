package com.tus.springcourse.firstrex.model.db;

import com.tus.springcourse.firstrex.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ArtistDb {

    private int id;
    private String name;
    private Role role;

}
