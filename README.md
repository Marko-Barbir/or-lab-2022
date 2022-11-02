# Laboratory exercise for Open Computing course - 2022./23.  

This laboratory exercise consists of an open data set containing limited information relating to music streaming on 2 websites: Spotify and YouTube.

## General information
Current version: 1.0  
Language: English  
Author: Marko Barbir  
Contact e-mail: [marko.barbir@fer.hr](mailto:marko.barbir@fer.hr)  
License: [Creative Commons Attribution Share Alike 4.0](https://creativecommons.org/licenses/by-sa/4.0/)  
Publication date: 2022.11.02.  
Update frequency: Never  

## Data information

### General data information

Data was collected on the day of 2022.11.01., by manual collection of publicly available information on the
web-sites: [Spotify](https://www.spotify.com/) and [YouTube](https://www.youtube.com/).
Notes:
- YouTube like and dislike data was rounded on videos where said count surpassed 1000
- YouTube dislike data was estimate using the [Return YouTube dislike](https://www.returnyoutubedislike.com/) addon
- in case of multiple official version of one song on YouTube, all such versions had their
stream/like/dislike summed up

### CSV column metadata

| column name                | datatype | explanation                                                                                        | 
|----------------------------|----------|----------------------------------------------------------------------------------------------------|
| artist_name                | string   | name of the artist whose collection this row refers to                                             |
| collection_name            | string   | name of the collection in which this row's track is contained                                      |
| collection_type            | string   | type of the collection in which this row's track is contained<br/>can be album, single or EP       |
| track_name                 | string   | name of the track which this row refers to                                                         |
| track_duration             | integer  | duration of track in seconds (null if unknown)                                                     |
| is_explicit                | boolean  | true if track contains foul/explicit language, false if it does not, null if unknown               |
| spotify_streams            | integer  | how many times this track had been played on spotify                                               |
| youtube_streams            | integer  | how many times this track had been played on YouTube                                               |
| youtube_likes              | integer  | how many times this track had been liked on YouTube                                                |
| youtube_dislikes_estimated | integer  | an estimate of how many times this track had been disliked on YouTube                              |
| track_credited_artist      | string   | name of artist credited for this row's track<br/>can be multiple thus introducing row duplication  |

### JSON key metadata

| object        | key                        | datatype                       | explanation                                                                          |
|---------------|----------------------------|--------------------------------|--------------------------------------------------------------------------------------|
| artist        | name                       | string                         | name of artist whose collections are contained in this JSON file                     |
| artist        | collections                | list of objects(collection)    | list of collections atributed to this artist                                         |
| collection    | name                       | string                         | name of collection                                                                   |
| collection    | type                       | string                         | type of collection, can be album, single or EP                                       |
| collection    | tracks                     | list of objects(track)         | list of tracks contained in this collection                                          |
| track         | name                       | string                         | name of music track                                                                  |
| track         | duration_seconds           | integer                        | duration of track in seconds, null if unknown                                        |
| track         | is_explicit                | boolean                        | true if track contains foul/explicit language, false if it does not, null if unknown |
| track         | spotify_streams            | integer                        | how many times this track had been played on spotify                                 |
| track         | youtube_streams            | integer                        | how many times this track had been played on YouTube                                 |
| track         | youtube_likes              | integer                        | how many times this track had been liked on YouTube                                  |
| track         | youtube_dislikes_estimated | integer                        | an estimate of how many times this track had been disliked on YouTube                |
| track         | credited_artists           | list of objects(artist_credit) | list of artists which are have credits on Spotify for this track                     |
| artist_credit | name                       | string                         | name of artist credited on relating track                                            |
