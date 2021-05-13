package projet.data;

import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Personnel {


	// Données observables
	
	private final Property<Integer>		id			= new SimpleObjectProperty<>();
	private final StringProperty		nom	 		= new SimpleStringProperty();
	private final StringProperty		prenom		= new SimpleStringProperty();
	private final StringProperty		adresse 		= new SimpleStringProperty();
	private final Property<Profession>	profession	= new SimpleObjectProperty<>();
	
	
	// Constructeurs
	
	public Personnel() {
	}
	
	public Personnel( int id, String nom, String prenom, String adresse, Profession profession ) {
		setId(id);
		setNom(nom);
		setPrenom(prenom);
		setAdresse(adresse);
		setProfession(profession);
	}
	
	
	
	// Getters & setters

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
	
	public final java.lang.String getNom() {
		return this.nomProperty().getValue();
	}
	
	public final void setNom(final java.lang.String nom) {
		this.nomProperty().setValue(nom);
	}
	
	public final StringProperty prenomProperty() {
		return this.prenom;
	}
	
	public final java.lang.String getPrenom() {
		return this.prenomProperty().getValue();
	}
	
	public final void setPrenom(final java.lang.String prenom) {
		this.prenomProperty().setValue(prenom);
	}
	
	public final StringProperty adresseProperty() {
		return this.adresse;
	}
	
	public final java.lang.String getAdresse() {
		return this.adresseProperty().getValue();
	}
	
	public final void setAdresse(final java.lang.String adresse) {
		this.adresseProperty().setValue(adresse);
	}

	public final Property<Profession> professionProperty() {
		return this.profession;
	}

	public final projet.data.Profession getProfession() {
		return this.professionProperty().getValue();
	}

	public final void setProfession(final projet.data.Profession profession) {
		this.professionProperty().setValue(profession);
	}

	
	// toString()
	
	@Override
	public String toString() {
		return getNom() + " " + getPrenom();
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
		Personnel other = (Personnel) obj;
		return Objects.equals(id.getValue(), other.id.getValue() );
	}
	
}
