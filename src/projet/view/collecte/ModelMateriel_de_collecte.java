package projet.view.collecte;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoMateriel;
import projet.dao.DaoMaterieldecollecte;
import projet.data.Materiel;
import projet.data.Materieldecollecte;


public class ModelMateriel_de_collecte  {
	
	
	// Données observables 
	
	private final ObservableList<Materieldecollecte> liste = FXCollections.observableArrayList(); 
	
	private final Materieldecollecte	courant = new Materieldecollecte();
	//private final Materieldecollecte	courant1 = new Materieldecollecte();
	
	// Autres champs
	
	private Materieldecollecte		selection;

	@Inject
	private IMapper			mapper;
	
    @Inject
	private DaoMaterieldecollecte	daoMaterieldecollecte;
	
	// Getters & Setters
	
	public ObservableList<Materieldecollecte> getListe() {
		return liste;
	}
	
	public Materieldecollecte getCourant() {
		return courant;
	}
	
	public Materieldecollecte getSelection() {
		return selection;
	}
	
	public void setSelection( Materieldecollecte selection ) {
		if ( selection == null ) {
			this.selection = new Materieldecollecte();
		} else {
			this.selection = daoMaterieldecollecte.retrouver( selection );
		}
	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoMaterieldecollecte.listerTout() );
 	}

	
	public void actualiserCourant() {
		mapper.update( courant, selection );
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getCollecte() == null) {
			message.append( "\n La collecte ne doit pas être vide." );
		}
		
		if( courant.getMateriel() == null) {
			message.append( "\n Le materiel ne doit pas être vide." );
		}
		
		if( courant.getQuantite() == null) {
			message.append( "\n La quantité ne doit pas être vide." );
		} else if( courant.getQuantite()==0) {
			message.append( "\n La quantité de matétiel est 0" );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getMateriel() == null ) {
			// Insertion
			daoMaterieldecollecte.inserer( courant );
		} else {
			// modficiation
			daoMaterieldecollecte.modifier( courant );
		}
	}
	
	
	public void supprimer( Materieldecollecte item ) {
		
		// Vérifie l'abence de personnes rattachées à la catégorie
		daoMaterieldecollecte.supprimer( item );
		selection = UtilFX.findNext( liste, item );
	}


	
}