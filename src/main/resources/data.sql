-- USERS

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('admin#14', '$2a$10$GaXANWZAe9KEU.2uIb6c1u/PH54uW6oUY3ARdRRt2yGLWVCj2rIvG', 1);

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('basic#14', '$2a$10$6yXPcLlYmVx7uE/nBcszReHjJPoYqqJrX8BE/kTEFGkntzo0EgG8y', 1);

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('pro#14', '$2a$10$K6mxLoU/mq6DVEg3.JBbLuSbkveAP8/yNyC4v2tz2KvEbc2na08UK', 1);

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('prova#1#14', '$2a$10$T9NdjkEBy6vO0y43/xhQrOME2RL4.qdNepFgj1y.HKofS7p/d65oW', 1);

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('prova#2#14', '$2a$10$T9NdjkEBy6vO0y43/xhQrOME2RL4.qdNepFgj1y.HKofS7p/d65oW', 0);

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('prova#3#14', '$2a$10$T9NdjkEBy6vO0y43/xhQrOME2RL4.qdNepFgj1y.HKofS7p/d65oW', 1);

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('prova#4#14', '$2a$10$T9NdjkEBy6vO0y43/xhQrOME2RL4.qdNepFgj1y.HKofS7p/d65oW', 1);

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('prova#5#14', '$2a$10$T9NdjkEBy6vO0y43/xhQrOME2RL4.qdNepFgj1y.HKofS7p/d65oW', 1);

MERGE INTO USERS (username, password, enabled) KEY (username)
VALUES ('prova#6#14', '$2a$10$T9NdjkEBy6vO0y43/xhQrOME2RL4.qdNepFgj1y.HKofS7p/d65oW', 1);



-- AUTHORITIES

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
VALUES ('admin#14', 'ROLE_ADMIN');

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
VALUES ('basic#14', 'ROLE_USER_BASIC');

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
VALUES ('pro#14', 'ROLE_USER_PRO');

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
VALUES ('prova#1#14', 'ROLE_USER_PROVA');

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
    VALUES ('prova#2#14', 'ROLE_USER_PROVA');

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
VALUES ('prova#3#14', 'ROLE_USER_PROVA');

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
VALUES ('prova#4#14', 'ROLE_USER_PROVA');

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
VALUES ('prova#5#14', 'ROLE_USER_PROVA');

MERGE INTO AUTHORITIES (username, authority) KEY (username, authority)
VALUES ('prova#6#14', 'ROLE_USER_PROVA');



-- USERDATA

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('admin#14', 'Admin', 'GymInTech', DATE '1990-01-01', 'admin14@gymintech.com', 0, DATE '2026-01-01');

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('basic#14', 'Maria', 'Bianchi', DATE '1995-03-12', 'basic14@email.com', 0, DATE '2026-01-01');

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('pro#14', 'Sara', 'Verdi', DATE '1992-07-22', 'pro14@email.com', 0, DATE '2026-01-01');

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('prova#1#14', 'Luca', 'Rossi', DATE '2000-01-15', 'prova1.14@email.com', 0, DATE '2026-01-01');

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('prova#2#14', 'Giulia', 'Rosa', DATE '1998-05-30', 'prova2.14@email.com', 3, DATE '2026-02-01');

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('prova#3#14', 'Andrea', 'Laszlo', DATE '1999-11-08', 'prova3.14@email.com', 0, DATE '2026-03-01');

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('prova#4#14', 'Elisa', 'Tomasi', DATE '2001-02-19', 'prova4.14@gymintech.com', 0, DATE '2026-04-01');

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('prova#5#14', 'Mattia', 'Vita', DATE '1997-09-03', 'prova5.14@gymintech.com', 0, DATE '2026-05-01');

MERGE INTO USERDATA (username, firstName, lastName, birthDate, email, numAllenamenti, signupDate) KEY (username)
VALUES ('prova#6#14', 'Arianna', 'Giassi', DATE '2002-12-25', 'prova6.14@gymintech.com', 0, DATE '2026-06-01');



-- USER_PRO_PROGRAMMI

MERGE INTO USER_PRO_PROGRAMMI (id, nome, username, kcal, numAllenamenti) KEY (id)
VALUES (1, 'Corpo Libero', 'pro#14', 90, 0);



-- ESERCIZI_PROGRAMMI_PERSONALIZZATI

MERGE INTO ESERCIZI_PROGRAMMI_PERSONALIZZATI (id, idProg, nomeEs, numSerie, numRep, kcal) KEY (id)
VALUES (1, 1, 'Squat', 3, 5, 30);

MERGE INTO ESERCIZI_PROGRAMMI_PERSONALIZZATI (id, idProg, nomeEs, numSerie, numRep, kcal) KEY (id)
VALUES (2, 1, 'Flessioni', 3, 5, 30);

MERGE INTO ESERCIZI_PROGRAMMI_PERSONALIZZATI (id, idProg, nomeEs, numSerie, numRep, kcal) KEY (id)
VALUES (3, 1, 'Affondi', 3, 5, 30);



-- RECENSIONI

MERGE INTO RECENSIONI (id, nome, recensione) KEY (id)
VALUES (1, 'Luca', 'Ottima applicazione, semplice e intuitiva da usare!');

MERGE INTO RECENSIONI (id, nome, recensione) KEY (id)
VALUES (2, 'Andrea', 'Mi piace molto poter tenere traccia dei miei allenamenti.');

MERGE INTO RECENSIONI (id, nome, recensione) KEY (id)
VALUES (3, 'Sara', 'I programmi sono fantastici, consigliatissimo!');