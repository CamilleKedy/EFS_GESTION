SET search_path TO projet;


-- Supprimer toutes les données

DELETE FROM Personnel;
DELETE FROM Role;
DELETE FROM Profession;
DELETE FROM Donneur;
DELETE FROM Site_de_collecte;
DELETE FROM Connexion;
DELETE FROM dossierMedical;
DELETE FROM Materiel;
DELETE FROM Collecte;
DELETE FROM RDV;
DELETE FROM personnelDeCollecte;
DELETE FROM materielDeCollecte;

-- Profession

INSERT INTO Profession (id_profession, libelle ) VALUES 
  (1, 'Gestionnaire de site'),
  (2, 'Agent de collation' ),
  (3, 'Infirmière' ),
  (4, 'Secrétaire' ),
  (5, 'Médecin' );

ALTER TABLE Profession ALTER COLUMN id_profession RESTART WITH 6;

-- Personnel

INSERT INTO Personnel (id_personnel, nom, prenom, adresse, id_profession ) VALUES 
  (1, 'cardinal', 'cardinal', 'system', 1),
  (2, 'KEDY', 'Albert', '18 rue JAB', 2 ),
  (3, 'TANKEU', 'Ange', '92 rue GDG', 3 ),
  (4, 'TCHAMGOUE', 'Yvana', '21 rue JFK', 4 ),
  (5, 'NGABMEN', 'Benjamin', '16 rue JAB', 5 );

ALTER TABLE Personnel ALTER COLUMN id_personnel RESTART WITH 6;


-- Donneur

