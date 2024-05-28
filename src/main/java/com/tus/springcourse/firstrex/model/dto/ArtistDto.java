package com.tus.springcourse.firstrex.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Builder
@Getter
@Setter
public class ArtistDto {

    private final int id;
    private final String name;

}
