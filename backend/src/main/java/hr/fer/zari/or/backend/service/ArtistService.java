package hr.fer.zari.or.backend.service;

import hr.fer.zari.or.backend.model.Artist;
import hr.fer.zari.or.backend.model.dto.GetArtistDTO;
import hr.fer.zari.or.backend.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public boolean exists(Long artistId) {
        return artistId != null && artistRepository.existsById(artistId);
    }

    @Transactional(readOnly = true)
    public List<Artist> getArtists() {
        return artistRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Artist getArtistById(long id) {
        return artistRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Artist with id %d does not exist.", id)));
    }

    @Transactional
    public Artist addArtist(GetArtistDTO artistDTO) {
        Assert.notNull(artistDTO, "Missing parameters.");
        Assert.notNull(artistDTO.getName(), "Missing parameters.");

        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        return artistRepository.save(artist);
    }

    @Transactional
    public Artist updateArtist(GetArtistDTO artistDTO, Long artistId) {
        Artist artist = getArtistById(artistId);
        if(artistDTO != null) {
            if(artistDTO.getName() != null) artist.setName(artistDTO.getName());
        }
        return artistRepository.save(artist);
    }

    @Transactional
    public void deleteArtist(Long artistId) {
        Artist artist = getArtistById(artistId);

        artistRepository.delete(artist);
    }
}
