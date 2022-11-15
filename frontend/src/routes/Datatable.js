import { DataGrid } from '@mui/x-data-grid';
import {
    Box,
    Button,
    Checkbox,
    Container, FormControl, InputLabel,
    ListItemText,
    MenuItem,
    OutlinedInput,
    Select,
    Stack,
    TextField
} from "@mui/material";
import {useEffect, useState} from "react";
import $ from 'jquery'

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

const fields = [
    ["artist_name", "Artist name"],
    ["collection_name", "Collection name"],
    ["collection_type", "Collection type"],
    ["credited_artists", "Credited artists"],
    ["track_name", "Track name"],
    ["duration_seconds", "Track duration"],
    ["explicit", "Explicit"],
    ["spotify_streams", "Spotify streams"],
    ["youtube_streams", "YouTube streams"],
    ["youtube_likes", "YouTube likes"],
    ["youtube_dislikes_estimated", "YouTube dislikes"],
]

function Datatable() {
    const [trackList, setTrackList] = useState([]);
    const [filterFields, setFilterFields] = useState([]);
    const [filterKeyword, setFilterKeyword] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            $.getJSON("http://localhost:8080/api/v1/artists?fields="+filterFields.join(",")
                +"&keyword=" + filterKeyword, function (data) {
                setTrackList(data);
            });
        };
        fetchData();
    }, [filterKeyword, filterFields]);

    const onSelectChange = (e) => {
        setFilterFields(
            // On autofill we get a stringified value.
            typeof e.target.value === 'string' ? e.target.value.split(',') : e.target.value,
        );
    };

    const onKeywordChange = (e) => {
        setFilterKeyword(e.target.value)
    };

    const columns = [
        {field: 'artist_name', headerName: 'Artist', width: 200},
        {field: 'collection_name', headerName: 'Collection', width: 200},
        {field: 'collection_type', headerName: 'Type', width: 75},
        {field: 'track_name', headerName: 'Track', width: 150},
        {field: 'duration', headerName: 'Duration(s)', width: 90},
        {field: 'spotify_streams', headerName: 'Spotify Streams', width: 120},
        {field: 'yt_streams', headerName: 'YT streams', width: 100},
        {field: 'yt_likes', headerName: 'YT likes', width: 70},
        {field: 'yt_dislikes', headerName: 'YT dislikes', width: 80},
        {field: 'explicit', headerName: 'Explicit', width: 60},
        {field: 'credited_artists', headerName: 'Credited Artists', width: 300},
    ]

    let i = 1;
    const rows = [];
    for(let artist of trackList) {
        for (let collection of artist.collections) {
            for (let track of collection.tracks) {
                rows.push({
                    id:i++,
                    artist_name:artist.name,
                    collection_name:collection.name,
                    collection_type:collection.type,
                    track_name:track.name,
                    duration:track.duration_seconds,
                    spotify_streams:track.spotify_streams,
                    yt_streams:track.youtube_streams,
                    yt_likes:track.youtube_likes,
                    yt_dislikes:track.youtube_dislikes_estimated,
                    explicit:track.is_explicit,
                    credited_artists:track.credited_artists.map(artist=>artist.name).join(",")
                })
            }
        }
    }
    console.log(filterFields)

    return (
        <Container sx={{height:"75vh", padding:0,width:"100vw"}}>
            <Stack sx={{marginTop:1}} direction="row" spacing={2}>
                <FormControl sx={{ m: 1, width: 300 }}>
                    <InputLabel id="checkbox-label">Filter Fields</InputLabel>
                <Select
                    labelId="checkbox-label"
                    id="checkbox"
                    multiple
                    onChange={onSelectChange}
                    value={filterFields}
                    renderValue={(fields) => fields.join(',')}
                    input={<OutlinedInput label="Filter fields" />}
                    MenuProps={MenuProps}
                >
                    {fields.map((field) => (
                        <MenuItem key={field[0]} value={field[0]}>
                            <Checkbox checked={filterFields.indexOf(field[0]) > -1} />
                            <ListItemText primary={field[0]} />
                        </MenuItem>
                    ))}
                </Select>
                </FormControl>
                <TextField label="Filter Keyword" onChange={onKeywordChange}/>
                <Button onClick={()=>{
                    window.location="http://localhost:8080/api/v1/artists/download?format=json&fields="+filterFields.join(",")
                        +"&keyword=" + filterKeyword;
                }}>Download JSON</Button>
                <Button onClick={()=>{
                    window.location="http://localhost:8080/api/v1/artists/download?format=csv&fields="+filterFields.join(",")
                    +"&keyword=" + filterKeyword;
                }}>Download CSV</Button>
            </Stack>
            <h1>Music Streaming Data</h1>
            <Box sx={{
                flexGrow: 1,
                height:"100%"
            }}>
            <DataGrid columns={columns}
                rows={rows}/>
            </Box>
        </Container>
    )
}

export default Datatable;