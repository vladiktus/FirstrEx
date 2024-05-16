package com.tus.springcourse.firstrex.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Genre {

    private Integer id;
    private String name;

}
