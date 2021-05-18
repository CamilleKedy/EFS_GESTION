package projet.view.site_collecte;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projet.commun.IMapper;
import projet.dao.DaoSite_de_collecte;
import projet.data.Site_de_collecte;


public class ModelSiteCollecte  {
	
	
	// Données observables 
	
	private final ObservableList<Site_de_collecte> liste = FXCollections.observableArrayList(); 
	
	private final Site_de_collecte	courant = new Site_de_collecte();

	
	// Autres champs
	
	private Site_de_collecte		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoSite_de_collecte	daoSite_de_collecte;

	
	
	// Getters & Setters
	
	public ObservableList<Site_de_collecte> getListe() {
		return liste;
	}
	
	public Site_de_collecte getCourant() {
		return courant;
	}
	
	public Site_de_collecte getSelection() {
		return selection;
	}
	
	public void setSelection( Site_de_collecte selection ) {
		if ( selection == null ) {
			this.selection = new Site_de_collecte();
		} else {
			this.selection = daoSite_de_collecte.retrouver( selection.getId_site_de_collecte() );
		}
	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoSite_de_collecte.listerTout() );
		
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
			selection.setId( daoSite_de_collecte.inserer( courant ) );
		} else {
			// modficiation
			daoSite_de_collecte.modifier( courant );
		}
	}
	
	
	public void supprimer( Site_de_collecte item ) {
		
		// Vérifie l'abence de personnes rattachées à la catégorie
		if ( daoPersonne.compterPourSite_de_collecte( item.getId() ) != 0 ) {
			throw new ExceptionValidation( "Des personnes sont rattachées à cette catégorie.." ) ;
		}
		
		daoSite_de_collecte.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}
	*/
}
