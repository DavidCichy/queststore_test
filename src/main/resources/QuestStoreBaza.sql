--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)

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
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: artifacts; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.artifacts (
    id integer NOT NULL,
    name text NOT NULL,
    description text NOT NULL,
    price integer NOT NULL,
    category integer
);


ALTER TABLE public.artifacts OWNER TO pulson;

--
-- Name: artifacts_categories; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.artifacts_categories (
    id integer NOT NULL,
    category text
);


ALTER TABLE public.artifacts_categories OWNER TO pulson;

--
-- Name: artifacts_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.artifacts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artifacts_id_seq OWNER TO pulson;

--
-- Name: artifacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.artifacts_id_seq OWNED BY public.artifacts.id;


--
-- Name: artifacts_statuses; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.artifacts_statuses (
    id integer NOT NULL,
    student_id integer,
    artifact_id integer,
    is_used boolean,
    buy_price integer
);


ALTER TABLE public.artifacts_statuses OWNER TO pulson;

--
-- Name: artifacts_statuses_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.artifacts_statuses_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artifacts_statuses_id_seq OWNER TO pulson;

--
-- Name: artifacts_statuses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.artifacts_statuses_id_seq OWNED BY public.artifacts_statuses.id;


--
-- Name: artifacts_types_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.artifacts_types_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artifacts_types_id_seq OWNER TO pulson;

--
-- Name: artifacts_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.artifacts_types_id_seq OWNED BY public.artifacts_categories.id;


--
-- Name: classes; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.classes (
    id integer NOT NULL,
    type text NOT NULL
);


ALTER TABLE public.classes OWNER TO pulson;

--
-- Name: classes_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.classes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.classes_id_seq OWNER TO pulson;

--
-- Name: classes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.classes_id_seq OWNED BY public.classes.id;


--
-- Name: students; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.students (
    id integer NOT NULL,
    name text NOT NULL,
    surname text NOT NULL,
    email text NOT NULL,
    class_id integer,
    user_id integer
);


ALTER TABLE public.students OWNER TO pulson;

--
-- Name: codecoolers_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.codecoolers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.codecoolers_id_seq OWNER TO pulson;

--
-- Name: codecoolers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.codecoolers_id_seq OWNED BY public.students.id;


--
-- Name: mentors; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.mentors (
    id integer NOT NULL,
    name text NOT NULL,
    surname text NOT NULL,
    email text NOT NULL,
    user_id integer
);


ALTER TABLE public.mentors OWNER TO pulson;

--
-- Name: mentors_classes; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.mentors_classes (
    id integer NOT NULL,
    mentor_id integer,
    class_id integer
);


ALTER TABLE public.mentors_classes OWNER TO pulson;

--
-- Name: mentors_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.mentors_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mentors_id_seq OWNER TO pulson;

--
-- Name: mentors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.mentors_id_seq OWNED BY public.mentors.id;


--
-- Name: quests; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.quests (
    id integer NOT NULL,
    name text NOT NULL,
    description text NOT NULL,
    reward integer NOT NULL,
    category integer NOT NULL
);


ALTER TABLE public.quests OWNER TO pulson;

--
-- Name: quests_categories; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.quests_categories (
    id integer NOT NULL,
    category text
);


ALTER TABLE public.quests_categories OWNER TO pulson;

--
-- Name: quests_categories_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.quests_categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quests_categories_id_seq OWNER TO pulson;

--
-- Name: quests_categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.quests_categories_id_seq OWNED BY public.quests_categories.id;


--
-- Name: quests_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.quests_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quests_id_seq OWNER TO pulson;

--
-- Name: quests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.quests_id_seq OWNED BY public.quests.id;


--
-- Name: quests_statuses; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.quests_statuses (
    id integer NOT NULL,
    student_id integer,
    quest_id integer,
    reward integer
);


ALTER TABLE public.quests_statuses OWNER TO pulson;

--
-- Name: quests_statuses_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.quests_statuses_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quests_statuses_id_seq OWNER TO pulson;

--
-- Name: quests_statuses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.quests_statuses_id_seq OWNED BY public.quests_statuses.id;


--
-- Name: sessions; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.sessions (
    id integer NOT NULL,
    user_id integer,
    session_id text
);


ALTER TABLE public.sessions OWNER TO pulson;

--
-- Name: sessions_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.sessions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sessions_id_seq OWNER TO pulson;

--
-- Name: sessions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.sessions_id_seq OWNED BY public.sessions.id;


--
-- Name: user_types; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.user_types (
    id integer,
    type text
);


ALTER TABLE public.user_types OWNER TO pulson;

--
-- Name: users; Type: TABLE; Schema: public; Owner: pulson
--

CREATE TABLE public.users (
    id integer NOT NULL,
    login text NOT NULL,
    password text NOT NULL,
    type integer NOT NULL
);


