package hr.fer.zari.or.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hr.fer.zari.or.backend.model.Artist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@JsonPropertyOrder({ "@context" })
public class GetCollectionDTO {

    private Long id;
    private String name;
    private String type;
    private List<GetTrackDTO> tracks;

    public GetCollectionDTO() {
        tracks = new ArrayList<>();
    }

    @JsonProperty("@context")
    public Object getContext() {
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("type", "description");

        return context;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GetTrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<GetTrackDTO> tracks) {
        this.tracks = tracks;
    }

}
