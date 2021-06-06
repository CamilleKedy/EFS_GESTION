package projet.view.site_de_collecte;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoCollecte;
import projet.dao.DaoSite_de_collecte;
import projet.data.Collecte;
import projet.data.Site_de_collecte;


public class ModelSite_de_collecte  {
	
	
	// Données observables 
	
	private final ObservableList<Site_de_collecte> liste = FXCollections.observableArrayList(); 
	private final ObservableList<String> listeRecherche = FXCollections.observableArrayList();
	private final Site_de_collecte	courant = new Site_de_collecte();
	private final FilteredList<Site_de_collecte> site_de_collecteFiltre = new FilteredList<>(liste, s -> true);
	private final ObservableList<Collecte> listeCollecte = FXCollections.observableArrayList();

	
	// Autres champs
	
	private Site_de_collecte		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoSite_de_collecte	daoSite_de_collecte;
    @Inject
	private DaoCollecte	daoCollecte;
	
	// Initialisations
    @PostConstruct
 	public void init() {
 		listeCollecte.addAll(daoCollecte.listerTout());
 		listeRecherche.addAll("Numéro", "Ville", "Nombre de lits", "Adresse");
 		//niveau.addListener( obs -> chargerImages() );
 	}
	
	// Getters & Setters
    
    
	
	public ObservableList<Site_de_collecte> getListe() {
		return liste;
	}
	
	public ObservableList<String> getListeRecherche() {
		return listeRecherche;
	}

	public FilteredList<Site_de_collecte> getSite_de_collecteFiltre() {
		return site_de_collecteFiltre;
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
	
	public void filtreListeSite_de_collecte(String categorie, String filtre) {
		if (categorie!=null)
		{
			if (categorie.equals("Numéro"))
			{
				site_de_collecteFiltre.setPredicate(s-> filtre != null ?  s.getId_site_de_collecte().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Ville")) 
			{
				site_de_collecteFiltre.setPredicate(s-> filtre != null ?  s.getVille().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Nombre de lits"))
			{
				site_de_collecteFiltre.setPredicate(s-> filtre != null ?  s.getNbr_lits().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Adresse"))
			{
				site_de_collecteFiltre.setPredicate(s-> filtre != null ?  s.getAdresse().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}

			
		}	
	
	}
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getVille() == null || courant.getVille().isEmpty() ) {
			message.append( "\n La ville ne doit pas être vide." );
		} else  if ( courant.getVille().length()> 100 ) {
			message.append( "\nLe libellé est trop long : 100 maxi." );
		}
		
		if( courant.getNbr_lits() == null ) {
			message.append( "\n La nombre de lits ne doit pas être vide." );
		}
		
		if( courant.getAdresse() == null || courant.getAdresse().isEmpty() ) {
			message.append( "\n L'adresse ne doit pas être vide." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId_site_de_collecte() == null ) {
			// Insertion
			selection.setId_site_de_collecte( daoSite_de_collecte.inserer( courant ) );
		} else {
			// modficiation
			daoSite_de_collecte.modifier( courant );
		}
	}
	
	
	public void supprimer( Site_de_collecte item ) {
		
		// Vérifie l'abence de personnes rattachées à la catégorie
		daoSite_de_collecte.supprimer( item.getId_site_de_collecte() );
		selection = UtilFX.findNext( liste, item );
	}


	
}
