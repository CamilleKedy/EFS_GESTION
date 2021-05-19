package projet.view.personnel;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoPersonnel;
import projet.dao.DaoPersonne;
import projet.data.Personnel;


public class ModelPersonnel  {
	
	
	// Données observables 
	
	private final ObservableList<Personnel> liste = FXCollections.observableArrayList(); 
	
	private final Personnel	courant = new Personnel();

	
	// Autres champs
	
	private Personnel		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoPersonnel	daoPersonnel;

	
	
	// Getters & Setters
	
	public ObservableList<Personnel> getListe() {
		return liste;
	}
	
	public Personnel getCourant() {
		return courant;
	}
	
	public Personnel getSelection() {
		return selection;
	}
	
	public void setSelection( Personnel selection ) {
		if ( selection == null ) {
			this.selection = new Personnel();
		} else {
			this.selection = daoPersonnel.retrouver( selection.getId() );
		}
	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoPersonnel.listerTout() );

 	}
	
	public void actualiserListeParProfession(String Profession) {
		liste.setAll( daoPersonnel.listerParProfession(Profession) );

 	}

	
	public void actualiserCourant() {
		mapper.update( courant, selection );
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
			message.append( "\nLe nom ne doit pas être vide." );
		} else  if ( courant.getNom().length()> 50 ) {
			message.append( "\nLe nom est trop long : 50 maxi." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoPersonnel.inserer( courant ) );
		} else {
			// modficiation
			daoPersonnel.modifier( courant );
		}
	}
	
	
	public void supprimer( Personnel item ) {	
		daoPersonnel.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}
	
}