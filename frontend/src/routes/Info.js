import {Box, Button, Container, Stack} from "@mui/material";

function Info() {
    return (
        <Container>
            <h1>
                Laboratory exercise for Open Computing course - 2022./23.
            </h1>
            <p>
                This laboratory exercise consists of an open data set containing limited information relating to music streaming on 2 websites: Spotify and YouTube.
            </p>
            <h2>
                General information
            </h2>
            <p>
                Current version: 1.0<br/>
                Language: English<br/>
                Author: Marko Barbir<br/>
                Contact e-mail: <a href="mailto:marko.barbir@fer.hr">marko.barbir@fer.hr</a><br/>
                License: <a href="https://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution Share Alike 4.0</a><br/>
                Publication date: 2022.11.02.<br/>
                Update frequency: Never<br/>
            </p>
            <h2>
                Data information
            </h2>
            <Stack direction="row" spacing={2}>
                <Button onClick={()=>{
                    window.location="http://localhost:8080/api/v1/artists/download?format=json"
                }}>Download JSON</Button>
                <Button onClick={()=>{
                    window.location="http://localhost:8080/api/v1/artists/download?format=csv"
                }}>Download CSV</Button>
                <Button href="/data">Open data in table</Button>
            </Stack>
            <h3>
                General data information
            </h3>
            <p>
                Data is stored in PostgreSQL 14.4 database hosted on a WSL 2 Docker container available on docker hub.

                <br/>In the repository a script for exporting the database data to CSV and JSON is also provided (although currently it has to be manually updated with correct database information instead of using command line arguments). The only command line argument, which is also required is the format which to export to, can be CSV or JSON (case-insensitive).

                <br/>A database dump with all the information is also provided in dump.sql file.

                <br/>Data was collected on the day of 2022.11.01., by manual collection of publicly available information on the web-sites: Spotify and YouTube. Notes:
                <ul>
                    <li>YouTube like and dislike data was rounded on videos where said count surpassed 1000</li>
                    <li>YouTube dislike data was estimate using the Return YouTube dislike addon</li>
                    <li>in case of multiple official version of one song on YouTube, all such versions had their stream/like/dislike summed up</li>
                </ul>
            </p>
            <br/>
            <h2>
                CSV column metadata
            </h2>
            <table>
                <thead>
                <tr>
                    <td>column name</td>
                    <td>datatype</td>
                    <td>explanation</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>artist_name</td>
                    <td>string</td>
                    <td>name of the artist whose collection this row refers to</td>
                </tr>
                <tr>
                    <td>collection_name</td>
                    <td>string</td>
                    <td>name of the collection in which this row's track is contained</td>
                </tr>
                <tr>
                    <td>collection_type</td>
                    <td>string</td>
                    <td>type of the collection in which this row's track is contained&lt;br/&gt;can be album, single or EP</td>
                </tr>
                <tr>
                    <td>track_name</td>
                    <td>string</td>
                    <td>name of the track which this row refers to</td>
                </tr>
                <tr>
                    <td>track_duration</td>
                    <td>integer</td>
                    <td>duration of track in seconds (null if unknown)</td>
                </tr>
                <tr>
                    <td>is_explicit</td>
                    <td>boolean</td>
                    <td>true if track contains foul/explicit language, false if it does not, null if unknown</td>
                </tr>
                <tr>
                    <td>spotify_streams</td>
                    <td>integer</td>
                    <td>how many times this track had been played on spotify</td>
                </tr>
                <tr>
                    <td>youtube_streams</td>
                    <td>integer</td>
                    <td>how many times this track had been played on YouTube</td>
                </tr>
                <tr>
                    <td>youtube_likes</td>
                    <td>integer</td>
                    <td>how many times this track had been liked on YouTube</td>
                </tr>
                <tr>
                    <td>youtube_dislikes_estimated</td>
                    <td>integer</td>
                    <td>an estimate of how many times this track had been disliked on YouTube</td>
                </tr>
                <tr>
                    <td>track_credited_artist</td>
                    <td>string</td>
                    <td>name of artist credited for this row's track&lt;br/&gt;can be multiple thus introducing row duplication</td>
                </tr>
                </tbody>
            </table>
            <br/>
            <h2>JSON Key Metadata</h2>
            <table>
                <thead>
                <tr>
                    <td>object</td>
                    <td>key</td>
                    <td>datatype</td>
                    <td>explanation</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>artist</td>
                    <td>name</td>
                    <td>string</td>
                    <td>name of artist whose collections are contained in this JSON file</td>
                </tr>
                <tr>
                    <td>artist</td>
                    <td>collections</td>
                    <td>list of objects(collection)</td>
                    <td>list of collections atributed to this artist</td>
                </tr>
                <tr>
                    <td>collection</td>
                    <td>name</td>
                    <td>string</td>
                    <td>name of collection</td>
                </tr>
                <tr>
                    <td>collection</td>
                    <td>type</td>
                    <td>string</td>
                    <td>type of collection, can be album, single or EP</td>
                </tr>
                <tr>
                    <td>collection</td>
                    <td>tracks</td>
                    <td>list of objects(track)</td>
                    <td>list of tracks contained in this collection</td>
                </tr>
                <tr>
                    <td>track</td>
                    <td>name</td>
                    <td>string</td>
                    <td>name of music track</td>
                </tr>
                <tr>
                    <td>track</td>
                    <td>duration_seconds</td>
                    <td>integer</td>
                    <td>duration of track in seconds, null if unknown</td>
                </tr>
                <tr>
                    <td>track</td>
                    <td>is_explicit</td>
                    <td>boolean</td>
                    <td>true if track contains foul/explicit language, false if it does not, null if unknown</td>
                </tr>
                <tr>
                    <td>track</td>
                    <td>spotify_streams</td>
                    <td>integer</td>
                    <td>how many times this track had been played on spotify</td>
                </tr>
                <tr>
                    <td>track</td>
                    <td>youtube_streams</td>
                    <td>integer</td>
                    <td>how many times this track had been played on YouTube</td>
                </tr>
                <tr>
                    <td>track</td>
                    <td>youtube_likes</td>
                    <td>integer</td>
                    <td>how many times this track had been liked on YouTube</td>
                </tr>
                <tr>
                    <td>track</td>
                    <td>youtube_dislikes_estimated</td>
                    <td>integer</td>
                    <td>an estimate of how many times this track had been disliked on YouTube</td>
                </tr>
                <tr>
                    <td>track</td>
                    <td>credited_artists</td>
                    <td>list of objects(artist_credit)</td>
                    <td>list of artists which are have credits on Spotify for this track</td>
                </tr>
                <tr>
                    <td>artist_credit</td>
                    <td>name</td>
                    <td>string</td>
                    <td>name of artist credited on relating track</td>
                </tr>
                </tbody>
            </table>
            <br/>
            <Box sx={{
                margin:"auto",
                width:300,
                height:60
            }}>
                <a style={{margin: "auto"}} href="/data">Click here for the data!</a>
            </Box>
        </Container>
    )
}

export default Info;