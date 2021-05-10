package projet.view.collecte;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoCollecte;
import projet.dao.DaoPersonne;
import projet.dao.DaoSite_de_collecte;
import projet.data.Collecte;
import projet.data.Collecte;
import projet.data.Personne;
import projet.view.systeme.ModelConfig;


public class ModelCollecte  {
	
	
	// Données observables 
	
	private final ObservableList<Collecte> liste = FXCollections.observableArrayList(); 
	
	private final Collecte	courant = new Collecte();
	
	/*private final ObservableList<Personne> personnesPourDialogAjout = FXCollections.observableArrayList();
	*/

	
	// Autres champs
	
	private Collecte		selection;

	@Inject
	private IMapper		mapper;
    @Inject
	private DaoCollecte		daoCollecte;

    /*@Inject
    private DaoPersonne	daoPersonne;
    @Inject
    private ModelConfig	modelConfig;
    
	private boolean flagModifSchema;*/
    
	
	// Initialisations
	
	
	// Getters & Setters
	
	public ObservableList<Collecte> getListe() {
		return liste;
	}
	
	public Collecte getCourant() {
		return courant;
	}
	
	/*public ObservableList<Personne> getPersonnesPourDialogAjout() {
		return personnesPourDialogAjout;
	}*/
	
	public Collecte getSelection() {
		return selection;
	}
	
	public void setSelection( Collecte selection ) {
		if ( selection == null ) {
			this.selection = new Collecte();
		} else {
			this.selection = daoCollecte.retrouver( selection.getId_collecte() );
		}
	}
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoCollecte.listerTout() );
 	}
	
	
	public void actualiserCourant() {
		mapper.update( courant, selection );
	}
	
	/*
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getTitre() == null || courant.getTitre().isEmpty() ) {
			message.append( "\nLe titre ne doit pas être vide." );
		} else  if ( courant.getTitre().length()> 50 ) {
			message.append( "\nLe titre est trop long : 50 maxi." );
		}

		if( courant.getEffectif() != null  ) {
			if( courant.getEffectif() < 0  ) {
				message.append( "\nL'effectif ne peut pas être inférieur à zéro." );
			} else  if ( courant.getEffectif() > 1000 ) {
				message.append( "\nEffectif trop grand : 1000 maxi." );
			}
		}

		if( courant.getBudget() != null  ) {
			if( courant.getBudget() < 0  ) {
				message.append( "\nLe budget ne peut pas être inférieur à zéro." );
			} else  if ( courant.getBudget() > 1000000 ) {
				message.append( "\nBudget trop grand : 1 000 000 maxi." );
			}
		}

		if( courant.getEcheance() != null  ) {
			if( courant.getEcheance().isBefore( LocalDate.of(2000, 1, 1) ) 
					|| courant.getEcheance().isAfter( LocalDate.of(2099, 12, 31) ) ) {
				message.append( "\nLa date d'échéance doit être comprise entre le 01/01/2000 et le 31/12/2099." );
			}
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoCollecte.inserer( courant ) );
		} else {
			// modficiation
			daoCollecte.modifier( courant );
		}
		
		if ( flagModifSchema ) {
			if (schema.getValue() == null) {
				getCheminSchemaCourant().delete();
			} else {
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(schema.getValue(), null), "JPG", getCheminSchemaCourant());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} 
		}
	}
	
	*/
	public void supprimer( Collecte item ) {
		daoCollecte.supprimer( item.getId_collecte() );
		selection = UtilFX.findNext( liste, item );
	}
	/*
	
	public void supprimerPersonnes( List<Personne> items ) {
		courant.getPersonnes().removeAll( items );
	}
	
	
	public void ajouterPersonnes( List<Personne> items ) {
		courant.getPersonnes().addAll( items );
	}
	*/
	
	// Métodes auxiliaires
	
	
}
