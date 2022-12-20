package hr.fer.zari.or.backend.service;

import hr.fer.zari.or.backend.model.Artist;
import hr.fer.zari.or.backend.model.Collection;
import hr.fer.zari.or.backend.model.dto.GetCollectionDTO;
import hr.fer.zari.or.backend.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final ArtistService artistService;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository, ArtistService artistService) {
        this.collectionRepository = collectionRepository;
        this.artistService = artistService;
    }

    @Transactional
    public boolean exists(Long collectionId) {
        return collectionRepository.existsById(collectionId);
    }

    @Transactional
    public boolean existsByArtistId(Long collectionId, Long artistId) {
        return collectionRepository.existsByIdAndArtist_Id(collectionId, artistId);
    }

    @Transactional(readOnly = true)
    public List<Collection> getCollectionsByArtistId(Long artistId) {
        if (!artistService.exists(artistId)){
            throw new NoSuchElementException(String.format("Artist with id %d does not exist.", artistId));
        }
        return collectionRepository.findAllByArtist_Id(artistId);
    }

    @Transactional(readOnly = true)
    public Collection getCollectionByArtistIdAndId(Long artistId, Long collectionId) {
        if (!artistService.exists(artistId)){
            throw new NoSuchElementException(String.format("Artist with id %d does not exist.", artistId));
        }
        return collectionRepository.findCollectionByIdAndArtist_Id(collectionId, artistId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Artist with id %d has no collection with id %d",
                                artistId, collectionId)));
    }

    @Transactional
    public Collection addCollection(GetCollectionDTO collectionDTO, Long artistId) {
        Assert.notNull(collectionDTO, "Missing parameters.");
        Assert.notNull(collectionDTO.getName(), "Missing parameters.");
        Assert.notNull(collectionDTO.getType(), "Missing parameters.");

        Artist artist = artistService.getArtistById(artistId);
        Collection collection = new Collection();
        collection.setName(collectionDTO.getName());
        collection.setType(collectionDTO.getType());
        collection.setArtist(artist);
        return collectionRepository.save(collection);
    }

    @Transactional
    public Collection updateCollection(GetCollectionDTO collectionDTO, Long artistId, Long collectionId) {
        Collection collection = getCollectionByArtistIdAndId(artistId, collectionId);
        if(collectionDTO != null) {
            if(collectionDTO.getName() != null) collection.setName(collectionDTO.getName());
            if(collectionDTO.getType() != null) collection.setType(collectionDTO.getType());
        }
        return collectionRepository.save(collection);
    }

    @Transactional
    public void deleteCollection(Long artistId, Long collectionId) {
        Collection collection = getCollectionByArtistIdAndId(artistId, collectionId);

        collectionRepository.delete(collection);
    }
}
