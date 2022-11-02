import psycopg2
from sys import argv

conn = psycopg2.connect(dbname="orlab", user="orlab", password="orlab123",
                        host="localhost", port=49153)

with conn:
    with conn.cursor() as cur:
        cur.execute("""
                            SELECT artist.name, collection.name, collection.type, track.name, duration_seconds, explicit, spotify_streams,
                             youtube_streams, youtube_likes, youtube_dislikes_estimated, artist2.name
                            FROM
                                artist
                                LEFT JOIN artist_collection ON artist.id = artist_collection.artist_id
                                JOIN collection ON artist_collection.collection_id = collection.id
                                LEFT JOIN collection_track ON collection.id = collection_track.collection_id
                                JOIN track ON collection_track.track_id = track.id
                                LEFT JOIN artist_track on track.id = artist_track.track_id
                                JOIN artist AS artist2 ON artist_track.artist_id = artist2.id
                            ;
                            """)
        result = cur.fetchall()
        if argv[1].lower() == "csv":
            print("artist_name,collection_name,collection_type,track_name,track_duration_seconds,"
                  "is_explicit,spotify_streams,youtube_streams,youtube_likes,"
                  "youtube_dislikes_estimated,track_credited_artist")
            for entry in result:
                print(','.join(map(lambda x: str(x) if x is not None else "", entry)))
        elif argv[1].lower() == "json":
            json_dict = {}
            for entry in result:
                artist_name, collection_name, collection_type, track_name, track_duration_seconds, \
                    is_explicit, spotify_streams, youtube_streams, youtube_likes, \
                    youtube_dislikes_estimated, credited_artist = entry
                json_dict.setdefault(artist_name, {})
                json_dict[artist_name].setdefault(collection_name, {"type": collection_type})
                json_dict[artist_name][collection_name].setdefault("tracks", {})
                json_dict[artist_name][collection_name]["tracks"].setdefault(track_name, {"duration_seconds": track_duration_seconds,
                                                                           "is_explicit": is_explicit,
                                                                           "spotify_streams": spotify_streams,
                                                                           "youtube_streams": youtube_streams,
                                                                           "youtube_likes": youtube_likes,
                                                                           "youtube_dislikes_estimated": youtube_dislikes_estimated,
                                                                           "credited_artists": []})
                json_dict[artist_name][collection_name]["tracks"][track_name]["credited_artists"].append(credited_artist)

            print("[")
            comma1 = False
            for artist_name, collections in json_dict.items():
                print(",\n"*comma1 + "\t{")
                comma1 = True
                print(f'\t\t"name": "{artist_name}",\n'
                      f'\t\t"collections": [')
                comma2 = False
                for collection_name, collection in collections.items():
                    print(",\n"*comma2 + "\t\t\t{")
                    comma2 = True
                    print(f'\t\t\t\t"name":"{collection_name}",')
                    print(f'\t\t\t\t"type":"{collection["type"]}",')
                    print(f'\t\t\t\t"tracks":[')
                    comma3 = False
                    for track_name, track in collection["tracks"].items():
                        print(",\n"*comma3 + "\t\t\t\t\t{")
                        comma3 = True
                        print(f'\t\t\t\t\t\t"name":"{track_name}",')
                        print(f'\t\t\t\t\t\t"duration_seconds":{"null" if track["duration_seconds"] is None else track["duration_seconds"]},')
                        print(f'\t\t\t\t\t\t"is_explicit":{str("null" if track["is_explicit"] is None else track["is_explicit"]).lower()},')
                        print(f'\t\t\t\t\t\t"spotify_streams":{"null" if track["spotify_streams"] is None else track["spotify_streams"]},')
                        print(f'\t\t\t\t\t\t"youtube_streams":{"null" if track["youtube_streams"] is None else track["youtube_streams"]},')
                        print(f'\t\t\t\t\t\t"youtube_likes":{"null" if track["youtube_likes"] is None else track["youtube_likes"]},')
                        print(f'\t\t\t\t\t\t"youtube_dislikes_estimated":{"null" if track["youtube_dislikes_estimated"] is None else track["youtube_dislikes_estimated"]},')
                        print(f'\t\t\t\t\t\t"credited_artists":[')
                        print(',\n'.join(['\t\t\t\t\t\t\t{'
                                          '\n\t\t\t\t\t\t\t\t"name": "' + name + '"'
                                          '\n\t\t\t\t\t\t\t}' for name in track["credited_artists"]]))
                        print(f'\t\t\t\t\t\t]')
                        print("\t\t\t\t\t}", end='')
                    print("\n\t\t\t\t]")
                    print("\t\t\t}", end='')
                print("\n\t\t]")
                print("\t}", end='')
            print("\n]")

conn.close()
