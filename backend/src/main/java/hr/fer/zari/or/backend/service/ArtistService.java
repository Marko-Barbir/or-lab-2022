package hr.fer.zari.or.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.zari.or.backend.model.Artist;
import hr.fer.zari.or.backend.model.dto.GetArtistDTO;
import hr.fer.zari.or.backend.model.dto.GetCollectionDTO;
import hr.fer.zari.or.backend.model.dto.GetTrackDTO;
import hr.fer.zari.or.backend.model.dto.ModelMappers;
import hr.fer.zari.or.backend.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Value("${frontend.public.location}")
    private String frontendPublicLocation;

    private final ArtistRepository artistRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, ObjectMapper objectMapper) {
        this.artistRepository = artistRepository;
        this.objectMapper = objectMapper;
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

    @Transactional(readOnly = true)
    public void refreshLocalDataset() throws IOException {
        Path jsonPath = Path.of(frontendPublicLocation, "music_streaming_metrics.json");
        Path csvPath = Path.of(frontendPublicLocation, "music_streaming_metrics.csv");

        List<GetArtistDTO> artists = getArtists().stream().map(ModelMappers::artistToArtistDTO).toList();

        Files.writeString(jsonPath, objectMapper.writeValueAsString(artists) ,StandardCharsets.UTF_8);

        PrintWriter pw = new PrintWriter(Files.newBufferedWriter(csvPath));
        printArtistsToCSV(pw, artists);
        pw.flush();
        pw.close();
    }

    public static void printArtistsToCSV(PrintWriter pw, List<GetArtistDTO> artists) {
        pw.println("artist_name,collection_name,collection_type,track_name,track_duration_seconds,is_explicit," +
                "spotify_streams,youtube_streams,youtube_likes,youtube_dislikes_estimated,track_credited_artist");
        for (GetArtistDTO artistDTO : artists) {
            for (GetCollectionDTO collectionDTO : artistDTO.getCollections()) {
                for (GetTrackDTO trackDTO : collectionDTO.getTracks()) {
                    for (GetTrackDTO.CreditedArtist creditedArtist : trackDTO.getCredited_artists()) {
                        pw.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                                artistDTO.getName(), collectionDTO.getName(), collectionDTO.getType(),
                                trackDTO.getName(), trackDTO.getDuration_seconds(), trackDTO.getIs_explicit(),
                                trackDTO.getSpotify_streams(), trackDTO.getYoutube_streams(), trackDTO.getYoutube_likes(),
                                trackDTO.getYoutube_dislikes_estimated(), creditedArtist.getName()));
                    }
                }
            }
        }
    }
}
