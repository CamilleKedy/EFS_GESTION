package projet.view;

import jfox.javafx.view.IEnumView;
import jfox.javafx.view.View;


public enum EnumView implements IEnumView {

	
	// Valeurs
	

	Info				( "connexion/ViewInfo.fxml" ),
	Connexion			( "connexion/ViewConnexionForm.fxml" ),
	AccueilSecretaire	( "systeme/ViewAccueilSecretaire.fxml" ),
	AccueilGestionnaire	( "systeme/ViewAccueilGestionnaire.fxml" ),
	CompteListe			( "compte/ViewCompteListe.fxml" ),
	CompteForm			( "compte/ViewCompteForm.fxml" ),
	CategorieListe		( "personne/ViewCategorieListe.fxml" ),
	CategorieForm		( "personne/ViewCategorieForm.fxml" ),
	PersonneListe		( "personne/ViewPersonneListe.fxml" ),
	PersonneForm		( "personne/ViewPersonneForm.fxml" ),
	PersonneEtatParCategorie( "personne/ViewPersonneEtatParCategorie.fxml" ),
	MemoListe			( "memo/ViewMemoListe.fxml" ),
	MemoForm			( "memo/ViewMemoForm.fxml" ),
	CollecteListe		( "collecte/ViewCollecteListe.fxml" ),
	CollecteForm		( "collecte/ViewCollecteForm.fxml" ),
	MemoAjoutPersonnes	( "memo/ViewMemoAjoutPersonnes.fxml" ),
	ServiceListe		( "service/ViewServiceListe.fxml" ),
	ServiceForm			( "service/ViewServiceForm.fxml" ),
	TestDaoCategorie	( "test/ViewTestDaoCategorie.fxml" ),
	TestDaoMemo			( "test/ViewTestDaoMemo.fxml" ),
	TestDaoService		( "test/ViewTestDaoService.fxml" ),
	EtatPersonneParCategorie1	( "personne/ViewEtatPersonneParCategorie1.fxml" ),
	EtatPersonneParCategorie2	( "personne/ViewEtatPersonneParCategorie2.fxml" ), 
	DonneurForm 		( "donneur/ViewDonneurForm.fxml" ),
	DonneurListe		( "donneur/ViewDonneurListe.fxml" ),
	Site_de_collecteListe		("site_de_collecte/ViewSite_de_collecteListe.fxml"),
	Site_de_collecteListeChoix		("site_de_collecte/ViewSite_de_collecteListeChoix.fxml"),
	ChoixSite			("communication/ViewChoixSite.fxml"),
	Site_de_collecteForm		("site_de_collecte/ViewSite_de_collecteForm.fxml"),
	PersonnelListe		("personnel/ViewPersonnelListe.fxml"),
	PersonnelForm		("personnel/ViewPersonnelForm.fxml"),
	RdvListe			("rdv/ViewRdvListe.fxml"),
	RdvForm				("rdv/ViewRdvForm.fxml"),

	;

	
	// Champs
	
	private final View	view;

	
	// Constructeurs
	
	EnumView( String path, boolean flagReuse ) {
		view = new View(path, flagReuse);
	}
	
	EnumView( String path ) {
		view = new View(path);
	}

	
	// Getters & setters
	
	@Override
	public View getView() {
		return view;
	}
}