INSERT INTO Donneur (id_donneur, nom_donneur, prenom_donneur, date_naissance,sexe_donneur, telephone_donneur,email_donneur , ville_donneur, adresse_donneur, demande_carte) VALUES 
  ( 1, 'DUTROUDET',   'Camille',    {d '1996-06-17' },  'Masculin',   '0766584820',   'camilled@mail.xyz',    'Limoges',     '96 avenue GHR',  'oui' ),
  ( 2, 'LACAN',       'Bérangère',  {d '1983-07-30' },  'Feminin',    '0766584820',   'berangerel@mail.xyz',  'Rouen',       '11 avenue KHK',  'non' ),
  ( 3, 'LENORMAND',   'Robin',      {d '1972-10-02' },  'Masculin',   '0766584820',   'robinl@mail.xyz',      'Montpellier', '6 rue VB',       'oui' ),
  ( 4, 'FONDRAT',     'Karin',      {d '1990-06-17' },  'Feminin',    '0766584820',   'karinf@mail.xyz',      'Paris',       '20 rue ALG',     'non' ),
  ( 5, 'TRUDEAU',     'Blandine',   {d '1985-11-16' },  'Feminin',    '0766584820',   'blandinet@mail.xyz',   'Brest',       '32 rue RR',      'oui' ),

  ( 6,  'GIRAUD',         'Zacharias',    {d '1992-06-17' },  'Masculin',   '0766584820',   'zachariasg@mail.xyz',  'Limoges',     '96 avenue IDN',   'oui' ),
  ( 7,  'PAGES',          'Matthieu',     {d '1983-06-12' },  'Masculin',   '0766584820',   'matthieup@mail.xyz',   'Limoges',     '11 avenue XJN',   'oui' ),
  ( 8,  'DE ROCHE',       'Emile',        {d '1971-11-08' },  'Masculin',   '0766584820',   'emiledr@mail.xyz',     'Bordeaux',    '6 rue VB',        'oui' ),
  ( 9,  'BRUN',           'Gérard',       {d '1999-06-17' },  'Masculin',   '0766584820',   'gerardb@mail.xyz',     'Paris',       '20 rue SZ',       'oui' ),
  ( 10, 'DELMAS-POTTIER', 'Roland',       {d '1985-11-16' },  'Masculin',   '0766584820',   'rolanddp@mail.xyz',    'Bordeaux',    '32 rue RR',       'oui' ),
  
  ( 11, 'RUIZ',     'Frédéric',   {d '1996-06-17' },  'Masculin',   '0766584820',   'fredericr@mail.xyz',  'Paris',       '96 avenue SS',  'oui' ),
  ( 12, 'CHAUVET',  'Augustin',   {d '1983-01-30' },  'Masculin',   '0766584820',   'augustinc@mail.xyz',  'Paris',       '11 avenue AZ',  'oui' ),
  ( 13, 'ROYER',    'Phillipe',   {d '1972-08-20' },  'Masculin',   '0766584820',   'philliper@mail.xyz',  'Montpellier', '6 rue QAS',     'oui' ),
  ( 14, 'JOUBERT',  'Etienne',    {d '1990-08-23' },  'Masculin',   '0766584820',   'etiennej@mail.xyz',   'Paris',       '20 rue NIS',    'non' ),
  ( 15, 'MARTINEZ', 'Emile',      {d '1985-09-11' },  'Masculin',   '0766584820',   'emilem@mail.xyz',     'Brest',       '32 rue QAJ',    'oui' ),
  
  ( 16, 'MATHIEU',  'Stephane',    {d '2000-06-17' },  'Masculin',   '0766584820',   'stephanem@mail.xyz',  'Limoges',  '96 avenue IND',  'oui' ),
  ( 17, 'BLONDEL',  'Denis',       {d '1982-07-12' },  'Masculin',   '0766584820',   'denisb@mail.xyz',     'Limoges',  '11 avenue EH',   'non' ),
  ( 18, 'GRONDIN',  'Joseph',      {d '2000-10-21' },  'Masculin',   '0766584820',   'josephg@mail.xyz',    'Reims',    '6 rue IE',       'oui' ),
  ( 19, 'FABRE',    'Thomas',      {d '1995-06-21' },  'Masculin',   '0766584820',   'thomasf@mail.xyz',    'Le Havre', '20 rue DGZ',     'non' ),
  ( 20, 'DE JOLY',  'Bertrand',    {d '1975-11-23' },  'Masculin',   '0766584820',   'bertranddj@mail.xyz', 'Lille',    '32 rue PDK',     'oui' ),
  
  ( 21, 'FAIVRE',   'Gabriel',    {d '1976-06-06' },  'Masculin',   '0766584820',   'gabrielf@mail.xyz',  'Limoges',     '96 avenue GZR',  'oui' ),
  ( 22, 'REYNAUD',  'Victor',     {d '1973-05-30' },  'Masculin',   '0766584820',   'victorr@mail.xyz',   'Lille',       '11 avenue XSJ',  'oui' ),
  ( 23, 'DUFOUR',   'Laurent',    {d '1982-01-02' },  'Masculin',   '0766584820',   'laurentd@mail.xyz',  'Lille',       '6 rue HD',       'oui' ),
  ( 24, 'SANCHEZ',  'Eugène',     {d '1992-06-21' },  'Masculin',   '0766584820',   'eugenes@mail.xyz',   'Lille',       '20 rue UUU',     'non' ),
  ( 25, 'MUNOZ',    'Aimé',       {d '1995-11-16' },  'Masculin',   '0766584820',   'aimem@mail.xyz',     'Nancy',       '32 rue RR',      'oui' ),
  
  ( 26, 'DIDIER',         'Gabriel',    {d '1996-09-17' },  'Masculin',   '0766584820',   'gabrield@mail.xyz',    'Limoges',     '96 avenue ZZZ',  'oui' ),
  ( 27, 'GARCIA-PELTIER', 'Louis',      {d '1993-03-30' },  'Masculin',   '0766584820',   'louisgp@mail.xyz',     'Nancy',       '11 avenue AAA',  'oui' ),
  ( 28, 'LEGENDRE',       'Honoré',     {d '2000-11-02' },  'Masculin',   '0766584820',   'honorel@mail.xyz',     'Montpellier', '6 rue DD',       'oui' ),
  ( 29, 'BAILLY',         'Aimé',       {d '2000-06-17' },  'Masculin',   '0766584820',   'aimeb@mail.xyz',       'Paris',       '20 rue WAW',     'oui' ),
  ( 30, 'VALET-MONIER',   'Frédéric',   {d '2000-11-16' },  'Masculin',   '0766584820',   'fredericvm@mail.xyz',  'Nancy',       '32 rue NX',      'oui' ),
  
  ( 31, 'GUILLOU',    'Georges',    {d '2000-06-17' },  'Masculin',   '0766584820',   'georgesg@mail.xyz',  'Limoges',     '96 avenue PL',   'oui' ),
  ( 32, 'LEBRETON',   'Alfred',     {d '1983-07-30' },  'Masculin',   '0766584820',   'alfredl@mail.xyz',   'Rouen',       '11 avenue IKM',  'oui' ),
  ( 33, 'SANCHEZ',    'Théo',       {d '2001-10-02' },  'Masculin',   '0766584820',   'theos@mail.xyz',     'Montpellier', '12 rue VB',      'oui' ),
  ( 34, 'MENDES',     'Thiago',     {d '1990-06-17' },  'Masculin',   '0766584820',   'thiagom@mail.xyz',   'Paris',       '20 rue NXN',     'oui' ),
  ( 35, 'ROCHE',      'Paul',       {d '1981-11-16' },  'Masculin',   '0766584820',   'paulr@mail.xyz',     'Toulouse',    '33 rue RR',      'oui' ),
  
  ( 36, 'MASSE',        'Jean',     {d '2000-06-17' },  'Masculin',   '0766584820',   'jeanm@mail.xyz',     'Limoges',     '96 avenue GHR',  'oui' ),
  ( 37, 'VIDAL',        'Vincent',  {d '1983-02-11' },  'Masculin',   '0766584820',   'vincentv@mail.xyz',  'Rouen',       '22 avenue KHK',  'non' ),
  ( 38, 'DE LA PEFIA',  'Ian',      {d '1972-11-02' },  'Masculin',   '0766584820',   'iandlp@mail.xyz',    'Montpellier', '6 rue VB',       'oui' ),
  ( 39, 'COSTE',        'Henri',    {d '2001-06-17' },  'Masculin',   '0766584820',   'henric@mail.xyz',    'Paris',       '22 rue ALG',     'oui' ),
  ( 40, 'DA COSTA',     'Auguste',  {d '1985-11-16' },  'Masculin',   '0766584820',   'augustedc@mail.xyz', 'Brest',       '22 rue DRF',     'oui' ),
  
  ( 41, 'TAPOCK',   'Jean de Dieu',     {d '2000-06-17' },  'Masculin',   '0766584820',   'jeandedieut@mail.xyz',   'Limoges',     '96 avenue GHR',  'oui' ),
  ( 42, 'KEMFANG',  'Duplex',           {d '2000-08-30' },  'Masculin',   '0766584820',   'duplexk@mail.xyz',       'Paris',       '11 avenue KHK',  'oui' ),
  ( 43, 'VALLET',   'Grégoire',         {d '2000-10-02' },  'Masculin',   '0766584820',   'gregoirev@mail.xyz',     'Bordeaux',    '6 rue VB',       'oui' ),
  ( 44, 'LAINE',    'Thibaut',          {d '1990-03-17' },  'Masculin',   '0766584820',   'thibautl@mail.xyz',      'Lille',       '20 rue TTT',     'non' ),
  ( 45, 'BONNEAU',  'Nicolas',          {d '1985-12-16' },  'Masculin',   '0766584820',   'nicolasb@mail.xyz',      'Paris',       '32 rue NC',      'oui' ),
  
  ( 46, 'GUIBERT',  'Zacharias',    {d '1996-06-27' },  'Masculin',   '0766584820',   'zachariasg@mail.xyz',  'Limoges',     '11 avenue III',   'oui' ),
  ( 47, 'LEDOUX',   'Henri',        {d '1983-07-12' },  'Masculin',   '0766584820',   'henril@mail.xyz',      'Paris',       '11 avenue UUU',   'oui' ),
  ( 48, 'NAVARRO',  'Lionnel',      {d '1972-10-12' },  'Masculin',   '0766584820',   'lionneln@mail.xyz',    'Montpellier', '6 rue UFU',       'oui' ),
  ( 49, 'MORALES',  'Kevin',        {d '2001-06-07' },  'Masculin',   '0766584820',   'kevinm@mail.xyz',      'Paris',       '5 rue LKF',       'non' ),
  ( 50, 'TOPRAK',   'Omer',         {d '1985-11-01' },  'Masculin',   '0766584820',   'omert@mail.xyz',       'Toulon',      '23 rue NVH',      'oui' ),



  ( 51, 'PIERRE',   'Noémi',      {d '2000-06-17' },  'Feminin',   '0766584820',   'noemip@mail.xyz',     'Limoges',    '11 avenue MC',    'oui' ),
  ( 52, 'LECOMTE',  'Lisa',       {d '1999-08-30' },  'Feminin',   '0766584820',   'lisal@mail.xyz',      'Lyon',       '11 avenue URU',   'oui' ),
  ( 53, 'VALLET',   'Astrid',     {d '2000-10-02' },  'Feminin',   '0766584820',   'astridv@mail.xyz',    'Paris',      '6 rue RER',       'oui' ),
  ( 54, 'LAINE',    'Sarah',      {d '1990-03-17' },  'Feminin',   '0766584820',   'sarahl@mail.xyz',     'Paris',      '20 rue EZE',      'oui' ),
  ( 55, 'BONNEAU',  'Henriette',  {d '1985-12-16' },  'Feminin',   '0766584820',   'henrietteb@mail.xyz', 'Brest',      '32 rue NCB',      'oui' ),
  
  ( 56, 'PEREZ',    'Luscia',     {d '1996-06-27' },  'Feminin',   '0766584820',   'lusciap@mail.xyz',    'Limoges',     '96 avenue IIE',  'oui' ),
  ( 57, 'LEDOUX',   'Dva',        {d '1983-07-12' },  'Feminin',   '0766584820',   'dval@mail.xyz',       'Rouen',       '11 avenue TTD',  'oui' ),
  ( 58, 'NAVARRO',  'Katryn',     {d '1972-10-12' },  'Feminin',   '0766584820',   'katrynn@mail.xyz',    'Limoges',     '6 rue RRE',      'oui' ),
  ( 59, 'MORALES',  'Lucy',       {d '2001-06-07' },  'Feminin',   '0766584820',   'lucym@mail.xyz',      'Paris',       '34 rue ALG',     'oui' ),
  ( 60, 'MILLET',   'Fréa',       {d '1985-11-01' },  'Feminin',   '0766584820',   'fream@mail.xyz',      'Marseille',   '32 rue RR',      'oui' ),
  
  ( 61, 'BOUSQUET', 'Jeanne',     {d '1992-06-17' },  'Feminin',   '0766584820',   'jeanneb@mail.xyz',    'Brest',       '90 avenue GHR',  'oui' ),
  ( 62, 'MALLET',   'Sarah',      {d '2000-08-30' },  'Feminin',   '0766584820',   'sarahm@mail.xyz',     'Rouen',       '78 avenue KHK',  'oui' ),
  ( 63, 'GAYA',     'Gina',       {d '2000-10-02' },  'Feminin',   '0766584820',   'ginag@mail.xyz',      'Toulouse',    '3 rue VB',       'oui' ),
  ( 64, 'GAYA',     'Suzan',      {d '1990-03-17' },  'Feminin',   '0766584820',   'suzang@mail.xyz',     'Nancy',       '2 rue ALG',      'non' ),
  ( 65, 'BIDAUD',   'Nicouleau',  {d '1985-12-16' },  'Feminin',   '0766584820',   'nicouleaub@mail.xyz', 'Brest',       '4 rue RR',       'oui' ),
  
  ( 66, 'PHILLIP',  'Diane',      {d '1996-06-27' },  'Feminin',   '0766584820',   'dianep@mail.xyz',     'Limoges',      '12 avenue GHR',  'oui' ),
  ( 67, 'NOAH',     'Diana',      {d '1983-07-12' },  'Feminin',   '0766584820',   'dianan@mail.xyz',     'Troyes',       '11 avenue KHK',  'non' ),
  ( 68, 'GUILBERT', 'Sandrine',   {d '1972-10-12' },  'Feminin',   '0766584820',   'sandrineg@mail.xyz',  'Montpellier',  '6 rue VB',       'oui' ),
  ( 69, 'THOMAS',   'Chantale',   {d '2001-06-07' },  'Feminin',   '0766584820',   'chantalet@mail.xyz',  'Nice',         '20 rue ALG',     'oui' ),
  ( 70, 'DIDIER',   'Mathilde',   {d '1985-11-01' },  'Feminin',   '0766584820',   'mathilded@mail.xyz',  'Nantes',       '34 rue RR',      'oui' ),

  ( 71, 'JACOBIN',    'Estelle',    {d '1999-06-17' },  'Feminin',   '0766584820',   'estellej@mail.xyz',  'Limoges',     '13 avenue GHR',  'oui' ),
  ( 72, 'CASTAGNE',   'Zoé',        {d '1988-08-30' },  'Feminin',   '0766584820',   'zoec@mail.xyz',      'Rouen',       '87 avenue KHK',  'non' ),
  ( 73, 'BARON',      'Valérie',    {d '1970-10-02' },  'Feminin',   '0766584820',   'valerieb@mail.xyz',  'Stasbourg',   '23 rue VB',      'oui' ),
  ( 74, 'LAINE',      'Mikaïa',     {d '1990-03-17' },  'Feminin',   '0766584820',   'mikaial@mail.xyz',   'Bordeaux',    '32 rue ALG',     'oui' ),
  ( 75, 'LESAGE',     'Corine',     {d '1985-12-16' },  'Feminin',   '0766584820',   'corinel@mail.xyz',   'Bordeaux',    '32 rue RR',      'oui' ),
  
  ( 76, 'GARCIA',         'Marcelle',    {d '1996-06-27' },  'Feminin',   '0766584820',   'marcelleg@mail.xyz',   'Lille',     '96 avenue GHR',  'oui' ),
  ( 77, 'DUBOIS',         'Cécile',      {d '1998-07-12' },  'Feminin',   '0766584820',   'ceciled@mail.xyz',     'Rennes',    '98 avenue KHK',  'oui' ),
  ( 78, 'LEBOUCHER',      'Lisa',        {d '1972-10-12' },  'Feminin',   '0766584820',   'lisal@mail.xyz',       'Rennes',    '63 rue VB',      'oui' ),
  ( 79, 'LEBOUCHER',      'Amélie',      {d '2001-06-07' },  'Feminin',   '0766584820',   'ameliel@mail.xyz',     'Lille',     '23 rue ALG',     'oui' ),
  ( 80, 'MONNIER-COLLIN', 'Laure',       {d '1985-11-01' },  'Feminin',   '0766584820',   'lauremc@mail.xyz',     'Brest',     '32 rue RR',      'oui' ),

  ( 81, 'PINTO',      'Dorothy',    {d '1997-06-17' },  'Feminin',   '0766584820',   'dorothyp@mail.xyz',   'Limoges',     '22 avenue GHR',  'oui' ),
  ( 82, 'KEMFANG',    'Esther',     {d '1990-08-30' },  'Feminin',   '0766584820',   'estherk@mail.xyz',    'Rouen',       '11 avenue KHK',  'oui' ),
  ( 83, 'DUMONT',     'Elise',      {d '1999-10-02' },  'Feminin',   '0766584820',   'elised@mail.xyz',     'Montpellier', '46 rue VB',      'oui' ),
  ( 84, 'BOURGEOIS',  'Daisy',      {d '1990-03-17' },  'Feminin',   '0766584820',   'daisyb@mail.xyz',     'Paris',       '12 rue ALG',     'non' ),
  ( 85, 'BONNEAU',    'Daniella',   {d '1985-12-16' },  'Feminin',   '0766584820',   'daniellab@mail.xyz',  'Brest',       '13 rue RR',      'oui' ),
  
  ( 86, 'MILLER',     'Lucie',      {d '1996-06-27' },  'Feminin',   '0766584820',   'luciem@mail.xyz',     'Reims',          '110 avenue GHR',   'oui' ),
  ( 87, 'LASTAR',     'Anna',       {d '1983-07-12' },  'Feminin',   '0766584820',   'annal@mail.xyz',      'Reims',          '12 avenue KHK',    'oui' ),
  ( 88, 'DUPUY',      'Amber',      {d '1990-10-12' },  'Feminin',   '0766584820',   'amberd@mail.xyz',     'Saint-Etienne',  '126 rue VB',       'oui' ),
  ( 89, 'DELATTRE',   'Kimberly',   {d '2001-06-07' },  'Feminin',   '0766584820',   'kimberlyd@mail.xyz',  'Paris',          '110 rue ALG',      'non' ),
  ( 90, 'BOURDON',    'Lucile',     {d '1985-11-01' },  'Feminin',   '0766584820',   'lucileb@mail.xyz',    'Saint-Etienne',  '11 rue RR',        'oui' ),

  ( 91, 'CARON',    'Claudia',    {d '2001-05-17' },  'Feminin',   '0766584820',   'claudiac@mail.xyz', 'Le Havre',     '1 avenue GHR',   'oui' ),
  ( 92, 'DELAGE',   'Marine',     {d '2000-07-30' },  'Feminin',   '0766584820',   'marined@mail.xyz',  'Toulon',       '18 avenue KHK',  'oui' ),
  ( 93, 'BIDOT',    'Lilou',      {d '2000-11-02' },  'Feminin',   '0766584820',   'liloub@mail.xyz',   'Montpellier',  '16 rue VB',      'oui' ),
  ( 94, 'PAYET',    'Céline',     {d '1990-01-17' },  'Feminin',   '0766584820',   'celinep@mail.xyz',  'Paris',        '10 rue ALG',     'oui' ),
  ( 95, 'DEVAUX',   'Océane',     {d '1985-11-16' },  'Feminin',   '0766584820',   'oceaned@mail.xyz',  'Rennes',       '32 rue RR',      'oui' ),
  
  ( 96,   'SALMONN',  'Alice',      {d '2000-06-27' },  'Feminin',   '0766584820',   'alices@mail.xyz',       'Limoges',     '76 avenue GHR',  'oui' ),
  ( 97,   'ROLLAND',  'Elisabeth',  {d '1983-07-12' },  'Feminin',   '0766584820',   'elisabethr@mail.xyz',   'Limoges',     '71 avenue KHK',  'non' ),
  ( 98,   'DUPRE',    'Sarah',      {d '1972-10-12' },  'Feminin',   '0766584820',   'sarahd@mail.xyz',       'Limoges',     '16 rue VB',      'oui' ),
  ( 99,   'TORRES',   'Sandrine',   {d '1990-06-07' },  'Feminin',   '0766584820',   'sandrinet@mail.xyz',    'Limoges',     '21 rue ALG',     'non' ),
  ( 100,  'RIBEIRA',  'Kristy',     {d '1985-11-01' },  'Feminin',   '0766584820',   'kristyr@mail.xyz',      'Paris',       '33 rue RR',      'oui' );

