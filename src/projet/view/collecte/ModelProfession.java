package projet.view.collecte;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoProfession;
import projet.dao.DaoPersonne;
import projet.data.Profession;


public class ModelProfession  {
	
	
	// Données observables 
	
	private final ObservableList<Profession> liste = FXCollections.observableArrayList(); 
	
	private final Profession	courant = new Profession();

	
	// Autres champs
	
	private Profession		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoProfession	daoProfession;

	
	
	// Getters & Setters
	
	public ObservableList<Profession> getListe() {
		return liste;
	}
	
	public Profession getCourant() {
		return courant;
	}
	
	public Profession getSelection() {
		return selection;
	}
	
	public void setSelection( Profession selection ) {
		if ( selection == null ) {
			this.selection = new Profession();
		} else {
			this.selection = daoProfession.retrouver( selection.getId() );
		}
	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoProfession.listerNonGestionnaire() );
		
 	}

	
	public void actualiserCourant() {
		mapper.update( courant, selection );
	}
	
	/*
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getLibelle() == null || courant.getLibelle().isEmpty() ) {
			message.append( "\nLe libellé ne doit pas être vide." );
		} else  if ( courant.getLibelle().length()> 25 ) {
			message.append( "\nLe libellé est trop long : 25 maxi." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoProfession.inserer( courant ) );
		} else {
			// modficiation
			daoProfession.modifier( courant );
		}
	}
	
	
	public void supprimer( Profession item ) {
		
		// Vérifie l'abence de personnes rattachées à la catégorie
		if ( daoPersonne.compterPourProfession( item.getId() ) != 0 ) {
			throw new ExceptionValidation( "Des personnes sont rattachées à cette catégorie.." ) ;
		}
		
		daoProfession.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}
	*/
}
