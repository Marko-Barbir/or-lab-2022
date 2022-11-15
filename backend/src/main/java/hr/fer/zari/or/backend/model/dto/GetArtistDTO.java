package hr.fer.zari.or.backend.model.dto;

import java.util.ArrayList;
import java.util.List;

public class GetArtistDTO {
    private String name;
    private List<GetCollectionDTO> collections;

    public GetArtistDTO() {
        this.collections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GetCollectionDTO> getCollections() {
        return collections;
    }

    public void setCollections(List<GetCollectionDTO> collections) {
        this.collections = collections;
    }
}
