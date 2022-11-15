package hr.fer.zari.or.backend.model.dto;

import hr.fer.zari.or.backend.model.Artist;
import hr.fer.zari.or.backend.model.Collection;
import hr.fer.zari.or.backend.model.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModelMappers {
    public static List<GetArtistDTO> artistListToDTOListFiltered(List<Artist> artists, List<String> filterFields, String keyword) {
        keyword = keyword.toLowerCase(Locale.ROOT);
        List<GetArtistDTO> result = new ArrayList<>();

        for (Artist artist : artists) {
            GetArtistDTO artistDTO = new GetArtistDTO();
            boolean addArtist = !filterFields.contains("artist_name") || artist.getName().toLowerCase(Locale.ROOT).contains(keyword);
            for (Collection collection : artist.getCollections()) {
                GetCollectionDTO collectionDTO = new GetCollectionDTO();
                boolean addCollection = true;
                if (filterFields.contains("collection_name") &&
                        !collection.getName().toLowerCase(Locale.ROOT).contains(keyword)) addCollection = false;
                else if (filterFields.contains("collection_type") &&
                        !collection.getType().toLowerCase(Locale.ROOT).contains(keyword)) addCollection = false;
                for (Track track : collection.getTracks()) {
                    boolean addTrack = !(filterFields.contains("credited_artists"));
                    GetTrackDTO trackDTO = new GetTrackDTO();
                    for (Artist creditedArtist : track.getArtists()) {
                        trackDTO.getCredited_artists().add(new GetTrackDTO.CreditedArtist(creditedArtist.getName()));
                        if (creditedArtist.getName().toLowerCase(Locale.ROOT).contains(keyword)) {
                            addTrack = true;
                        }
                    }
                    if (filterFields.contains("track_name") &&
                            !track.getName().toLowerCase(Locale.ROOT).contains(keyword)) addTrack=false;
                    else if (filterFields.contains("duration_seconds") &&
                            !String.valueOf(track.getDuration()).toLowerCase(Locale.ROOT).contains(keyword)) addTrack=false;
                    else if (filterFields.contains("explicit") &&
                            !String.valueOf(track.isExplicit()).toLowerCase(Locale.ROOT).contains(keyword)) addTrack = false;
                    else if (filterFields.contains("spotify_streams") &&
                            !String.valueOf(track.getSpotifyStreams()).toLowerCase(Locale.ROOT).contains(keyword)) addTrack = false;
                    else if (filterFields.contains("youtube_streams") &&
                            !String.valueOf(track.getYoutubeStreams()).toLowerCase(Locale.ROOT).contains(keyword)) addTrack = false;
                    else if (filterFields.contains("youtube_likes") &&
                            !String.valueOf(track.getYoutubeLikes()).toLowerCase(Locale.ROOT).contains(keyword)) addTrack = false;
                    else if (filterFields.contains("youtube_dislikes_estimated") &&
                            !String.valueOf(track.getYoutubeDislikes()).toLowerCase(Locale.ROOT).contains(keyword)) addTrack = false;

                    if(addTrack) {
                        trackDTO.setName(track.getName());
                        trackDTO.setIs_explicit(track.isExplicit());
                        trackDTO.setDuration_seconds(track.getDuration());
                        trackDTO.setSpotify_streams(track.getSpotifyStreams());
                        trackDTO.setYoutube_likes(track.getYoutubeLikes());
                        trackDTO.setYoutube_streams(track.getYoutubeStreams());
                        trackDTO.setYoutube_dislikes_estimated(track.getYoutubeDislikes());
                        collectionDTO.getTracks().add(trackDTO);
                    }
                }
                if(addCollection && !collectionDTO.getTracks().isEmpty()) {
                    collectionDTO.setName(collection.getName());
                    collectionDTO.setType(collection.getType());
                    artistDTO.getCollections().add(collectionDTO);
                }
            }
            if (addArtist && !artistDTO.getCollections().isEmpty()) {
                artistDTO.setName(artist.getName());
                result.add(artistDTO);
            }
        }

        return result;
    }
}
