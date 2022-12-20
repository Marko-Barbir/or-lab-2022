package hr.fer.zari.or.backend.rest;

import hr.fer.zari.or.backend.model.dto.*;
import hr.fer.zari.or.backend.service.ArtistService;
import hr.fer.zari.or.backend.service.CollectionService;
import hr.fer.zari.or.backend.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = "api/v2",
        produces = "application/json"
)
public class V2Controller {

    private final ArtistService artistService;
    private final CollectionService collectionService;
    private final TrackService trackService;

    @Autowired
    public V2Controller(ArtistService artistService, CollectionService collectionService, TrackService trackService) {
        this.artistService = artistService;
        this.collectionService = collectionService;
        this.trackService = trackService;
    }

    @GetMapping
    public void getOpenApi(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        Resource resource = new ClassPathResource("openapi.json");
        InputStream is  = resource.getInputStream();
        is.transferTo(response.getOutputStream());
    }

    @GetMapping("artists")
    public ResponseWrapper<List<GetArtistDTO>> getArtists() {
        return new ResponseWrapper<>("OK", "Fetched resource.",
                artistService.getArtists().stream().map(ModelMappers::artistToArtistDTO).collect(Collectors.toList()));
    }

    @PostMapping("artists")
    public ResponseWrapper<GetArtistDTO> addArtist(@RequestBody GetArtistDTO artistDTO) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully added resource",
                ModelMappers.artistToArtistDTO(artistService.addArtist(artistDTO)));
    }

    @PutMapping("artists/{artistId}")
    public ResponseWrapper<GetArtistDTO> updateArtist(@RequestBody GetArtistDTO artistDTO,
                                                      @PathVariable("artistId") Long artistId) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully updated resource",
                ModelMappers.artistToArtistDTO(artistService.updateArtist(artistDTO, artistId)));
    }

    @DeleteMapping("artists/{artistId}")
    public ResponseWrapper<Object> deleteArtist(@PathVariable("artistId") Long artistId) {
        artistService.deleteArtist(artistId);
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully deleted resource",
                null);
    }

    @GetMapping("artists/{id}")
    public ResponseWrapper<GetArtistDTO> getArtist(@PathVariable("id") Long id) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Fetched resource.",
                ModelMappers.artistToArtistDTO(artistService.getArtistById(id)));
    }

    @GetMapping("artists/{artistId}/collections")
    public ResponseWrapper<List<GetCollectionDTO>> getArtistCollections(@PathVariable("artistId") Long artistId) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Fetched resource.",
                collectionService.getCollectionsByArtistId(artistId).stream().map(ModelMappers::collectionToCollectionDTO).collect(Collectors.toList()));
    }

    @PostMapping("artists/{artistId}/collections")
    public ResponseWrapper<GetCollectionDTO> addArtistCollection(@PathVariable("artistId") Long artistId,
                                                                 @RequestBody GetCollectionDTO collectionDTO) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully added resource",
                ModelMappers.collectionToCollectionDTO(collectionService.addCollection(collectionDTO, artistId)));
    }

    @PutMapping("artists/{artistId}/collections/{collectionId}")
    public ResponseWrapper<GetCollectionDTO> updateArtistCollection(@PathVariable("artistId") Long artistId,
                                                                    @RequestBody GetCollectionDTO collectionDTO,
                                                                    @PathVariable Long collectionId) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully updated resource",
                ModelMappers.collectionToCollectionDTO(collectionService.updateCollection(collectionDTO, artistId, collectionId)));
    }

    @DeleteMapping("artists/{artistId}/collections/{collectionId}")
    public ResponseWrapper<Object> deleteArtistCollection(@PathVariable("artistId") Long artistId,
                                                                    @PathVariable Long collectionId) {
        collectionService.deleteCollection(artistId, collectionId);
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully deleted resource",
                null);
    }

    @GetMapping("artists/{artistId}/collections/{collectionId}")
    public ResponseWrapper<GetCollectionDTO> getArtistCollection(@PathVariable("artistId") Long artistId,
                                                                 @PathVariable("collectionId") Long collectionId) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Fetched resource",
                ModelMappers.collectionToCollectionDTO(collectionService.getCollectionByArtistIdAndId(artistId, collectionId)));
    }

    @GetMapping("artists/{artistId}/collections/{collectionId}/tracks")
    public ResponseWrapper<List<GetTrackDTO>> getArtistCollectionTracks(@PathVariable("artistId") Long artistId,
                                                                        @PathVariable("collectionId") Long collectionId) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Fetched resource",
                trackService.getTracksByArtistId_CollectionId(artistId, collectionId)
                        .stream().map(ModelMappers::trackToTrackDTO).collect(Collectors.toList()));
    }

    @PostMapping("artists/{artistId}/collections/{collectionId}/tracks")
    public ResponseWrapper<GetTrackDTO> addArtistCollectionTrack(@PathVariable("artistId") Long artistId,
                                                                       @PathVariable("collectionId") Long collectionId,
                                                                       @RequestBody GetTrackDTO trackDTO) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully added resource",
                ModelMappers.trackToTrackDTO(trackService.addTrack(trackDTO, artistId, collectionId)));
    }

    @PutMapping("artists/{artistId}/collections/{collectionId}/tracks/{trackId}")
    public ResponseWrapper<GetTrackDTO> addArtistCollectionTrack(@PathVariable("artistId") Long artistId,
                                                                 @PathVariable("collectionId") Long collectionId,
                                                                 @PathVariable("trackId") Long trackId,
                                                                 @RequestBody GetTrackDTO trackDTO) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully updated resource",
                ModelMappers.trackToTrackDTO(trackService.updateTrack(trackDTO, artistId, collectionId, trackId)));
    }

    @DeleteMapping("artists/{artistId}/collections/{collectionId}/tracks/{trackId}")
    public ResponseWrapper<GetTrackDTO> updateArtistCollectionTrack(@PathVariable("artistId") Long artistId,
                                                                 @PathVariable("collectionId") Long collectionId,
                                                                 @PathVariable("trackId") Long trackId) {
        trackService.deleteTrack(artistId, collectionId, trackId);
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Successfully deleted resource",
                null);
    }

    @GetMapping("artists/{artistId}/collections/{collectionId}/tracks/{trackId}")
    public ResponseWrapper<GetTrackDTO> getArtistCollectionTrack(@PathVariable("artistId") Long artistId,
                                                                 @PathVariable("collectionId") Long collectionId,
                                                                 @PathVariable("trackId") Long trackId) {
        return new ResponseWrapper<>(HttpStatus.OK.getReasonPhrase(), "Fetched resource",
                ModelMappers.trackToTrackDTO(trackService.getTrackByArtistId_CollectionId_Id(artistId, collectionId, trackId)));
    }
}