ALTER TABLE Donneur ALTER COLUMN id_donneur RESTART WITH 101;

-- Site_de_collecte
  
INSERT INTO Site_de_collecte (id_site, ville, nbr_lits, adresse ) VALUES 
  (1, 'Limoges',  22,   '19 avenue ART' ),
  (2, 'Bordeaux', 22,   '20 avenue HT' ),
  (3, 'Nantes',   19,   '2 avenue MPN' ),
  (4, 'Paris',    29,   '92 avenue KKT' ),
  (5, 'Toulouse', 18,   '17 impasse StA' );

ALTER TABLE Site_de_collecte ALTER COLUMN id_site RESTART WITH 6;

-- Connexion

INSERT INTO Connexion (id_connexion, login,  motDePasse, id_personnel) VALUES 
  ( 1, 'cardinal',  'cardinal',   2 ),
  ( 2, 'nawel',     'nawel',      3 );

ALTER TABLE Connexion ALTER COLUMN id_connexion RESTART WITH 3;


-- Roles

INSERT INTO Role (id_role, role, id_connexion) VALUES 
  ( 1, 'GESTIONNAIRE',  1 ),
  ( 2, 'SECRETAIRE',    2 );

ALTER TABLE Role ALTER COLUMN id_role RESTART WITH 3;


