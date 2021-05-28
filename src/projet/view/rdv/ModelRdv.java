package projet.view.rdv;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.ConverterLocalDate;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoRdv;
import projet.dao.DaoDonneur;
import projet.data.Rdv;
import projet.data.Donneur;


public class ModelRdv  {
	
	
	// Données observables 
	private final Rdv	courant = new Rdv();
	private final ObservableList<Rdv> liste = FXCollections.observableArrayList(); 
	private final ObservableList<String> listePriseSang = FXCollections.observableArrayList(); 
	private final ObservableList<String> critereRecherche = FXCollections.observableArrayList();  // Liste des filtres de recherche
	private final ObservableList<Donneur> personnel = FXCollections.observableArrayList();
	private final FilteredList<Donneur> personnelFiltre = new FilteredList<>(personnel, s -> true);
	private final FilteredList<Rdv> collecteFiltre = new FilteredList<>(liste, s -> true);// Liste filtrée de collectes. C'est cette liste qui sera affichée
	
	
	
	
	/*private final ObservableList<Personne> personnesPourDialogAjout = FXCollections.observableArrayList();
	*/

	
	// Autres champs
	
	private Rdv		selection;

//	private  FilteredList<Donneur> personnelRdvFiltre = new FilteredList<>(courant.getDonneur(), s -> true);

	@Inject
	private IMapper		mapper;
    @Inject
	private DaoRdv		daoRdv;

   @Inject
    private DaoDonneur	daoDonneur;
   /*
    @Inject
    private ModelConfig	modelConfig;
    
	private boolean flagModifSchema;*/
    
	
	// Initialisations
   @PostConstruct
	public void init() {
		critereRecherche.addAll("Date", "Heure", "Collecte", "Prise de sang effectuée");
		listePriseSang.addAll("non", "oui");
   }
	
	// Getters & Setters
	
	public ObservableList<Rdv> getListe() {
		return liste;
	}
	
	public ObservableList<String> getCritereRecherche() {
		return critereRecherche;
	}
	
	public Rdv getCourant() {
		return courant;
	}
	
	public ObservableList<String> getListePriseSang() {
		return listePriseSang;
	}

	public ObservableList<Donneur> getDonneur() {
		return personnel;
	}
	
	public FilteredList<Donneur> getDonneurFiltre() {
		return personnelFiltre;
	}
/*	
	public FilteredList<Donneur> getDonneurRdvFiltre() {
		return personnelRdvFiltre;
	}
*/	
	public FilteredList<Rdv> getRdvFiltre() {
		return collecteFiltre;
	}
	
	public Rdv getSelection() {
		return selection;
	}
	
	public void setSelection( Rdv selection ) {
		if ( selection == null ) {
			this.selection = new Rdv();
		} else {
			this.selection = daoRdv.retrouver( selection.getId() );
		}
	}
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoRdv.listerTout() );
		
 	}
	
	// Fonction permettant de filtre la liste de collecte en fonction de la catégorie à filtrer et le contenu du texte, passés en paramètres
	public void filtreListeRdv(String categorie, String filtre) {
		if (categorie!=null)
		{
			if (categorie.equals("Date"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getId().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Heure"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getHeure_rdv().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Collecte"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getCollecte().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Prise de sang effectuée"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getPrise_de_sang().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			
		}	
	
	}
	
	/*
	public void filtreDonneurParProfession(String filtre) {
		personnelFiltre.setPredicate(s-> filtre != null ?  s.getProfession().getLibelle().equals(filtre) : true);
	
	}
	
	public void filtreDonneurRdvParProfession(String filtre) {
		personnelRdvFiltre.setPredicate(s-> filtre != null ?  s.getProfession().getLibelle().equals(filtre) : true);	
		
	}
	*/
	
	public void actualiserDonneurListe() {
		personnel.setAll(daoDonneur.listerTout());
		personnel.removeAll(courant.getDonneur());
		
	}
	
	
	public void actualiserCourant() {
		mapper.update( courant, selection );
		//filtreDonneurRdvParProfession(null);
		
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		/*
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
*/
		if( courant.getHeure_rdv() != null  ) {
		
			  if ( courant.getHeure_rdv().isAfter( LocalTime.of(18, 0))) {
				message.append( "\nL'heure du rendez-vous ne doit pas être après 18H00." );
			  }	
		}
		
		if( courant.getHeure_rdv() != null  ) {
			
			  if ( courant.getHeure_rdv().isBefore( LocalTime.of(8, 0))) {
				message.append( "\nL'heure du rendez-vous ne doit pas être avant 8H00." );
			  }	
		}

		if( courant.getDate_rdv() != null  ) {
			if( courant.getDate_rdv().isBefore( LocalDate.now()) ) 
			{
				message.append( "\nLa date de début doit être supérieure à la date d'aujourd'hui : "+ LocalDate.now().toString() );
			}
		}
		
		if( courant.getPrise_de_sang() != null  ) {
			if( courant.getQte_sang() < 420 || courant.getQte_sang() > 480 ) {
				message.append( "\nLa quantité de sang aà prélever doir respecter les normes médicales (Entre 420ml et 480ml)" );
			}
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoRdv.inserer( courant ) );
		} else {
			// modficiation
			daoRdv.modifier( courant );
		}
		
		
	}
	
	
	public void supprimer( Rdv item ) {
		daoRdv.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}
	
/*	
	public void supprimerDonneur( List<Donneur> items ) {
		courant.getDonneur().removeAll( items );
	}
	
	
	public void ajouterDonneur( List<Donneur> items ) {
		courant.getDonneur().addAll( items );
	}
*/	
	
	// Métodes auxiliaires
	
	
}
