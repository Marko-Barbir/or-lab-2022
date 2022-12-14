package hr.fer.zari.or.backend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "duration_seconds")
    private int duration;
    @Column(name = "explicit")
    private boolean explicit;
    @Column(name = "spotify_streams")
    private Integer spotifyStreams;
    @Column(name = "youtube_streams")
    private Integer youtubeStreams;
    @Column(name = "youtube_likes")
    private Integer youtubeLikes;
    @Column(name = "youtube_dislikes_estimated")
    private Integer youtubeDislikes;

    @ManyToMany
    @JoinTable(
            name = "artist_track",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> artists;
    @ManyToOne
    @JoinTable(
            name = "collection_track",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "collection_id")
    )
    private Collection collection;

    public Track() {
        this.artists = new ArrayList<>();
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public Integer getSpotifyStreams() {
        return spotifyStreams;
    }

    public void setSpotifyStreams(Integer spotifyStreams) {
        this.spotifyStreams = spotifyStreams;
    }

    public Integer getYoutubeStreams() {
        return youtubeStreams;
    }

    public void setYoutubeStreams(Integer youtubeStreams) {
        this.youtubeStreams = youtubeStreams;
    }

    public Integer getYoutubeLikes() {
        return youtubeLikes;
    }

    public void setYoutubeLikes(Integer youtubeLikes) {
        this.youtubeLikes = youtubeLikes;
    }

    public Integer getYoutubeDislikes() {
        return youtubeDislikes;
    }

    public void setYoutubeDislikes(Integer youtubeDislikes) {
        this.youtubeDislikes = youtubeDislikes;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}
