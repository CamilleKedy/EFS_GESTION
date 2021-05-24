package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Compte  {

	
	// Données observables
	
	private final Property<Integer>	id			= new SimpleObjectProperty<>();
	private final StringProperty	pseudo		= new SimpleStringProperty();
	private final StringProperty	motDePasse	= new SimpleStringProperty();
	private final ObservableList<String> roles = FXCollections.observableArrayList();
	private final Property<Personnel>	personnel			= new SimpleObjectProperty<>();
	
	// Constructeurs
	
	public Compte() {
	}

	public Compte( int id, String pseudo, String motDePasse, Personnel personnel ) {
		setId(id);
		setPseudo(pseudo);
		setMotDePasse(motDePasse);
		setPersonnel(personnel);
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

	public final StringProperty pseudoProperty() {
		return this.pseudo;
	}

	public final String getPseudo() {
		return this.pseudoProperty().getValue();
	}

	public final void setPseudo(final String pseudo) {
		this.pseudoProperty().setValue(pseudo);
	}

	public final StringProperty motDePasseProperty() {
		return this.motDePasse;
	}

	public final String getMotDePasse() {
		return this.motDePasseProperty().getValue();
	}

	public final void setMotDePasse(final String motDePasse) {
		this.motDePasseProperty().setValue(motDePasse);
	}
	
	public final Property<Personnel> personnelProperty() {
		return this.personnel;
	}

	public final Personnel getPersonnel() {
		return this.personnelProperty().getValue();
	}

	public final void setPersonnel(final Personnel personnel) {
		this.personnelProperty().setValue(personnel);
	}

	
	public final ObservableList<String> getRoles() {
		return this.roles;
	}

	
	public boolean isInRole( String role ) {
		
		if ( role != null ) {
			for ( String r : roles ) {
				if ( role.equals( r ) ) {
					return true;
				}
			}
		}
		return false;
	}

	
	// toString()
	
	@Override
	public String toString() {
		return getPseudo();
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
		Compte other = (Compte) obj;
		return Objects.equals(id.getValue(), other.id.getValue() );
	}
	
}
