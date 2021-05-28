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


public class ModelMateriel  {
	
	
	// Données observables 
	
	private final ObservableList<Materiel> liste = FXCollections.observableArrayList(); 
	
	private final Materiel	courant = new Materiel();
	//private final Materieldecollecte	courant1 = new Materieldecollecte();
	
	// Autres champs
	
	private Materiel		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoMateriel	daoMateriel;
	
    @Inject
	private DaoMaterieldecollecte	daoMaterieldecollecte;
	
	// Getters & Setters
	
	public ObservableList<Materiel> getListe() {
		return liste;
	}
	
	public Materiel getCourant() {
		return courant;
	}
	
	public Materiel getSelection() {
		return selection;
	}
	
	public void setSelection( Materiel selection ) {
		if ( selection == null ) {
			this.selection = new Materiel();
		} else {
			this.selection = daoMateriel.retrouver( selection.getId_materiel() );
		}
	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoMateriel.listerTout() );
 	}

	
	public void actualiserCourant() {
		mapper.update( courant, selection );
	}
	
	
	public void diminuer(int quantite) {
		courant.setQuantite_materiel(courant.getQuantite_materiel()- quantite);
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getNom_materiel() == null || courant.getNom_materiel().isEmpty() ) {
			message.append( "\n Le nom ne doit pas être vide." );
		} else  if ( courant.getNom_materiel().length()> 100 ) {
			message.append( "\nLe nom  est trop long : 100 maxi." );
		}
		
		if( courant.getQuantite_materiel() == null) {
			message.append( "\n La quantité ne doit pas être vide." );
		} else if( courant.getQuantite_materiel()==0) {
			message.append( "\n La quantité de matétiel est 0" );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId_materiel() == null ) {
			// Insertion
			selection.setId_materiel( daoMateriel.inserer( courant ) );
		} else {
			// modficiation
			daoMateriel.modifier( courant );
		}
	}
	
	
	public void supprimer( Materiel item ) {
		
		// Vérifie l'abence de personnes rattachées à la catégorie
		daoMateriel.supprimer( item.getId_materiel() );
		selection = UtilFX.findNext( liste, item );
	}

	public void ModifierQuantite(Materiel materiel, int delta) {
		for(Materiel m : liste) {
			if(m.equals(materiel)) {
				m.setQuantite_materiel(m.getQuantite_materiel() + delta);
			}
		}
	}
	
}