ALTER TABLE public.users OWNER TO pulson;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: pulson
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO pulson;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulson
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: artifacts id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.artifacts ALTER COLUMN id SET DEFAULT nextval('public.artifacts_id_seq'::regclass);


--
-- Name: artifacts_categories id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.artifacts_categories ALTER COLUMN id SET DEFAULT nextval('public.artifacts_types_id_seq'::regclass);


--
-- Name: artifacts_statuses id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.artifacts_statuses ALTER COLUMN id SET DEFAULT nextval('public.artifacts_statuses_id_seq'::regclass);


--
-- Name: classes id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.classes ALTER COLUMN id SET DEFAULT nextval('public.classes_id_seq'::regclass);


--
-- Name: mentors id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.mentors ALTER COLUMN id SET DEFAULT nextval('public.mentors_id_seq'::regclass);


--
-- Name: quests id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.quests ALTER COLUMN id SET DEFAULT nextval('public.quests_id_seq'::regclass);


--
-- Name: quests_categories id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.quests_categories ALTER COLUMN id SET DEFAULT nextval('public.quests_categories_id_seq'::regclass);


--
-- Name: quests_statuses id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.quests_statuses ALTER COLUMN id SET DEFAULT nextval('public.quests_statuses_id_seq'::regclass);


--
-- Name: sessions id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.sessions ALTER COLUMN id SET DEFAULT nextval('public.sessions_id_seq'::regclass);


