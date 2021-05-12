package projet.view.donneur;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoDonneur;
import projet.data.Donneur;


public class ModelDonneur {
	
	
	// Données observables 
	
//	private Property<String> niveau = new SimpleObjectProperty<>("non");
//	private final ObservableList<String> listeChoixCarte = FXCollections.observableArrayList();
	private final ObservableList<Donneur> liste = FXCollections.observableArrayList();
	
	private final Donneur	courant = new Donneur();
	
	
	// Autres champs
	
	private Donneur		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoDonneur		daoDonneur;
	
/*	
    @PostConstruct
	public void init() {
    	listeChoixCarte.addAll("oui", "non");
    }
*/    
    
	// Getters & Setters
	
	public ObservableList<Donneur> getListe() {
		return liste;
	}
	
	public Donneur getCourant() {
		return courant;
	}
	
	public Donneur getSelection() {
		return selection;
	}
	
	public void setSelection( Donneur selection ) {
		if ( selection == null ) {
			this.selection = new Donneur();
		} else {
			this.selection = daoDonneur.retrouver( selection.getId() );
		}
	}
	
/*	
	public final Property<String> niveauProperty() {
		return this.niveau;
	}
	
	public final String getNiveau() {
		return this.niveauProperty().getValue();
	}
	

	public final void setNiveau(final String niveau) {
		this.niveauProperty().setValue(niveau);
	}
*/
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoDonneur.listerTout() );
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
			message.append( "\nLe nom est trop long." );
		}

		if( courant.getPrenom() == null || courant.getPrenom().isEmpty() ) {
			message.append( "\nLe prénom ne doit pas être vide." );
		} else if ( courant.getPrenom().length()> 50 ) {
			message.append( "\nLe prénom est trop long." );
		}

		if( courant.getVille() == null || courant.getVille().isEmpty() ) {
			message.append( "\nLa ville ne doit pas être vide." );
		} else if ( courant.getVille().length()> 50 ) {
			message.append( "\nLa ville est trop longue." );
		}
		
		if( courant.getAdresse() == null || courant.getAdresse().isEmpty() ) {
			message.append( "\nL'adresse ne doit pas être vide." );
		} else if ( courant.getAdresse().length()> 50 ) {
			message.append( "\nL'adresse est trop longue." );
		}
		
		
		if(courant.getDateNaissance() != null && courant.getDateNaissance().isAfter(LocalDate.of(2022, 1, 3))) {
			message.append( "\nERREUR Date postérieure au 03/01/2022." );
		}
		
		if( courant.getDemandeCarte() == null ) {
			message.append( "\nLa demande d'une carte par le donneur doit être indiquée." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}

		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoDonneur.inserer( courant ) );
		} else {
			// modficiation
			daoDonneur.modifier( courant );
		}
	}
	

	public void supprimer( Donneur item ) {
		daoDonneur.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}



	
	

	
}
