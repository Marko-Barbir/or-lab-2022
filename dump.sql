--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

DROP DATABASE orlab;




--
-- Drop roles
--

DROP ROLE orlab;
DROP ROLE postgres;


--
-- Roles
--

CREATE ROLE orlab;
ALTER ROLE orlab WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:1TOgIlDap+EIjVO1idOVnw==$4KKYOiFOB4Mi2Gz5UMwGAKTKXEzMUgJ9lYLmjWHG6II=:DhuO/SdDZ9pqotUwgiSsIcNth6BUcT53I7gpbvgi9oY=';
CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:R53CkksnLsVYExvI4FUX3g==$+wF+3wDplmw2dWi25ge2tF+sPXeJpQ4wTJXlUq/MwpA=:kiogMsd77uDPxTcNThnMiBDx+DICjBtMPU9F6S8oVwA=';






--
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4 (Debian 14.4-1.pgdg110+1)
-- Dumped by pg_dump version 14.4 (Debian 14.4-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "orlab" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4 (Debian 14.4-1.pgdg110+1)
-- Dumped by pg_dump version 14.4 (Debian 14.4-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: orlab; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE orlab WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE orlab OWNER TO postgres;

\connect orlab

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: orlab; Type: SCHEMA; Schema: -; Owner: orlab
--

CREATE SCHEMA orlab;


ALTER SCHEMA orlab OWNER TO orlab;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: artist; Type: TABLE; Schema: orlab; Owner: orlab
--

CREATE TABLE orlab.artist (
    id integer NOT NULL,
    name character varying(75) NOT NULL
);


ALTER TABLE orlab.artist OWNER TO orlab;

--
-- Name: artist_collection; Type: TABLE; Schema: orlab; Owner: orlab
--

CREATE TABLE orlab.artist_collection (
    artist_id integer NOT NULL,
    collection_id integer NOT NULL
);


ALTER TABLE orlab.artist_collection OWNER TO orlab;

--
-- Name: artist_id_seq; Type: SEQUENCE; Schema: orlab; Owner: orlab
--

ALTER TABLE orlab.artist ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME orlab.artist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: artist_track; Type: TABLE; Schema: orlab; Owner: orlab
--

CREATE TABLE orlab.artist_track (
    artist_id integer NOT NULL,
    track_id integer NOT NULL
);


ALTER TABLE orlab.artist_track OWNER TO orlab;

--
-- Name: collection; Type: TABLE; Schema: orlab; Owner: orlab
--

CREATE TABLE orlab.collection (
    id integer NOT NULL,
    name character varying(75) NOT NULL,
    type character varying(25),
    CONSTRAINT check_type_valid CHECK (((type)::text = ANY (ARRAY[('album'::character varying)::text, ('single'::character varying)::text, ('EP'::character varying)::text])))
);


ALTER TABLE orlab.collection OWNER TO orlab;

--
-- Name: collection_id_seq; Type: SEQUENCE; Schema: orlab; Owner: orlab
--

ALTER TABLE orlab.collection ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME orlab.collection_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: collection_track; Type: TABLE; Schema: orlab; Owner: orlab
--

CREATE TABLE orlab.collection_track (
    collection_id integer NOT NULL,
    track_id integer NOT NULL
);


ALTER TABLE orlab.collection_track OWNER TO orlab;

--
-- Name: track; Type: TABLE; Schema: orlab; Owner: orlab
--

CREATE TABLE orlab.track (
    id integer NOT NULL,
    name character varying(75) NOT NULL,
    duration_seconds integer,
    explicit boolean,
    spotify_streams integer,
    youtube_streams integer,
    youtube_likes integer,
    youtube_dislikes_estimated integer
);


ALTER TABLE orlab.track OWNER TO orlab;

--
-- Name: track_id_seq; Type: SEQUENCE; Schema: orlab; Owner: orlab
--

ALTER TABLE orlab.track ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME orlab.track_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: artist; Type: TABLE DATA; Schema: orlab; Owner: orlab
--

COPY orlab.artist (id, name) FROM stdin;
1	Kendrick Lamar
2	Blxst
3	Amanda Reifer
4	Sampha
5	Taylour Paige
6	Summer Walker
7	Ghostface Killah
8	Kodak Black
9	Baby Keem
10	Sam Dew
11	Tanna Leone
12	Beth Gibbons
13	SZA
14	ScHoolboy Q
15	2 Chainz
16	Saudi
17	Swae Lee
18	Khalid
19	Vince Staples
20	Yugen Blakrok
21	Jorja Smith
22	SOB X RBE
23	Ab-Soul
24	Anderson .Paak
25	James Blake
26	Jay Rock
27	Future
28	Zacari
29	Babes Wodumo
30	Mozzy
31	Sjava
32	REASON
33	Travis Scott
34	The Weeknd
35	U2
36	Rihanna
37	George Clinton
38	Thundercat
39	Bilal
40	Anna Wise
41	Snoop Dogg
42	James Fauntleroy
43	Ronald Isley
44	Rapsody
45	Jay Rock
46	Drake
47	MC Eith
48	Dr. Dre
49	Mary J. Blige
50	Jay-Z
51	Emeli Sandé
52	Kenshi Yonezu
53	Kishi Bashi
54	Cleopatrick
55	Dead Poet Society
56	Stephen
57	Survive Said The Prophet
58	Crywolf
59	Denzel Curry
60	Eden Project
\.


--
-- Data for Name: artist_collection; Type: TABLE DATA; Schema: orlab; Owner: orlab
--

COPY orlab.artist_collection (artist_id, collection_id) FROM stdin;
1	1
52	7
53	8
54	10
55	11
56	12
57	13
58	14
59	15
60	16
\.


--
-- Data for Name: artist_track; Type: TABLE DATA; Schema: orlab; Owner: orlab
--

COPY orlab.artist_track (artist_id, track_id) FROM stdin;
1	1
1	2
1	3
1	4
1	5
1	6
1	7
1	8
1	9
1	10
1	11
1	12
1	13
1	14
1	15
1	16
1	17
1	18
1	19
2	4
3	4
4	5
5	8
6	9
7	9
8	12
9	14
10	14
11	16
12	17
52	20
53	21
54	22
55	23
56	24
57	25
58	26
59	27
60	28
60	29
60	30
60	31
60	32
\.


--
-- Data for Name: collection; Type: TABLE DATA; Schema: orlab; Owner: orlab
--

COPY orlab.collection (id, name, type) FROM stdin;
1	Mr. Morale & The Big Steppers	album
2	Black Panther The Album Music From And Inspired By	album
3	DAMN.	album
4	untitled unmastered.	album
5	To Pimp A Butterfly	album
6	good kid, m.A.A.d city	album
7	KICK BACK	single
8	For Every Voice That Never Sang	single
10	hometown	single
11	Lo Air	single
12	Sincerely	single
13	found & lost	single
14	Quantum Immortality (Radio Edit)	single
15	Walkin	single
16	Bipolar Paradise	EP
\.


--
-- Data for Name: collection_track; Type: TABLE DATA; Schema: orlab; Owner: orlab
--

COPY orlab.collection_track (collection_id, track_id) FROM stdin;
7	20
1	1
1	2
1	3
1	4
1	5
1	6
1	7
1	8
1	9
1	10
1	11
1	12
1	13
1	14
1	15
1	16
1	17
1	18
1	19
8	21
10	22
11	23
12	24
13	25
14	26
15	27
16	28
16	29
16	30
16	31
16	32
\.


--
-- Data for Name: track; Type: TABLE DATA; Schema: orlab; Owner: orlab
--

COPY orlab.track (id, name, duration_seconds, explicit, spotify_streams, youtube_streams, youtube_likes, youtube_dislikes_estimated) FROM stdin;
26	Quantum Immortality (Radio Edit)	231	f	232507	\N	\N	\N
1	United In Grief	255	t	84118869	9434916	268000	2600
2	N95	195	t	181953044	56316904	1453000	20300
3	Worldwide Steppers	203	t	45434119	3920385	79000	1200
4	Die Hard	239	t	148720056	10932146	182000	1700
5	Father Time	222	t	79671528	6360256	141000	735
7	Rich Spirit	202	t	95455147	8407267	153000	1300
6	Rich - Interlude	103	t	31156192	2054394	33000	279
8	We Cry Together	341	t	49379689	7613336	226000	4100
9	Purple Hearts	329	t	52351109	5416242	122000	916
10	Count Me Out	283	t	67302831	10774221	193000	1200
11	Crown	264	t	30684782	2582050	56000	544
12	Silent Hill	220	t	83381223	19935539	354000	9400
13	Savior - Interlude	152	t	27141800	1958553	42000	255
14	Savior	224	t	56651442	5733898	118000	1100
15	Auntie Diaries	281	t	28159655	2308133	60000	2200
16	Mr. Morale	210	t	38928066	4453325	116000	1000
17	Mother I Sober	406	t	24419668	3544301	96000	842
18	Mirror	256	t	34019440	3670193	111000	791
19	The Heart Part V	332	t	48528772	41098540	1907000	18131
20	KICK BACK	193	f	32748647	44720763	1372000	10300
21	For Every Voice That Never Sang	227	f	579319	33176	749	4
22	hometown	256	f	65931881	2550820	43300	718
23	Lo Air	182	f	6150708	293704	7100	61
25	found & lost	188	f	24985978	9253719	140000	944
24	Sincerely	287	f	9698290	8744509	118000	2115
27	Walkin	280	t	41514533	7790072	275900	1729
28	drowning.	257	f	27617984	16507689	135000	2875
29	Fumes	211	f	18481982	17572665	182000	2100
30	Jupiter	242	f	8046765	3961911	24000	348
31	Soul	126	f	3389645	1391900	8778	164
32	Man Down	315	f	7623345	4480241	36000	525
\.


--
-- Name: artist_id_seq; Type: SEQUENCE SET; Schema: orlab; Owner: orlab
--

SELECT pg_catalog.setval('orlab.artist_id_seq', 60, true);


--
-- Name: collection_id_seq; Type: SEQUENCE SET; Schema: orlab; Owner: orlab
--

SELECT pg_catalog.setval('orlab.collection_id_seq', 16, true);


--
-- Name: track_id_seq; Type: SEQUENCE SET; Schema: orlab; Owner: orlab
--

SELECT pg_catalog.setval('orlab.track_id_seq', 32, true);


--
-- Name: artist_collection artist_collection_pk; Type: CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.artist_collection
    ADD CONSTRAINT artist_collection_pk PRIMARY KEY (artist_id, collection_id);


--
-- Name: artist artist_pk; Type: CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.artist
    ADD CONSTRAINT artist_pk PRIMARY KEY (id);


--
-- Name: artist_track artist_track_pk; Type: CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.artist_track
    ADD CONSTRAINT artist_track_pk PRIMARY KEY (artist_id, track_id);


--
-- Name: collection collection_pk; Type: CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.collection
    ADD CONSTRAINT collection_pk PRIMARY KEY (id);


--
-- Name: collection_track collection_track_pk; Type: CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.collection_track
    ADD CONSTRAINT collection_track_pk PRIMARY KEY (collection_id, track_id);


--
-- Name: track track_pk; Type: CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.track
    ADD CONSTRAINT track_pk PRIMARY KEY (id);


--
-- Name: artist_name_index; Type: INDEX; Schema: orlab; Owner: orlab
--

CREATE INDEX artist_name_index ON orlab.artist USING btree (name);


--
-- Name: track_spotify_streams_index; Type: INDEX; Schema: orlab; Owner: orlab
--

CREATE INDEX track_spotify_streams_index ON orlab.track USING btree (spotify_streams);


--
-- Name: track_youtube_streams_youtube_likes_index; Type: INDEX; Schema: orlab; Owner: orlab
--

CREATE INDEX track_youtube_streams_youtube_likes_index ON orlab.track USING btree (youtube_streams, youtube_likes);


--
-- Name: artist_collection artist_collection_artist_null_fk; Type: FK CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.artist_collection
    ADD CONSTRAINT artist_collection_artist_null_fk FOREIGN KEY (artist_id) REFERENCES orlab.artist(id);


--
-- Name: artist_collection artist_collection_collection_null_fk; Type: FK CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.artist_collection
    ADD CONSTRAINT artist_collection_collection_null_fk FOREIGN KEY (collection_id) REFERENCES orlab.collection(id);


--
-- Name: artist_track artist_track_artist_null_fk; Type: FK CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.artist_track
    ADD CONSTRAINT artist_track_artist_null_fk FOREIGN KEY (artist_id) REFERENCES orlab.artist(id);


--
-- Name: artist_track artist_track_track_null_fk; Type: FK CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.artist_track
    ADD CONSTRAINT artist_track_track_null_fk FOREIGN KEY (track_id) REFERENCES orlab.track(id);


--
-- Name: collection_track collection_track_collection_null_fk; Type: FK CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.collection_track
    ADD CONSTRAINT collection_track_collection_null_fk FOREIGN KEY (collection_id) REFERENCES orlab.collection(id);


--
-- Name: collection_track collection_track_track_null_fk; Type: FK CONSTRAINT; Schema: orlab; Owner: orlab
--

ALTER TABLE ONLY orlab.collection_track
    ADD CONSTRAINT collection_track_track_null_fk FOREIGN KEY (track_id) REFERENCES orlab.track(id);


--
-- Name: DATABASE orlab; Type: ACL; Schema: -; Owner: postgres
--

GRANT CONNECT ON DATABASE orlab TO orlab;


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4 (Debian 14.4-1.pgdg110+1)
-- Dumped by pg_dump version 14.4 (Debian 14.4-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

