package projet.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import org.exolab.castor.types.DateTime;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Collecte  {
	

	// Données observables
	
	private final Property<Integer>	id_collecte		= new SimpleObjectProperty<>();
	private final Property<Integer>	qte_collation	= new SimpleObjectProperty<>();
	private final Property<LocalDate>	date_debut	= new SimpleObjectProperty<>();
	private final Property<LocalDate>	date_fin	= new SimpleObjectProperty<>();
	private final Property<Integer>	nbre_infirmiers	= new SimpleObjectProperty<>();
	private final Property<Integer>	nbre_medecins	= new SimpleObjectProperty<>();
	private final Property<Integer> nbre_secretaire	= new SimpleObjectProperty<>();
	private final Property<Integer>	nbre_agents_collation	= new SimpleObjectProperty<>();
	private final Property<LocalTime>	horaire_debut	= new SimpleObjectProperty<>();
	private final Property<LocalTime>	horaire_fin	= new SimpleObjectProperty<>();
	private final Property<Site_de_collecte>	site_de_collecte		= new SimpleObjectProperty<>();
	private final ObservableList<Personnel> personnel = FXCollections.observableArrayList();
	
	
	// Constructeurs
	
	public Collecte() {
	}

	public Collecte( final int id_collecte, final int qte_collation, final LocalDate date_debut, final LocalDate date_fin, final int nbre_infirmiers, final int nbre_medecins, final int nbre_secretaire, final int nbre_agents_collation, final LocalTime horaire_debut, final LocalTime horaire_fin, final Site_de_collecte site_de_collecte) {
		setId_collecte(id_collecte);
		setQte_collation(qte_collation);
		setDate_debut(date_debut);
		setDate_fin(date_fin);
		setNbre_infirmiers(nbre_infirmiers);
		setNbre_medecins(nbre_medecins);
		setNbre_secretaire(nbre_secretaire);
		setNbre_agents_collation(nbre_agents_collation);
		setHoraire_debut(horaire_debut);
		setHoraire_fin(horaire_fin);
		setSite_de_collecte(site_de_collecte);
	}
	
	
	
	
	// Getters et Setters

	public final Property<Integer> id_collecteProperty() {
		return this.id_collecte;
	}
	

	public final Integer getId_collecte() {
		return this.id_collecteProperty().getValue();
	}
	

	public final void setId_collecte(final Integer id_collecte) {
		this.id_collecteProperty().setValue(id_collecte);
	}
	

	public final Property<Integer> qte_collationProperty() {
		return this.qte_collation;
	}
	

	public final Integer getQte_collation() {
		return this.qte_collationProperty().getValue();
	}
	

	public final void setQte_collation(final Integer qte_collation) {
		this.qte_collationProperty().setValue(qte_collation);
	}
	

	public final Property<LocalDate> date_debutProperty() {
		return this.date_debut;
	}
	

	public final LocalDate getDate_debut() {
		return this.date_debutProperty().getValue();
	}
	

	public final void setDate_debut(final LocalDate date_debut) {
		this.date_debutProperty().setValue(date_debut);
	}
	

	public final Property<LocalDate> date_finProperty() {
		return this.date_fin;
	}
	

	public final LocalDate getDate_fin() {
		return this.date_finProperty().getValue();
	}
	

	public final void setDate_fin(final LocalDate date_fin) {
		this.date_finProperty().setValue(date_fin);
	}
	

	public final Property<Integer> nbre_infirmiersProperty() {
		return this.nbre_infirmiers;
	}
	

	public final Integer getNbre_infirmiers() {
		return this.nbre_infirmiersProperty().getValue();
	}
	

	public final void setNbre_infirmiers(final Integer nbre_infirmiers) {
		this.nbre_infirmiersProperty().setValue(nbre_infirmiers);
	}
	

	public final Property<Integer> nbre_medecinsProperty() {
		return this.nbre_medecins;
	}
	

	public final Integer getNbre_medecins() {
		return this.nbre_medecinsProperty().getValue();
	}
	

	public final void setNbre_medecins(final Integer nbre_medecins) {
		this.nbre_medecinsProperty().setValue(nbre_medecins);
	}
	

	public final Property<Integer> nbre_secretaireProperty() {
		return this.nbre_secretaire;
	}
	

	public final Integer getNbre_secretaire() {
		return this.nbre_secretaireProperty().getValue();
	}
	

	public final void setNbre_secretaire(final Integer nbre_secretaire) {
		this.nbre_secretaireProperty().setValue(nbre_secretaire);
	}
	

	public final Property<Integer> nbre_agents_collationProperty() {
		return this.nbre_agents_collation;
	}
	

	public final Integer getNbre_agents_collation() {
		return this.nbre_agents_collationProperty().getValue();
	}
	

	public final void setNbre_agents_collation(final Integer nbre_agents_collation) {
		this.nbre_agents_collationProperty().setValue(nbre_agents_collation);
	}
	

	public final Property<LocalTime> horaire_debutProperty() {
		return this.horaire_debut;
	}
	

	public final LocalTime getHoraire_debut() {
		return this.horaire_debutProperty().getValue();
	}
	

	public final void setHoraire_debut(final LocalTime horaire_debut) {
		this.horaire_debutProperty().setValue(horaire_debut);
	}
	

	public final Property<LocalTime> horaire_finProperty() {
		return this.horaire_fin;
	}
	

	public final LocalTime getHoraire_fin() {
		return this.horaire_finProperty().getValue();
	}
	

	public final void setHoraire_fin(final LocalTime horaire_fin) {
		this.horaire_finProperty().setValue(horaire_fin);
	}
	
	public final Property<Site_de_collecte> site_de_collecteProperty() {
		return this.site_de_collecte;
	}

	public final projet.data.Site_de_collecte getSite_de_collecte() {
		return this.site_de_collecteProperty().getValue();
	}

	public final void setSite_de_collecte(final projet.data.Site_de_collecte site_de_collecte) {
		this.site_de_collecteProperty().setValue(site_de_collecte);
	}
	
	public ObservableList<Personnel> getPersonnel() {
		return personnel;
	}
	
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id_collecte.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collecte other = (Collecte) obj;
		return Objects.equals(id_collecte.getValue(), other.id_collecte.getValue() );
	}

	
	

	
	
}

