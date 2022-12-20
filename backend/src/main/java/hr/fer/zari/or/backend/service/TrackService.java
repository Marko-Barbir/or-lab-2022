package hr.fer.zari.or.backend.service;

import hr.fer.zari.or.backend.model.Collection;
import hr.fer.zari.or.backend.model.Track;
import hr.fer.zari.or.backend.model.dto.GetTrackDTO;
import hr.fer.zari.or.backend.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final ArtistService artistService;
    private final CollectionService collectionService;

    @Autowired
    public TrackService(TrackRepository trackRepository, ArtistService artistService, CollectionService collectionService) {
        this.trackRepository = trackRepository;
        this.artistService = artistService;
        this.collectionService = collectionService;
    }

    @Transactional(readOnly = true)
    public List<Track> getTracksByArtistId_CollectionId(Long artistId, Long collectionId) {
        if (!artistService.exists(artistId)){
            throw new NoSuchElementException(String.format("Artist with id %d does not exist.", artistId));
        }
        if (!collectionService.exists(collectionId)){
            throw new NoSuchElementException(String.format("Collection with id %d does not exist.", collectionId));
        }
        return trackRepository.findAllByCollection_IdAndCollection_Artist_Id(collectionId, artistId);
    }

    @Transactional(readOnly = true)
    public Track getTrackByArtistId_CollectionId_Id(Long artistId, Long collectionId, Long trackId) {
        if (!artistService.exists(artistId)){
            throw new NoSuchElementException(String.format("Artist with id %d does not exist.", artistId));
        }
        if (!collectionService.existsByArtistId(collectionId, artistId)){
            throw new NoSuchElementException(String.format(
                    "Collection with id %d does not exist or does not belong to artist with id %d.",
                    collectionId, artistId));
        }
        return trackRepository.findByIdAndCollection_IdAndCollection_Artist_Id(trackId, collectionId, artistId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Collection with id %d has no track with id %d.",
                                collectionId, trackId)));
    }

    @Transactional
    public Track addTrack(GetTrackDTO trackDTO, Long artistId, Long collectionId) {
        Assert.notNull(trackDTO, "Missing parameters.");
        Assert.notNull(trackDTO.getName(), "Missing parameters.");

        Collection collection = collectionService.getCollectionByArtistIdAndId(artistId, collectionId);

        Track track = new Track();
        track.setCollection(collection);
        track.setDuration(trackDTO.getDuration_seconds());
        track.setName(trackDTO.getName());
        track.setSpotifyStreams(trackDTO.getSpotify_streams());
        track.setYoutubeStreams(trackDTO.getYoutube_streams());
        track.setYoutubeLikes(trackDTO.getYoutube_likes());
        track.setYoutubeDislikes(trackDTO.getYoutube_dislikes_estimated());
        track.setExplicit(trackDTO.getIs_explicit());
        track.getArtists().add(collection.getArtist());

        return trackRepository.save(track);
    }

    @Transactional
    public Track updateTrack(GetTrackDTO trackDTO, Long artistId, Long collectionId, Long trackId) {
        Track track = getTrackByArtistId_CollectionId_Id(artistId, collectionId, trackId);

        if(trackDTO.getIs_explicit() != null) track.setExplicit(trackDTO.getIs_explicit());
        if(trackDTO.getName() != null) track.setName(trackDTO.getName());
        if(trackDTO.getDuration_seconds() != null) track.setDuration(trackDTO.getDuration_seconds());
        if(trackDTO.getSpotify_streams() != null) track.setSpotifyStreams(trackDTO.getSpotify_streams());
        if(trackDTO.getYoutube_streams() != null) track.setYoutubeStreams(trackDTO.getYoutube_streams());
        if(trackDTO.getYoutube_likes() != null) track.setYoutubeLikes(trackDTO.getYoutube_likes());
        if(trackDTO.getYoutube_dislikes_estimated() != null) track.setYoutubeDislikes(trackDTO.getYoutube_dislikes_estimated());

        return trackRepository.save(track);
    }

    @Transactional
    public void deleteTrack(Long artistId, Long collectionId, Long trackId) {
        Track track = getTrackByArtistId_CollectionId_Id(artistId, collectionId, trackId);

        trackRepository.delete(track);
    }
}
