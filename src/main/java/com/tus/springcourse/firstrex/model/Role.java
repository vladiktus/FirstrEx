package com.tus.springcourse.firstrex.model;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Role {

    private int id;
    private String name;

}
