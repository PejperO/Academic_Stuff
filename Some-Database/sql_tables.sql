-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2021-12-01 16:13:44.99

-- tables
-- Table: Klasa
CREATE TABLE Klasa (
    Klasa int  NOT NULL,
    Data_tworzenia date  NOT NULL,
    Data_zakonczenia date  NULL,
    CONSTRAINT Klasa_pk PRIMARY KEY  (Klasa)
);

-- Table: Nauczyciele
CREATE TABLE Nauczyciele (
    id int  NOT NULL,
    Imie varchar(40)  NOT NULL,
    Nazwisko varchar(40)  NOT NULL,
    Przedmiot varchar(60)  NULL,
    CONSTRAINT Nauczyciele_pk PRIMARY KEY  (id)
);

-- Table: Oceny
CREATE TABLE Oceny (
    id int  NOT NULL,
    Nauczyciele_id int  NOT NULL,
    Uczniowie_id int  NOT NULL,
    Ocena int  NOT NULL,
    Przedmiot varchar(40)  NOT NULL,
    CONSTRAINT Oceny_pk PRIMARY KEY  (id)
);

-- Table: Uczniowie
CREATE TABLE Uczniowie (
    id int  NOT NULL,
    Imie varchar(40)  NOT NULL,
    Nazwisko varchar(40)  NOT NULL,
    Klasa_Klasa int  NOT NULL,
    CONSTRAINT Uczniowie_pk PRIMARY KEY  (id)
);

-- Table: zaleznosc
CREATE TABLE zaleznosc (
    id int  NOT NULL,
    Nauczyciele_id int  NOT NULL,
    Klasa_Klasa int  NOT NULL,
    CONSTRAINT zaleznosc_pk PRIMARY KEY  (id)
);

-- foreign keys
-- Reference: Oceny_Nauczyciele (table: Oceny)
ALTER TABLE Oceny ADD CONSTRAINT Oceny_Nauczyciele
    FOREIGN KEY (Nauczyciele_id)
    REFERENCES Nauczyciele (id);

-- Reference: Oceny_Uczniowie (table: Oceny)
ALTER TABLE Oceny ADD CONSTRAINT Oceny_Uczniowie
    FOREIGN KEY (Uczniowie_id)
    REFERENCES Uczniowie (id);

-- Reference: Table_15_Klasa (table: zaleznosc)
ALTER TABLE zaleznosc ADD CONSTRAINT Table_15_Klasa
    FOREIGN KEY (Klasa_Klasa)
    REFERENCES Klasa (Klasa);

-- Reference: Table_15_Nauczyciele (table: zaleznosc)
ALTER TABLE zaleznosc ADD CONSTRAINT Table_15_Nauczyciele
    FOREIGN KEY (Nauczyciele_id)
    REFERENCES Nauczyciele (id);

-- Reference: Uczniowie_Klasa (table: Uczniowie)
ALTER TABLE Uczniowie ADD CONSTRAINT Uczniowie_Klasa
    FOREIGN KEY (Klasa_Klasa)
    REFERENCES Klasa (Klasa);

-- End of file.

