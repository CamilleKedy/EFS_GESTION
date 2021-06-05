package projet.view.rdv;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
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
	
	private Property<String> priseSang = new SimpleObjectProperty<>("non");
	private final ObservableList<Rdv> liste = FXCollections.observableArrayList(); 
	private final ObservableList<String> listePriseSang = FXCollections.observableArrayList(); 
	private final ObservableList<String> critereRecherche = FXCollections.observableArrayList();  // Liste des filtres de recherche
	private final ObservableList<Donneur> donneur = FXCollections.observableArrayList();
	private final FilteredList<Donneur> donneurFiltre = new FilteredList<>(donneur, s -> true);
	private final FilteredList<Rdv> rdvFiltre = new FilteredList<>(liste, s -> true);// Liste filtrée de collectes. C'est cette liste qui sera affichée
	
	
	
	// Autres champs
	
	private Rdv		selection = new Rdv();
	private  Rdv	courant = new Rdv();

//	private  FilteredList<Donneur> donneurRdvFiltre = new FilteredList<>(courant.getDonneur(), s -> true);

	@Inject
	private IMapper		mapper;
    @Inject
	private DaoRdv		daoRdv;

   @Inject
    private DaoDonneur	daoDonneur;
  
	
	// Initialisations
   @PostConstruct
	public void init() {
		critereRecherche.addAll("Nom donneur","Site de collecte", "Prise de sang effectuée","Date", "Heure");
		listePriseSang.addAll("non", "oui");
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
				rdvFiltre.setPredicate(s-> filtre != null ?  s.getDate_rdv().toString().contains(filtre) : true);
			}
			else if (categorie.equals("Heure"))
			{
				rdvFiltre.setPredicate(s-> filtre != null ?  s.getHeure_rdv().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Site de collecte"))
			{
				rdvFiltre.setPredicate(s-> filtre != null ?  s.getCollecte().getSite_de_collecte().getVille().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Nom donneur"))
			{
				rdvFiltre.setPredicate(s-> filtre != null ?  s.getDonneur().getNom().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Prise de sang effectuée"))
			{
				rdvFiltre.setPredicate(s-> filtre != null ?  s.getPrise_de_sang().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			
		}	
	
	}
	

	
	public void actualiserDonneurListe() {
		donneur.setAll(daoDonneur.listerTout());
		donneur.removeAll(courant.getDonneur());
		
	}
	
	
	public void actualiserCourant() {
		mapper.update( courant, selection );
		//filtreDonneurRdvParProfession(null);
		
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
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
			if(courant.getQte_sang()!=null && ( courant.getQte_sang() < 420 || courant.getQte_sang() > 480) ) {
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
			return donneur;
		}
		
		public FilteredList<Donneur> getDonneurFiltre() {
			return donneurFiltre;
		}
	/*	
		public FilteredList<Donneur> getDonneurRdvFiltre() {
			return donneurRdvFiltre;
		}
	*/	
		public FilteredList<Rdv> getRdvFiltre() {
			return rdvFiltre;
		}
		
		public Rdv getSelection() {
			return selection;
		}
		
		public Property<String> priseSangProperty() {
			return priseSang;
		}
	
}
