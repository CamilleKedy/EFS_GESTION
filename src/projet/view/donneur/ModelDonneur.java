package projet.view.donneur;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoDonneur;
import projet.data.Donneur;
import projet.data.Site_de_collecte;


public class ModelDonneur {
	
	
	// Données observables 
	
	private final ObservableList<String> listeChoixCarte = FXCollections.observableArrayList();
	private final ObservableList<Donneur> liste = FXCollections.observableArrayList();
	private final ObservableList<String> critereRecherche = FXCollections.observableArrayList();
	private final FilteredList<Donneur> donneurFiltre = new FilteredList<>(liste, s -> true);// Liste filtrée de collectes. C'est cette liste qui sera affichée
	
	private final Donneur	courant = new Donneur();
	
	
	// Autres champs
	
	private Donneur		selection = new Donneur();

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoDonneur		daoDonneur;
	
	
    @PostConstruct
	public void init() {
    	listeChoixCarte.addAll("oui", "non");
    	critereRecherche.addAll("Nom", "Prenom","Adresse", "Ville");
    }
    
    
	
	public void setSelection( Donneur selection ) {
		if ( selection == null ) {
			this.selection = new Donneur();
		} else {
			this.selection = daoDonneur.retrouver( selection.getId() );
		}
	}
	
	// Actions
	// Fonction permettant de filtre la liste de donneur en fonction de la catégorie à filtrer et le contenu du texte, passés en paramètres
		public void filtreListeDonneur(String categorie, String filtre) {
			if (categorie!=null)
			{
				if (categorie.equals("Nom"))
				{
					donneurFiltre.setPredicate(s-> filtre != null ?  s.getNom().toLowerCase().contains(filtre.toLowerCase()) : true);
				}
				else if (categorie.equals("Prenom"))
				{
					donneurFiltre.setPredicate(s-> filtre != null ?  s.getPrenom().toLowerCase().contains(filtre.toLowerCase()) : true);
				}
				else if (categorie.equals("Adresse"))
				{
					donneurFiltre.setPredicate(s-> filtre != null ?  s.getAdresse().contains(filtre.toLowerCase()) : true);
				}
				else if (categorie.equals("Ville"))
				{
					donneurFiltre.setPredicate(s-> filtre != null ?  s.getVille().toLowerCase().contains(filtre.toLowerCase()) : true);
				}
				
			}	
		
		}
	
	public void actualiserListe(Site_de_collecte site) { 
		if(site == null) {
			liste.setAll( daoDonneur.listerTout() );
		}else {
			liste.setAll( daoDonneur.listerPourSite(site) );
		}
	}
	
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
		
		
		if(courant.getDateNaissance() != null && courant.getDateNaissance().isAfter(LocalDate.now())) {
			message.append( "\nERREUR Date postérieure au "+ LocalDate.now().toString() );	
		} else if( LocalDate.now().getYear()-courant.getDateNaissance().getYear()  < 18) {
			message.append( "\nERREUR: Le donneur doit avoir plus de 18 ans." );
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

	  public ObservableList<String> getListeChoixCarte() {
		  return listeChoixCarte;
	  }



	public ObservableList<String> getCritereRecherche() {
		return critereRecherche;
	}



	public FilteredList<Donneur> getDonneurFiltre() {
		return donneurFiltre;
	}



		
	

	
}
