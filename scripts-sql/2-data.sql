SET search_path TO projet;


-- Supprimer toutes les données
DELETE FROM Personnel;
DELETE FROM Donneur;
DELETE FROM Site_de_collecte;
DELETE FROM Connexion;
DELETE FROM dossierMedical;
DELETE FROM Materiel;
DELETE FROM Collecte;
DELETE FROM RDV;
DELETE FROM personnelDeCollecte;
DELETE FROM materielDeCollecte;


-- Personnel

INSERT INTO Personnel (id_personnel, nom, prenom, adresse, profession ) VALUES 
  (1, 'cardinal', 'cardinal', 'system', 'Gestionnaire de site'),
  (2, 'KEDY', 'Albert', '18 rue JAB', 'Agent de collation' ),
  (3, 'TANKEU', 'Ange', '92 rue GDG', 'infirmière' ),
  (4, 'TCHAMGOUE', 'Yvana', '21 rue JFK', 'Sécrétaire' ),
  (5, 'NGABMEN', 'Benjamin', '16 rue JAB', 'Médecin' );

--ALTER TABLE Personnel ALTER COLUMN id_personnel RESTART WITH 6;


-- Donneur

INSERT INTO Donneur (id_donneur, nom_donneur, prenom_donneur, date_naissance, ville_donneur, adresse_donneur, demande_carte) VALUES 
  ( 1, 'DUTROUDET', 'Camille',    {d '1996-06-17' }, 'Limoges',     '96 avenue GHR',  'oui' ),
  ( 2, 'LACAN',     'Bérangère',  {d '1983-07-30' }, 'Rouen',       '11 avenue KHK',  'non' ),
  ( 3, 'LENORMAND', 'Robin',      {d '1972-10-02' }, 'Montpellier', '6 rue VB',       'oui' ),
  ( 4, 'FONDRAT',   'Karin',      {d '1990-06-17' }, 'Paris',       '20 rue ALG',     'non' ),
  ( 5, 'TRUDEAU',   'Blandine',   {d '1985-11-16' }, 'Brest',       '32 rue RR',      'oui' );

--ALTER TABLE Donneur ALTER COLUMN id_donneur RESTART WITH 6;

-- Site_de_collecte
  
INSERT INTO Site_de_collecte (id_site, ville, nbr_lits, adresse ) VALUES 
  (1, 'Limoges',  22,   '19 avenue ART' ),
  (2, 'Bordeaux', 22,   '20 avenue HT' ),
  (3, 'Nantes',   19,   '2 avenue MPN' ),
  (4, 'Paris',    29,   '92 avenue KKT' ),
  (5, 'Toulouse', 18,   '17 impasse StA' );

--ALTER TABLE Site_de_collecte ALTER COLUMN id_site RESTART WITH 6;


-- Connexion

INSERT INTO Connexion (IdConnexion, login,  motDePasse, role/*, id_personnel*/) VALUES 
  ( 1, 'cardinal',  'cardinal',   'Gestionnaire'/*, 2*/ ),
  ( 2, 'nawel', 'nawel',  'Sécrétaire'/*, 3 */);

--ALTER TABLE Connexion ALTER COLUMN IdConnexion RESTART WITH 3;


-- dossierMedical

INSERT INTO dossierMedical (id_dossier, groupe_sanguin, rhesus, poids, inaptitude_temporaire/*, id_donneur*/ ) VALUES 
  ( 1, 'A',   'positif',  85,   'A fait un don il y''a 6 jours.'/*, 1*/ ),
  ( 2, 'AB',  'negatif',  57,   NULL/*, 2*/ ),
  ( 3, 'O',   'negatif',  89,   'A fait un don il y''a 3 semaines.'/*, 3*/ ),
  ( 4, 'O',   NULL,       44,   NULL/*, 4 */),
  ( 5, 'B',   'positif',  60,   NULL/*, 5*/ );

--ALTER TABLE dossierMedical ALTER COLUMN id_dossier RESTART WITH 6;


-- Materiel
  
INSERT INTO Materiel (id_materiel, quantite) VALUES 
  (1,   100 ),
  (2,   25),
  (3,   39);

--ALTER TABLE Materiel ALTER COLUMN id_materiel RESTART WITH 4;


-- Collecte

INSERT INTO Collecte (id_collecte, qte_collation, date_debut, date_fin, nbre_infirmiers, nbre_medecins, nbre_secretaire, nbre_agents_collation, horaire_debut, horaire_fin/*, id_site*/) VALUES 
  ( 1, 100, {d '2022-01-03'}, {d '2022-01-31'}, 1, 1, 1, 1, '8:00:00', '17:30:00'/*, 1 */);

--ALTER TABLE Collecte ALTER COLUMN id_collecte RESTART WITH 2;


-- RDV

INSERT INTO RDV ( id_rdv, heure_rdv, prise_de_sang, date_rdv, qte_sang_donnee/*, id_collecte, id_donneur*/ ) VALUES 
  ( 1, '08:00:00', 'oui', {d '2022-01-04' }, 450/*, 1, 1*/),
  ( 2, '09:00:00', 'oui', {d '2022-01-04' }, 420/*, 1, 2*/),
  ( 3, '15:00:00', 'non', {d '2022-01-13' }, NULL/*, 1, 5*/),
  ( 4, '08:00:00', 'oui', {d '2022-01-05' }, 480/*, 1, 3*/);

ALTER TABLE RDV ALTER COLUMN id_rdv RESTART WITH 5;


-- personnelDeCollecte
 /* INSERT INTO personnelDeCollecte (id_personnel, id_collecte) VALUES 
  (1, '100' ),
  (2, '25'),
  (3, '39');*/

-- materielDeCollecte
 
--INSERT INTO materielDeCollecte (/*id_collecte, id_materiel, */quantite) VALUES 
--  (10);
  

