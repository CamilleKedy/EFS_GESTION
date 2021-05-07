SET search_path TO projet;


-- Schéma

DROP SCHEMA IF EXISTS efsGestion CASCADE;
CREATE SCHEMA efsGestion AUTHORIZATION projet;
GRANT ALL PRIVILEGES ON SCHEMA efsGestion TO projet;


-- Tables

CREATE TABLE Personnel(
   id_personnel serial,
   nom VARCHAR(50) NOT NULL,
   prenom VARCHAR(50) NOT NULL,
   adresse VARCHAR(50) NOT NULL,
   profession VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_personnel)
);

CREATE TABLE Donneur(
   id_donneur serial,
   nom_donneur VARCHAR(50) NOT NULL,
   prenom_donneur VARCHAR(50) NOT NULL,
   date_naissance DATE NOT null check(date_naissance- current_date >= 18),
   ville_donneur VARCHAR(50) NOT NULL,
   adresse_donneur VARCHAR(50) NOT NULL,
   demande_carte CHAR(3) NOT null check(demande_carte in ('oui','non')) ,
   PRIMARY KEY(id_donneur)
);

CREATE TABLE Site_de_collecte(
   id_site serial,
   ville VARCHAR(50) NOT NULL,
   nbr_lits INT,
   adresse VARCHAR(100) NOT NULL,
   PRIMARY KEY(id_site)
);

CREATE TABLE Connexion(
   IdConnexion  serial,
   login VARCHAR(50) NOT NULL,
   motDePasse VARCHAR(10) NOT NULL,
   role VARCHAR(10) NOT NULL,
   id_personnel INT NOT NULL,
   PRIMARY KEY(IdConnexion),
   FOREIGN KEY(id_personnel) REFERENCES Personnel(id_personnel)
);

CREATE TABLE dossierMedical(
   id_dossier  serial,
   groupe_sanguin VARCHAR(3) NOT null  check(groupe_sanguin in ('A','AB','B','O')),
   rhesus VARCHAR(10),
   poids real NOT NULL,
   inaptitude_temporaire VARCHAR(50),
   id_donneur INT NOT NULL,
   PRIMARY KEY(id_dossier),
   UNIQUE(id_donneur),
   FOREIGN KEY(id_donneur) REFERENCES Donneur(id_donneur)
);

CREATE TABLE Materiel(
   id_materiel serial,
   quantite VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_materiel)
);

CREATE TABLE Collecte(
   id_collecte serial,
   qte_collation INT NOT null check( qte_collation >=0),
   date_debut DATE NOT null check(date_debut > CURRENT_DATE),
   date_fin DATE NOT null check(date_fin > date_debut),
   nbre_infirmiers INT not null,
   nbre_medecins INT not null,
   nbre_secretaire INT not null,
   nbre_agents_collation INT not null,
   horaire_debut TIME NOT NULL,
   horaire_fin TIME check(horaire_fin > horaire_debut),
   id_site INT NOT NULL,
   PRIMARY KEY(id_collecte),
   FOREIGN KEY(id_site) REFERENCES Site_de_collecte(id_site)
);

CREATE TABLE RDV(
   id_rdv  serial,
   heure_rdv TIME NOT NULL,
   prise_de_sang CHAR(3) NOT NULL,
   date_rdv DATE NOT NULL,
   qte_sang_donnee DECIMAL(15,2),
   id_collecte INT NOT NULL,
   id_donneur INT NOT NULL,
   PRIMARY KEY(id_rdv),
   FOREIGN KEY(id_collecte) REFERENCES Collecte(id_collecte),
   FOREIGN KEY(id_donneur) REFERENCES Donneur(id_donneur),
   check (prise_de_sang in ('oui', 'non'))
  
);

CREATE TABLE personnelDeCollecte(
   id_personnel INT,
   id_collecte INT,
   PRIMARY KEY(id_personnel, id_collecte),
   FOREIGN KEY(id_personnel) REFERENCES Personnel(id_personnel),
   FOREIGN KEY(id_collecte) REFERENCES Collecte(id_collecte)
);

CREATE TABLE materielDeCollecte(
   id_collecte INT,
   id_materiel INT,
   quantite INT,
   PRIMARY KEY(id_collecte, id_materiel),
   FOREIGN KEY(id_collecte) REFERENCES Collecte(id_collecte),
   FOREIGN KEY(id_materiel) REFERENCES Materiel(id_materiel)
);


-- Vues

CREATE VIEW v_compte_avec_roles AS
	SELECT c.*, ARRAY_AGG( r.role ) AS roles
	FROM compte c
	LEFT JOIN role r ON c.idcompte = r.idcompte
	GROUP BY c.idcompte;
