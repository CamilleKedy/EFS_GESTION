package projet.view.personnel;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoPersonnel;
import projet.dao.DaoPersonne;
import projet.data.Personnel;
import projet.data.Site_de_collecte;


public class ModelPersonnel  {
	
	
	// Données observables 
	
	private final ObservableList<Personnel> liste = FXCollections.observableArrayList(); 
	private final ObservableList<String> listeRecherche = FXCollections.observableArrayList();
	private final Personnel	courant = new Personnel();
	private final FilteredList<Personnel> PersonnelFiltre = new FilteredList<>(liste, s -> true);

	
	// Autres champs
	
	private Personnel		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoPersonnel	daoPersonnel;

	
    @PostConstruct
 	public void init() {
 		listeRecherche.addAll("Numéro", "Nom", "Prenom", "Adresse", "Profession");
 		//niveau.addListener( obs -> chargerImages() );
 	}
    
	// Getters & Setters
    
    
	
	public ObservableList<Personnel> getListe() {
		return liste;
	}
	
	public ObservableList<String> getListeRecherche() {
		return listeRecherche;
	}

	public FilteredList<Personnel> getPersonnelFiltre() {
		return PersonnelFiltre;
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
	
	public void filtreListePersonnel(String categorie, String filtre) {
		if (categorie!=null)
		{
			if (categorie.equals("Numéro"))
			{
				PersonnelFiltre.setPredicate(s-> filtre != null ?  s.getId().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Nom")) 
			{
				PersonnelFiltre.setPredicate(s-> filtre != null ?  s.getNom().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Prenom"))
			{
				PersonnelFiltre.setPredicate(s-> filtre != null ?  s.getPrenom().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Adresse"))
			{
				PersonnelFiltre.setPredicate(s-> filtre != null ?  s.getAdresse().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Profession"))
			{
				PersonnelFiltre.setPredicate(s-> filtre != null ?  s.getProfession().getLibelle().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			
		}	
	
	}
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
			message.append( "\n La le nom ne doit pas être vide." );
		} else  if ( courant.getNom().length()> 100 ) {
			message.append( "\nLe libellé est trop long : 100 maxi." );
		}
		
		if( courant.getPrenom() == null ) {
			message.append( "\n La nombre de lits ne doit pas être vide." );
		}
		
		if( courant.getAdresse() == null || courant.getAdresse().isEmpty() ) {
			message.append( "\n L'adresse ne doit pas être vide." );
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
		
		// Vérifie l'abence de personnes rattachées à la catégorie		
		daoPersonnel.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}

}
	
	

