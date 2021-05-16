package projet.data;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Donneur  {

	
	// Données observables
	
	private final Property<Integer>	id			= new SimpleObjectProperty<>();
	private final StringProperty	nom		= new SimpleStringProperty();
	private final StringProperty	prenom	= new SimpleStringProperty();
	private final Property<LocalDate> dateNaissance = new SimpleObjectProperty<>();
	private final StringProperty 	ville = new SimpleStringProperty();
	private final StringProperty 	adresse = new SimpleStringProperty();
	private final Property<String> demandeCarte = new SimpleObjectProperty<>("non");

	
	// Constructeurs
	
	public Donneur() {
	}

	public Donneur( int id, String nom, String prenom, LocalDate date, String ville, String adresse, String carte ) {
		setId(id);
		setNom(nom);
		setPrenom(prenom);
		setDateNaissance(date);
		setVille(ville);
		setAdresse(adresse);
		setDemandeCarte(carte);
	}
	
	
	// toString()
	
	@Override
	public String toString() {
		return getNom();
	}
	
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Donneur other = (Donneur) obj;
		return Objects.equals(id.getValue(), other.id.getValue() );
	}

	
	// Getters et Setters
	
	public final Property<Integer> idProperty() {
		return this.id;
	}
	

	public final Integer getId() {
		return this.idProperty().getValue();
	}
	

	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}
	

	public final StringProperty nomProperty() {
		return this.nom;
	}
	

	public final String getNom() {
		return this.nomProperty().get();
	}
	

	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	

	public final StringProperty prenomProperty() {
		return this.prenom;
	}
	

	public final String getPrenom() {
		return this.prenomProperty().get();
	}
	

	public final void setPrenom(final String prenom) {
		this.prenomProperty().set(prenom);
	}
	

	public final StringProperty adresseProperty() {
		return this.adresse;
	}
	

	public final String getAdresse() {
		return this.adresseProperty().get();
	}
	

	public final void setAdresse(final String adresse) {
		this.adresseProperty().set(adresse);
	}
	

	public final StringProperty villeProperty() {
		return this.ville;
	}
	

	public final String getVille() {
		return this.villeProperty().get();
	}
	

	public final void setVille(final String ville) {
		this.villeProperty().set(ville);
	}
	
	public final Property<LocalDate> dateNaissanceProperty() {
		return this.dateNaissance;
	}
	

	public final LocalDate getDateNaissance() {
		return this.dateNaissanceProperty().getValue();
	}
	

	public final void setDateNaissance(final LocalDate dateNaissance) {
		this.dateNaissanceProperty().setValue(dateNaissance);
	}

	public final Property<String> demandeCarteProperty() {
		return this.demandeCarte;
	}
	

	public final String getDemandeCarte() {
		return this.demandeCarteProperty().getValue();
	}
	

	public final void setDemandeCarte(final String demandeCarte) {
		this.demandeCarteProperty().setValue(demandeCarte);
	}


	
}
