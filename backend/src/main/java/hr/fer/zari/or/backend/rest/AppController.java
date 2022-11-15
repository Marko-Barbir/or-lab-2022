package hr.fer.zari.or.backend.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.zari.or.backend.model.dto.GetArtistDTO;
import hr.fer.zari.or.backend.model.dto.GetCollectionDTO;
import hr.fer.zari.or.backend.model.dto.GetTrackDTO;
import hr.fer.zari.or.backend.model.dto.ModelMappers;
import hr.fer.zari.or.backend.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AppController {

    private final ObjectMapper objectMapper;
    private final ArtistService artistService;

    @Autowired
    public AppController(ObjectMapper objectMapper, ArtistService artistService) {
        this.objectMapper = objectMapper;
        this.artistService = artistService;
    }

    @GetMapping("artists")
    public List<GetArtistDTO> getArtists(@RequestParam(value = "fields", defaultValue = "") List<String> filterFields,
                                         @RequestParam(value = "keyword", defaultValue = "") String filterKeyword,
                                         HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return ModelMappers.artistListToDTOListFiltered(artistService.getArtists()
                , filterFields, filterKeyword);
    }

    @GetMapping("artists/download")
    public void downloadArtists(@RequestParam(value = "fields", defaultValue = "") List<String> filterFields,
                                @RequestParam(value = "keyword", defaultValue = "") String filterKeyword,
                                @RequestParam(defaultValue = "json") String format,
                                HttpServletResponse response) {
        PrintWriter pw;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            return;
        }
        List<GetArtistDTO> outList = ModelMappers.artistListToDTOListFiltered(artistService.getArtists()
                , filterFields, filterKeyword);
        if (format.equalsIgnoreCase("json")) {
            try {
                pw.append(objectMapper.writeValueAsString(outList));
            } catch (JsonProcessingException e){
                return;
            }
            response.setContentType("application/json");
            response.addHeader("Content-Disposition", "attachment; filename=\"music_streaming_metrics.json\"");
        } else if (format.equalsIgnoreCase("csv")) {
            pw.println("artist_name,collection_name,collection_type,track_name,track_duration_seconds,is_explicit," +
                    "spotify_streams,youtube_streams,youtube_likes,youtube_dislikes_estimated,track_credited_artist");
            for (GetArtistDTO artistDTO : outList) {
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
            response.setContentType("text/csv");
            response.addHeader("Content-Disposition", "attachment; filename=\"music_streaming_metrics.csv\"");
        }
        pw.flush();
    }
}
