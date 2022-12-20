package hr.fer.zari.or.backend.model.dto;

import hr.fer.zari.or.backend.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class GetCollectionDTO {

    private Long id;
    private String name;
    private String type;
    private List<GetTrackDTO> tracks;

    public GetCollectionDTO() {
        tracks = new ArrayList<>();
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
