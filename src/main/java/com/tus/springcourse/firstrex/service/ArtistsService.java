package com.tus.springcourse.firstrex.service;

import com.tus.springcourse.firstrex.model.Artist;
import com.tus.springcourse.firstrex.model.Role;
import com.tus.springcourse.firstrex.repository.ArtistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistsService {

    @Autowired
    private ArtistsRepository artistsRepository;

    @Transactional
    public Artist addArtist(Artist artist) {
        Artist newArtist = artistsRepository.addArtist(artist);
        for (Role role : artist.getRoles()) {
            artistsRepository.addArtistRole(newArtist.getId(), role.getId());
        }
        return newArtist;
    }

    public List<Artist> getListOfArtists() {
        return artistsRepository.getArtistList();
    }

    public List<Artist> searchArtistByName(String name){
        return artistsRepository.searchArtistByName(name);
    }

    public Artist getArtistById(int id){
        return artistsRepository.getArtistById(id);
    }

    public void deleteArtistById(int id) {
        artistsRepository.deleteArtistById(id);
    }

    @Transactional
    public Artist updateArtist(Artist artist){
        Artist updatedArtist = artistsRepository.updateArtist(artist);
        artistsRepository.deleteArtistRole(updatedArtist.getId());
        for (Role role : artist.getRoles()) {
            artistsRepository.addArtistRole(updatedArtist.getId(), role.getId());
        }
        return  updatedArtist;
    }

}
