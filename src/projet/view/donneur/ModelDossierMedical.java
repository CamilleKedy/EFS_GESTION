package projet.view.donneur;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoDossierMedical;
import projet.data.DossierMedical;


public class ModelDossierMedical {
	
	
	// Données observables 
	
	private final ObservableList<String> listeGroupeSanguin = FXCollections.observableArrayList();
	private final ObservableList<String> listeRhesus = FXCollections.observableArrayList();
	private final ObservableList<DossierMedical> liste = FXCollections.observableArrayList();
	
	private final DossierMedical	courant = new DossierMedical();
	
	
	// Autres champs
	
	private DossierMedical		selection = new DossierMedical();

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoDossierMedical		daoDossierMedical;
	
	
    @PostConstruct
	public void init() {
    	listeGroupeSanguin.addAll("A", "B", "AB", "O");
    	listeRhesus.addAll("positif", "negatif");
    }
    
    
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoDossierMedical.listerTout() );
	}

	
	public void actualiserCourant() {
		mapper.update( courant, selection );
	}

	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
		if( courant.getPoids() <= 0 ) {
			message.append( "\nLe poids ne peut être nul ou négatif." );
		}

		if( courant.getDonneur().getId() <= 0 ) {
			message.append( "\nL'Id ne peut être nul ou négatif." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}

		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoDossierMedical.inserer( courant ) );
		} else {
			// modficiation
			daoDossierMedical.modifier( courant );
		}
	}
	

	public void supprimer( DossierMedical item ) {
		daoDossierMedical.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}


	

	// Getters & Setters

	public DossierMedical getSelection() {
		return selection;
	}




	public void setSelection(DossierMedical selection) {
		if ( selection == null ) {
			this.selection = new DossierMedical();
		} else {
			this.selection = daoDossierMedical.retrouver( selection.getId() );
		}
	}

	public DossierMedical getCourant() {
		return courant;
	}


	public ObservableList<String> getListeGroupeSanguin() {
		return listeGroupeSanguin;
	}


	public ObservableList<String> getListeRhesus() {
		return listeRhesus;
	}


	public ObservableList<DossierMedical> getListe() {
		return liste;
	}
	
	
	
}
