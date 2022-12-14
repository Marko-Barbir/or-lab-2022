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
public class GetTrackDTO {
    private Long id;
    private String name;
    private Integer duration_seconds;
    private Boolean is_explicit;
    private Integer spotify_streams;
    private Integer youtube_streams;
    private Integer youtube_likes;
    private Integer youtube_dislikes_estimated;
    private List<CreditedArtist> credited_artists;

    public GetTrackDTO() {
        this.credited_artists = new ArrayList<>();
    }

    @JsonProperty("@context")
    public Object getContext() {
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("duration_seconds", "duration");
        context.put("is_explicit", "isFamilyFriendly");
        Stream.of("spotify_streams", "youtube_streams", "youtube_likes", "youtube_dislikes_estimated")
                .forEachOrdered(k -> context.put(k, "interactionStatistic"));
        context.put("credited_artists", "byArtist");

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

    public Integer getDuration_seconds() {
        return duration_seconds;
    }

    public void setDuration_seconds(Integer duration_seconds) {
        this.duration_seconds = duration_seconds;
    }

    public Boolean getIs_explicit() {
        return is_explicit;
    }

    public void setIs_explicit(Boolean is_explicit) {
        this.is_explicit = is_explicit;
    }

    public Integer getSpotify_streams() {
        return spotify_streams;
    }

    public void setSpotify_streams(Integer spotify_streams) {
        this.spotify_streams = spotify_streams;
    }

    public Integer getYoutube_streams() {
        return youtube_streams;
    }

    public void setYoutube_streams(Integer youtube_streams) {
        this.youtube_streams = youtube_streams;
    }

    public Integer getYoutube_likes() {
        return youtube_likes;
    }

    public void setYoutube_likes(Integer youtube_likes) {
        this.youtube_likes = youtube_likes;
    }

    public Integer getYoutube_dislikes_estimated() {
        return youtube_dislikes_estimated;
    }

    public void setYoutube_dislikes_estimated(Integer youtube_dislikes_estimated) {
        this.youtube_dislikes_estimated = youtube_dislikes_estimated;
    }

    public List<CreditedArtist> getCredited_artists() {
        return credited_artists;
    }

    public void setCredited_artists(List<CreditedArtist> credited_artists) {
        this.credited_artists = credited_artists;
    }

    public static class CreditedArtist{

        private Long id;
        private String name;

        public CreditedArtist(Artist artist) {
            this.id = artist.getId();
            this.name = artist.getName();
        }

        public CreditedArtist(String name) {
            this.name = name;
        }

        public CreditedArtist() {
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
    }
}