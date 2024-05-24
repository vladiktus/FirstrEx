package com.tus.springcourse.firstrex.service;

import com.tus.springcourse.firstrex.exception.EntityNotFound;
import com.tus.springcourse.firstrex.model.Artist;
import com.tus.springcourse.firstrex.model.Role;
import com.tus.springcourse.firstrex.repository.ArtistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing artists.
 */
@Service
public class ArtistsService {

    @Autowired
    private ArtistsRepository artistsRepository;

    /**
     * Adds a new artist to the repository.
     *
     * @param artist the artist to be added
     * @return the added artist
     */
    @Transactional
    public Artist addArtist(Artist artist) {
        Artist newArtist = artistsRepository.addArtist(artist);
        for (Role role : artist.getRoles()) {
            artistsRepository.addArtistRole(newArtist.getId(), role.getId());
        }
        return newArtist;
    }

    /**
     * Retrieves the list of all artists.
     *
     * @return a list of artists
     */
    public List<Artist> getListOfArtists() {
        return artistsRepository.getArtistList();
    }

    /**
     * Searches for artists by their name.
     *
     * @param name the name to search for
     * @return a list of artists matching the given name
     */
    public List<Artist> searchArtistByName(String name){
        return artistsRepository.searchArtistByName(name);
    }

    /**
     * Retrieves an artist by their ID.
     *
     * @param id the ID of the artist
     * @return the artist with the given ID
     */
    public Artist getArtistById(int id){
        return artistsRepository.getArtistById(id);
    }

    /**
     * Deletes an artist by their ID.
     *
     * @param id the ID of the artist to be deleted
     */
    @Transactional
    public void deleteArtistById(int id) {
        artistsRepository.deleteArtistRole(id);
        artistsRepository.deleteArtistById(id);
    }

    /**
     * Updates an existing artist.
     *
     * @param artist the artist with updated information
     * @return the updated artist
     * @throws EntityNotFound if the artist does not exist
     */
    @Transactional
    public Artist updateArtist(Artist artist){
        if (!artistsRepository.exist(artist.getId())) {
            throw new EntityNotFound();
        }
        Artist updatedArtist = artistsRepository.updateArtist(artist);
        artistsRepository.deleteArtistRole(updatedArtist.getId());
        for (Role role : artist.getRoles()) {
            artistsRepository.addArtistRole(updatedArtist.getId(), role.getId());
        }
        return updatedArtist;
    }
}

