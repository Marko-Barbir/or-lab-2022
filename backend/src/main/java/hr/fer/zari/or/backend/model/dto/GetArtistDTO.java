package hr.fer.zari.or.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hr.fer.zari.or.backend.model.Artist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({ "@context", "@type" })
public class GetArtistDTO {

    private Long id;
    private String name;
    private List<GetCollectionDTO> collections;

    public GetArtistDTO() {
        this.collections = new ArrayList<>();
    }

    @JsonProperty("@context")
    public Object getContext() {
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("@vocab", "https://schema.org/");
        context.put("id", "identifier");
        context.put("collections", "album");

        return context;
    }

    @JsonProperty("@type")
    public String getType() {
        return "MusicGroup";
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

    public List<GetCollectionDTO> getCollections() {
        return collections;
    }

    public void setCollections(List<GetCollectionDTO> collections) {
        this.collections = collections;
    }
}
