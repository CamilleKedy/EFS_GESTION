package projet.view.collecte;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoCollecte;
import projet.dao.DaoPersonnel;
import projet.data.Collecte;
import projet.data.Personnel;


public class ModelCollecte  {
	
	
	// Données observables 
	private final Collecte	courant = new Collecte();
	private final ObservableList<Collecte> liste = FXCollections.observableArrayList(); 
	private final ObservableList<String> listeRecherche = FXCollections.observableArrayList();  // Liste des filtres de recherche
	private final ObservableList<Personnel> personnel = FXCollections.observableArrayList();
	private final FilteredList<Personnel> personnelFiltre = new FilteredList<>(personnel, s -> true);
	private final FilteredList<Collecte> collecteFiltre = new FilteredList<>(liste, s -> true);// Liste filtrée de collectes. C'est cette liste qui sera affichée
	
	
	
	
	/*private final ObservableList<Personne> personnesPourDialogAjout = FXCollections.observableArrayList();
	*/

	
	// Autres champs
	
	private Collecte		selection;

	private  FilteredList<Personnel> personnelCollecteFiltre = new FilteredList<>(courant.getPersonnel(), s -> true);

	@Inject
	private IMapper		mapper;
    @Inject
	private DaoCollecte		daoCollecte;

   @Inject
    private DaoPersonnel	daoPersonnel;
   /*
    @Inject
    private ModelConfig	modelConfig;
    
	private boolean flagModifSchema;*/
    
	
	// Initialisations
   @PostConstruct
	public void init() {
		listeRecherche.addAll("Numéro", "Ville", "Adresse", "Date de début", "Date de fin");
		//niveau.addListener( obs -> chargerImages() );
	}
	
	// Getters & Setters
	
	public ObservableList<Collecte> getListe() {
		return liste;
	}
	
	public ObservableList<String> getListeRecherche() {
		return listeRecherche;
	}
	
	public Collecte getCourant() {
		return courant;
	}
	
	public ObservableList<Personnel> getPersonnel() {
		return personnel;
	}
	
	public FilteredList<Personnel> getPersonnelFiltre() {
		return personnelFiltre;
	}
	
	public FilteredList<Personnel> getPersonnelCollecteFiltre() {
		return personnelCollecteFiltre;
	}
	
	public FilteredList<Collecte> getCollecteFiltre() {
		return collecteFiltre;
	}
	
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
	
	// Fonction permettant de filtre la liste de collecte en fonction de la catégorie à filtrer et le contenu du texte, passés en paramètres
	public void filtreListeCollecte(String categorie, String filtre) {
		if (categorie!=null)
		{
			if (categorie.equals("Numéro"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getId_collecte().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Ville"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getSite_de_collecte().getVille().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Adresse"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getSite_de_collecte().getAdresse().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Date de début"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getDate_debut().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			else if (categorie.equals("Date de fin"))
			{
				collecteFiltre.setPredicate(s-> filtre != null ?  s.getDate_fin().toString().toUpperCase().contains(filtre.toUpperCase()) : true);
			}
			
		}	
	
	}
	public void filtrePersonnelParProfession(String filtre) {
		personnelFiltre.setPredicate(s-> filtre != null ?  s.getProfession().getLibelle().equals(filtre) : true);
	
	}
	
	public void filtrePersonnelCollecteParProfession(String filtre) {
		personnelCollecteFiltre.setPredicate(s-> filtre != null ?  s.getProfession().getLibelle().equals(filtre) : true);	
		
	}
	
	public void actualiserPersonnelListe() {
		personnel.setAll(daoPersonnel.listerTout());
		personnel.removeAll(courant.getPersonnel());
		
	}
	
	
	public void actualiserCourant() {
		mapper.update( courant, selection );
		filtrePersonnelCollecteParProfession(null);
		
	}
	
	
	// Calcule le nombre de matériel et de membre de personnel nécessaires à une collecte
	public void calculQuantites() {
		// Math.ceil permet d'arrondir à l'entier supérieur un double
		courant.setNbre_infirmiers((int)Math.ceil((courant.getSite_de_collecte().getNbr_lits()*1.)/2)) ; // 1 infirmier pour 2 lits
		courant.setNbre_medecins((int)Math.ceil((courant.getSite_de_collecte().getNbr_lits()*1.)/6)) ; // 1 médécin pour 6 lits
		courant.setNbre_secretaire((int)Math.ceil((courant.getSite_de_collecte().getNbr_lits()*1.)/12)) ; // 1 sécrétaire pour 12 lits
		courant.setNbre_agents_collation((int)Math.ceil((courant.getSite_de_collecte().getNbr_lits()*1.)/12)) ; // 1 agent de collation pour 12 lits
		
		// On considère la durée moyenne d'un don étant de 45 min. On calcule donc le nombre de dons possibles dans la plage horaire
		// Nombre de minute de fin - Nombre de min de départ / Durée moyenne d'un don * Nombre de dons simultanés (nombre de lits)
		courant.setQte_collation((int)Math.ceil(((courant.getHoraire_fin().getHour()*60+courant.getHoraire_fin().getMinute() - courant.getHoraire_debut().getHour()*60 -courant.getHoraire_debut().getMinute())*1.)/45)*courant.getSite_de_collecte().getNbr_lits() );
		
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
		if( courant.getHoraire_debut() != null  ) {
		
			  if ( courant.getHoraire_debut().isAfter(courant.getHoraire_fin())  ) {
				message.append( "\nL'heure de début doit être inférieure à l'heure de fin" );
			  }	
		}
		
		if( courant.getHoraire_fin() != null  ) {
			
			  if ( courant.getHoraire_fin().isBefore(courant.getHoraire_debut()) ||  courant.getHoraire_fin().equals(courant.getHoraire_debut())   ) {
				message.append( "\nL'heure de fin doit être supérieure à l'heure de début" );
			  }	
		}

		if( courant.getDate_debut() != null  ) {
			if( courant.getDate_debut().isBefore( LocalDate.of(2000, 1, 1) ) 
					|| courant.getDate_fin().isAfter( courant.getDate_fin() )) 
			{
				message.append( "\nLa date de début doit être supérieure à la date d'aujourd'hui." );
			}
		}
		
		if( courant.getDate_fin() != null  ) {
			if( courant.getDate_fin().isBefore( LocalDate.now() ) 
					|| courant.getDate_fin().isBefore( courant.getDate_debut() ) ) {
				message.append( "\nLa date de fin doit être supérieure à la date de début et à la date d'aujourd'hui" );
			}
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		// Mets à jour les quantités
		calculQuantites();
		// Effectue la mise à jour
		
		if ( courant.getId_collecte() == null ) {
			// Insertion
			selection.setId_collecte( daoCollecte.inserer( courant ) );
		} else {
			// modficiation
			daoCollecte.modifier( courant );
		}
		
		
	}
	
	
	public void supprimer( Collecte item ) {
		daoCollecte.supprimer( item.getId_collecte() );
		selection = UtilFX.findNext( liste, item );
	}
	
	
	public void supprimerPersonnel( List<Personnel> items ) {
		courant.getPersonnel().removeAll( items );
	}
	
	
	public void ajouterPersonnel( List<Personnel> items ) {
		courant.getPersonnel().addAll( items );
	}
	
	
	// Métodes auxiliaires
	
	
}