-- dossierMedical

INSERT INTO dossierMedical (id_dossier, groupe_sanguin, rhesus, poids, inaptitude_temporaire, id_donneur ) VALUES 
  ( 1, 'A',     'positif',  85,   'A fait un don récemment.', 1 ),
  ( 2, 'AB',    'positif',  57,   NULL, 2 ),
  ( 3, 'O',     'negatif',  89,   'A fait un don récemment.', 3 ),
  ( 4, 'O',     'positif',  44,   NULL, 4 ),
  ( 5, 'B',     'positif',  60,   NULL, 5 ),

  ( 6,  'A',    'positif',  85,   'A fait un don récemment.', 6 ),
  ( 7,  'AB',   'positif',  77,   NULL, 7 ),
  ( 8,  'A',    'positif',  90,   'A fait un don récemment.', 8 ),
  ( 9,  'A',    'positif',  78,   NULL, 9 ),
  ( 10, 'A',    'positif',  89,   NULL, 10 ),

  ( 11, 'O',    'positif',  85,   'A fait un don récemment.', 11 ),
  ( 12, 'AB',   'negatif',  78,   NULL, 12 ),
  ( 13, 'O',    'positif',  89,   'A fait un don récemment.', 13 ),
  ( 14, 'O',    'negatif',  69,   NULL, 14 ),
  ( 15, 'B',    'positif',  78,   NULL, 15 ),

  ( 16, 'AB',   'positif',  85,   'A fait un don récemment.', 16 ),
  ( 17, 'O',    'positif',  80,   NULL, 17 ),
  ( 18, 'O',    'negatif',  89,   'A fait un don récemment.', 18 ),
  ( 19, 'O',    'positif',  90,   NULL, 19 ),
  ( 20, 'B',    'positif',  97,   NULL, 20 ),

  ( 21, 'B',    'positif',  96,   'A fait un don récemment.', 21 ),
  ( 22, 'B',    'positif',  96,   NULL, 22 ),
  ( 23, 'A',    'positif',  89,   'A fait un don récemment.', 23 ),
  ( 24, 'A',    'positif',  98,   NULL, 24 ),
  ( 25, 'A',    'positif',  99,   NULL, 25 ),

  ( 26, 'AB',   'positif',  67,   'A fait un don récemment.', 26 ),
  ( 27, 'AB',   'positif',  98,   NULL, 27 ),
  ( 28, 'A',    'negatif',  78,   'A fait un don récemment.', 28 ),
  ( 29, 'O',    'negatif',  91,   NULL, 29 ),
  ( 30, 'B',    'positif',  80,   NULL, 30 ),

  ( 31, 'O',    'positif',  85,   'A fait un don récemment.', 31 ),
  ( 32, 'A',    'positif',  80,   NULL, 32 ),
  ( 33, 'O',    'positif',  89,   'A fait un don récemment.', 33 ),
  ( 34, 'O',    'negatif',  76,   NULL, 34 ),
  ( 35, 'AB',   'positif',  77,   NULL, 35 ),

  ( 36, 'AB',   'positif',  77,   'A fait un don récemment.', 36 ),
  ( 37, 'AB',   'negatif',  85,   NULL, 37 ),
  ( 38, 'AB',   'negatif',  89,   'A fait un don récemment.', 38 ),
  ( 39, 'O',    'negatif',  86,   NULL, 39 ),
  ( 40, 'B',    'positif',  89,   NULL, 40 ),
  
  ( 41, 'A',    'positif',  85,   'A fait un don récemment.', 41 ),
  ( 42, 'AB',   'positif',  78,   NULL, 42 ),
  ( 43, 'A',    'negatif',  89,   'A fait un don récemment.', 43 ),
  ( 44, 'A',    'positif',  76,   NULL, 44 ),
  ( 45, 'B',    'positif',  89,   NULL, 45 ),

  ( 46, 'A',    'positif',  86,   'A fait un don récemment.', 46 ),
  ( 47, 'AB',   'negatif',  65,   NULL, 47 ),
  ( 48, 'A',    'positif',  89,   'A fait un don récemment.', 48 ),
  ( 49, 'B',    'positif',  88,   NULL, 49 ),
  ( 50, 'O',    'positif',  88,   NULL, 50 ),
  
  ( 51, 'A',    'positif',  85,   'A fait un don récemment.', 51 ),
  ( 52, 'O',    'negatif',  57,   NULL, 52 ),
  ( 53, 'AB',   'positif',  66,   'A fait un don récemment.', 53 ),
  ( 54, 'O',    'positif',  44,   NULL, 54 ),
  ( 55, 'B',    'positif',  60,   NULL, 55 ),

  ( 56, 'O',    'positif',  45,   'A fait un don récemment.', 56 ),
  ( 57, 'O',    'positif',  44,   NULL, 57 ),
  ( 58, 'O',    'positif',  54,   'A fait un don récemment.', 58 ),
  ( 59, 'O',    'negatif',  50,   NULL, 59 ),
  ( 60, 'O',    'positif',  60,   NULL, 60 ),
  
  ( 61, 'O',    'positif',  77,   'A fait un don récemment.', 61 ),
  ( 62, 'A',    'negatif',  57,   NULL, 62 ),
  ( 63, 'O',    'positif',  89,   'A fait un don récemment.', 63 ),
  ( 64, 'O',    'positif',  44,   NULL, 64 ),
  ( 65, 'A',    'positif',  45,   NULL, 65 ),

  ( 66, 'A',    'positif',  44,   NULL, 66 ),
  ( 67, 'AB',   'negatif',  57,   NULL, 67 ),
  ( 68, 'AB',   'positif',  89,   'A fait un don récemment.', 68 ),
  ( 69, 'AB',   'positif',  44,   NULL, 69 ),
  ( 70, 'B',    'positif',  57,   NULL, 70 ),
  
  ( 71, 'A',    'positif',  58,   NULL, 71 ),
  ( 72, 'B',    'positif',  57,   NULL, 72 ),
  ( 73, 'A',    'positif',  59,   'A fait un don récemment.', 73 ),
  ( 74, 'A',    'negatif',  59,   NULL, 74 ),
  ( 75, 'B',    'positif',  50,   NULL, 75 ),

  ( 76, 'A',    'positif',  89,   'A fait un don récemment.', 76 ),
  ( 77, 'A',    'negatif',  58,   NULL, 77 ),
  ( 78, 'O',    'positif',  69,   'A fait un don récemment.', 78 ),
  ( 79, 'B',    'positif',  70,   NULL, 79 ),
  ( 80, 'B',    'positif',  60,   NULL, 80 ),
  
  ( 81, 'B',    'positif',  70,   'A fait un don récemment.', 81 ),
  ( 82, 'AB',   'positif',  56,   NULL, 82 ),
  ( 83, 'AB',   'positif',  80,   'A fait un don récemment.', 83 ),
  ( 84, 'O',    'negatif',  56,   NULL, 84 ),
  ( 85, 'B',    'positif',  60,   NULL, 85 ),

  ( 86, 'B',    'positif',  67,   NULL, 86 ),
  ( 87, 'AB',   'positif',  56,   NULL, 87 ),
  ( 88, 'O',    'positif',  55,   NULL, 88 ),
  ( 89, 'A',    'negatif',  66,   NULL, 89 ),
  ( 90, 'B',    'positif',  77,   NULL, 90 ),
  
  ( 91, 'O',    'positif',  65,   NULL, 91 ),
  ( 92, 'AB',   'negatif',  57,   NULL, 92 ),
  ( 93, 'AB',   'negatif',  78,   NULL, 93 ),
  ( 94, 'O',    'negatif',  70,   NULL, 94 ),
  ( 95, 'A',    'positif',  60,   NULL, 95 ),

  ( 96, 'A',    'positif',  66,   NULL, 96 ),
  ( 97, 'AB',   'positif',  67,   NULL, 97 ),
  ( 98, 'A',    'negatif',  89,   NULL, 98 ),
  ( 99, 'A',    'negatif',  68,   NULL, 99 ),
  ( 100, 'B',   'positif',  60,   NULL, 100 );

