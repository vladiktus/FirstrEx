package com.tus.springcourse.firstrex.converter;

import com.tus.springcourse.firstrex.model.Artist;
import com.tus.springcourse.firstrex.model.db.ArtistDb;
import com.tus.springcourse.firstrex.model.dto.ArtistDto;

public class ArtistConverter {

    public static ArtistDto toArtistDto(ArtistDb artistdb) {

        return ArtistDto.builder()
            .id(artistdb.getId())
            .name(artistdb.getName())
            .build();
    }

}