--
-- Name: students id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.students ALTER COLUMN id SET DEFAULT nextval('public.codecoolers_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: artifacts; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.artifacts (id, name, description, price, category) FROM stdin;
1	Circle of Sorcery	60 min workshop by a mentor(s) of the chosen topic	1000	1
2	Summon Code Elemental	Mentor joins a student team for a one hour	1000	2
3	Tome of knowledge	Extra material for the currnet topic	500	2
4	Transform mentors	All mentors should dress up as pirates (or just funny) for the day	30000	1
5	Combat training	Private mentoring	50	2
6	Sanctuary	You can spend a day in home office	300	2
7	Time Travel	Extend SI week assignment deadline by one day	500	1
\.


--
-- Data for Name: artifacts_categories; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.artifacts_categories (id, category) FROM stdin;
1	magic
2	basic
\.


--
-- Data for Name: artifacts_statuses; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.artifacts_statuses (id, student_id, artifact_id, is_used, buy_price) FROM stdin;
1	3	2	f	1000
3	3	6	f	300
4	3	3	t	500
2	3	5	t	50
5	3	5	t	50
6	3	5	t	50
\.


--
-- Data for Name: classes; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.classes (id, type) FROM stdin;
1	progbasics
2	java
3	web
4	advanced
\.


--
-- Data for Name: mentors; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.mentors (id, name, surname, email, user_id) FROM stdin;
1	Edward	Edwardowaty	edward@edwardowaty.com	3
2	Esmeralda	Szeroka	e@gmail.com	5
3	Jan	Kowalski	xd@o2.pl	6
\.


--
-- Data for Name: mentors_classes; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.mentors_classes (id, mentor_id, class_id) FROM stdin;
1	1	4
5	1	1
2	2	3
4	2	4
3	3	2
\.


--
-- Data for Name: quests; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.quests (id, name, description, reward, category) FROM stdin;
1	Spot trap	Spot a major mistake in the assignment	50	1
2	Taming a pet	Doing a demo about a pet project	100	1
3	Recruiting some n00bs	Taking part in the student creening process	100	1
4	Forging weapons	Organizing a workshop for other students	400	1
5	Master the mornings	Attend 1 month without being late	300	1
6	Fast as an unicorn	Deliver 4 consecutive SI week assignments on time	500	1
7	Achiever	Set up a SMART goal accepted by a mentor, then achieve it	1000	1
8	Fortune	Students choose the best project of the week. Selected team scores	500	1
10	Enter the arena	Do a presentation on a meet-up	500	1
11	Exploring a dungeon	Finishing a Teamwork week	100	2
12	Solving the magic puzzle	Finishing an SI assignment	100	2
13	Slaying a dragon	Passing a Checkpoint	500	2
9	Creating an scroll	Creating extra material for the current TW/SI topic (should be revised by mentors)	500	1
14	XDDDDD	dupa	500	1
\.


--
-- Data for Name: quests_categories; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.quests_categories (id, category) FROM stdin;
1	easy
2	medium
3	hard
\.


--
-- Data for Name: quests_statuses; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.quests_statuses (id, student_id, quest_id, reward) FROM stdin;
1	1	7	1000
2	3	13	500
4	3	3	100
5	3	10	500
3	3	10	400
6	3	14	500
\.


--
-- Data for Name: sessions; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.sessions (id, user_id, session_id) FROM stdin;
76	4	01ac6d31-c4f1-44f5-9f29-4c2bd4e08aa9
78	4	101211b4-b014-41a2-86a4-4d688cb658de
79	4	3fe86c10-d97b-4bd7-b2df-9e40ee10de7e
\.


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.students (id, name, surname, email, class_id, user_id) FROM stdin;
1	Jan	Kowalski	jan@kowalski.net	1	1
2	Paweł	Kowalski	pawel@kowalski.net	2	2
4	Damian	Byk	byqu@wp.pl	2	7
5	Marcin	Mały	little@o2.pl	2	8
3	Marcin	Majkut	jd@com.pl	4	4
\.


--
-- Data for Name: user_types; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.user_types (id, type) FROM stdin;
1	mentor
2	student
3	admin
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: pulson
--

COPY public.users (id, login, password, type) FROM stdin;
2	Pawel	haslo	2
1	codecooler	password	2
3	mentor	1234	1
4	xayoo	jd	2
5	mentor2	1234	1
6	mentor3	1234	1
7	byqu	1337	2
8	maly	qazwsx	2
\.


--
-- Name: artifacts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.artifacts_id_seq', 7, true);


--
-- Name: artifacts_statuses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.artifacts_statuses_id_seq', 5, true);


--
-- Name: artifacts_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.artifacts_types_id_seq', 1, false);


--
-- Name: classes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.classes_id_seq', 4, true);


--
-- Name: codecoolers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.codecoolers_id_seq', 7, true);


--
-- Name: mentors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.mentors_id_seq', 2, true);


--
-- Name: quests_categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.quests_categories_id_seq', 1, false);


--
-- Name: quests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.quests_id_seq', 13, true);


--
-- Name: quests_statuses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.quests_statuses_id_seq', 5, true);


--
-- Name: sessions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.sessions_id_seq', 79, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulson
--

SELECT pg_catalog.setval('public.users_id_seq', 3, true);


--
-- Name: artifacts_categories artifacts_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.artifacts_categories
    ADD CONSTRAINT artifacts_categories_pkey PRIMARY KEY (id);


--
-- Name: artifacts artifacts_pk; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.artifacts
    ADD CONSTRAINT artifacts_pk PRIMARY KEY (id);


--
-- Name: artifacts_statuses artifacts_statuses_pkey; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.artifacts_statuses
    ADD CONSTRAINT artifacts_statuses_pkey PRIMARY KEY (id);


--
-- Name: classes classes_pk; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.classes
    ADD CONSTRAINT classes_pk PRIMARY KEY (id);


--
-- Name: students codecoolers_pk; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT codecoolers_pk PRIMARY KEY (id);


--
-- Name: mentors_classes mentors_classes_pkey; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.mentors_classes
    ADD CONSTRAINT mentors_classes_pkey PRIMARY KEY (id);


--
-- Name: mentors mentors_pk; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.mentors
    ADD CONSTRAINT mentors_pk PRIMARY KEY (id);


--
-- Name: quests_categories quests_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.quests_categories
    ADD CONSTRAINT quests_categories_pkey PRIMARY KEY (id);


--
-- Name: quests quests_pk; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.quests
    ADD CONSTRAINT quests_pk PRIMARY KEY (id);


--
-- Name: quests_statuses quests_statuses_pkey; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.quests_statuses
    ADD CONSTRAINT quests_statuses_pkey PRIMARY KEY (id);


--
-- Name: sessions sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_pkey PRIMARY KEY (id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: artifacts_id_uindex; Type: INDEX; Schema: public; Owner: pulson
--

CREATE UNIQUE INDEX artifacts_id_uindex ON public.artifacts USING btree (id);


--
-- Name: classes_id_uindex; Type: INDEX; Schema: public; Owner: pulson
--

CREATE UNIQUE INDEX classes_id_uindex ON public.classes USING btree (id);


--
-- Name: codecoolers_id_uindex; Type: INDEX; Schema: public; Owner: pulson
--

CREATE UNIQUE INDEX codecoolers_id_uindex ON public.students USING btree (id);


--
-- Name: mentors_id_uindex; Type: INDEX; Schema: public; Owner: pulson
--

CREATE UNIQUE INDEX mentors_id_uindex ON public.mentors USING btree (id);


--
-- Name: quests_id_uindex; Type: INDEX; Schema: public; Owner: pulson
--

CREATE UNIQUE INDEX quests_id_uindex ON public.quests USING btree (id);


--
-- Name: users_id_uindex; Type: INDEX; Schema: public; Owner: pulson
--

CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);


--
-- Name: users_login_uindex; Type: INDEX; Schema: public; Owner: pulson
--

CREATE UNIQUE INDEX users_login_uindex ON public.users USING btree (login);


--
-- Name: students class_id; Type: FK CONSTRAINT; Schema: public; Owner: pulson
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT class_id FOREIGN KEY (class_id) REFERENCES public.classes(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