ALTER TABLE dossierMedical ALTER COLUMN id_dossier RESTART WITH 101;


-- Materiel

INSERT INTO Materiel (id_materiel, nom_materiel, quantite_materiel) VALUES 
  (1,   'Seringue', 500 ),
  (2,   'Gants', 100),
  (3,   'Cache-nez', 600);

ALTER TABLE Materiel ALTER COLUMN id_materiel RESTART WITH 4;


-- Collecte

INSERT INTO Collecte (id_collecte, qte_collation, date_debut, date_fin, nbre_infirmiers, nbre_medecins, nbre_secretaire, nbre_agents_collation, horaire_debut, horaire_fin, id_site) VALUES 
  ( 1, 100, {d '2022-01-03'}, {d '2022-01-31'}, 1, 1, 1, 1, '8:00:00', '17:30:00', 1 ),
  ( 2, 200, {d '2022-01-03'}, {d '2022-01-31'}, 5, 2, 4, 2, '9:00:00', '17:30:00', 3 ),
  ( 3, 400, {d '2022-01-03'}, {d '2022-01-31'}, 7, 2, 1, 3, '9:00:00', '17:30:00', 2 );

ALTER TABLE Collecte ALTER COLUMN id_collecte RESTART WITH 4;


-- RDV

INSERT INTO RDV ( id_rdv, heure_rdv, prise_de_sang, date_rdv, qte_sang_donnee, id_collecte, id_donneur ) VALUES 
  ( 1, '08:00:00', 'oui', {d '2022-01-04' },  450,  1,  1),
  ( 2, '09:00:00', 'oui', {d '2022-01-04' },  420,  2,  2),
  ( 3, '15:00:00', 'oui', {d '2022-01-13' },  455,  1,  3),
  ( 4, '08:00:00', 'oui', {d '2022-01-14' },  480,  2,  4),
  ( 5, '08:30:00', 'oui', {d '2022-01-05' },  460,  2,  5),

  ( 6, '08:00:00', 'oui', {d '2022-01-14' },  450,  1,  6),
  ( 7, '09:00:00', 'oui', {d '2022-01-24' },  420,  2,  7),
  ( 8, '15:00:00', 'oui', {d '2022-01-13' },  455,  1,  8),
  ( 9, '08:00:00', 'oui', {d '2022-01-25' },  480,  2,  9),
  ( 10, '10:30:00', 'oui', {d '2022-01-20' },  425, 2,  10),

  ( 11, '11:00:00', 'oui', {d '2022-01-04' },  450,  1,  11),
  ( 12, '09:30:00', 'oui', {d '2022-01-14' },  440,  2,  12),
  ( 13, '15:00:00', 'oui', {d '2022-01-13' },  455,  1,  13),
  ( 14, '08:30:00', 'oui', {d '2022-01-12' },  480,  2,  14),
  ( 15, '08:30:00', 'oui', {d '2022-01-05' },  425,  2,  15),

  ( 16, '08:30:00', 'oui', {d '2022-01-24' },  450,  1,  16),
  ( 17, '09:30:00', 'oui', {d '2022-01-20' },  465,  2,  17),
  ( 18, '15:00:00', 'oui', {d '2022-01-13' },  450,  1,  18),
  ( 19, '10:30:00', 'oui', {d '2022-01-14' },  480,  2,  19),
  ( 20, '11:30:00', 'oui', {d '2022-01-28' },  425,  2,  20),

  ( 21, '15:00:00', 'oui', {d '2022-01-10' },  450,  1,  21),
  ( 22, '09:00:00', 'oui', {d '2022-01-10' },  420,  2,  22),
  ( 23, '15:00:00', 'oui', {d '2022-01-13' },  450,  1,  23),
  ( 24, '13:00:00', 'oui', {d '2022-01-05' },  480,  2,  24),
  ( 25, '15:30:00', 'oui', {d '2022-01-20' },  460,  2,  25),

  ( 26, '12:00:00', 'oui', {d '2022-01-04' },  450,  1,  50),
  ( 27, '17:30:00', 'oui', {d '2022-01-04' },  420,  2,  51),
  ( 28, '15:00:00', 'oui', {d '2022-01-13' },  450,  1,  52),
  ( 29, '08:00:00', 'oui', {d '2022-01-10' },  480,  2,  53),
  ( 30, '08:30:00', 'oui', {d '2022-01-05' },  460,  2,  54),

  ( 31, '11:00:00', 'oui', {d '2022-01-20' },  450,  1,  55),
  ( 32, '16:30:00', 'oui', {d '2022-01-04' },  450,  2,  56),
  ( 33, '16:00:00', 'oui', {d '2022-01-13' },  450,  1,  57),
  ( 34, '11:00:00', 'oui', {d '2022-01-17' },  480,  2,  58),
  ( 35, '08:30:00', 'oui', {d '2022-01-05' },  425,  2,  59),

  ( 36, '10:00:00', 'oui', {d '2022-01-04' },  450,  1,  60),
  ( 37, '15:00:00', 'oui', {d '2022-01-17' },  455,  2,  61),
  ( 38, '15:30:00', 'oui', {d '2022-01-13' },  450,  1,  62),
  ( 39, '10:30:00', 'oui', {d '2022-01-05' },  480,  2,  63),
  ( 40, '08:30:00', 'oui', {d '2022-01-19' },  425,  2,  64),

  ( 41, '15:30:00', 'oui', {d '2022-01-04' },  450,  1,  65),
  ( 42, '11:00:00', 'oui', {d '2022-01-17' },  450,  2,  66),
  ( 43, '15:00:00', 'oui', {d '2022-01-13' },  450,  1,  67),
  ( 44, '11:00:00', 'oui', {d '2022-01-05' },  480,  2,  68),
  ( 45, '13:30:00', 'oui', {d '2022-01-05' },  425,  2,  69),

  ( 46, '08:00:00', 'oui', {d '2022-01-19' },  450,  1,  70),
  ( 47, '09:00:00', 'oui', {d '2022-01-18' },  450,  2,  71),
  ( 48, '15:00:00', 'oui', {d '2022-01-13' },  450,  1,  72),
  ( 49, '08:30:00', 'oui', {d '2022-01-19' },  480,  2,  73),
  ( 50, '13:30:00', 'oui', {d '2022-01-05' },  425,  2,  74);

ALTER TABLE RDV ALTER COLUMN id_rdv RESTART WITH 51;


-- personnelDeCollecte

 INSERT INTO personnelDeCollecte (id_personnel, id_collecte) VALUES 
  (2, 2 ),
  (3, 1),
  (4, 2),
  (5, 1);



-- materielDeCollecte
 
INSERT INTO materielDeCollecte (id_collecte, id_materiel, quantite) VALUES 
  (1, 2, 100),
  (1, 1, 125),
  (1, 3, 200);

