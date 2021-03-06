DROP TABLE IF EXISTS Categories;
CREATE TABLE Categories(
  id_categorie INTEGER PRIMARY KEY,
  libelle_categorie TEXT UNIQUE,
  id_cat_parent INTEGER
);

DROP TABLE IF EXISTS Etiquettes;
CREATE TABLE Etiquettes(
  id_etiquette INTEGER PRIMARY KEY,
  nom_etiquette TEXT UNIQUE
);

DROP TABLE IF EXISTS Etiquettes_Textes;
CREATE TABLE Etiquettes_Textes(
  id_etiquette_texte INTEGER PRIMARY KEY,
  id_etiquette INTEGER,
  id_texte INTEGER
);

DROP TABLE IF EXISTS Textes;
CREATE TABLE Textes(
  id_texte INTEGER PRIMARY KEY,
  nom_texte TEXT UNIQUE,
  id_categorie INTEGER NOT NULL,
  contenu TEXT,
  FOREIGN KEY(id_categorie) REFERENCES Categories(id_categorie)
);

DROP TABLE IF EXISTS Documents;
CREATE TABLE Documents(
  id_document INTEGER PRIMARY KEY,
  nom_document TEXT UNIQUE,
  date_creation TEXT,
  date_modif TEXT
);

