package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Materiel  {
	

	// Données observables
	
	private final Property<Integer>	id_materiel		= new SimpleObjectProperty<>();
	private final StringProperty	nom_materiel	= new SimpleStringProperty();	
	
	// Constructeurs
	
	public Materiel() {
	}

	public Materiel( final int id_materiel, final String nom_materiel) {
		setId_materiel(id_materiel);
		setNom_materiel(nom_materiel);
	}
	
	
	// Getters et Setters

	public final Property<Integer> id_materielProperty() {
		return this.id_materiel;
	}
	

	public final Integer getId_materiel() {
		return this.id_materielProperty().getValue();
	}
	

	public final void setId_materiel(final Integer id_materiel) {
		this.id_materielProperty().setValue(id_materiel);
	}
	

	public final StringProperty nom_materielProperty() {
		return this.nom_materiel;
	}
	

	public final String getNom_materiel() {
		return this.nom_materielProperty().get();
	}
	

	public final void setNom_materiel(final String nom_materiel) {
		this.nom_materielProperty().set(nom_materiel);
	}
	

	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id_materiel.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Materiel other = (Materiel) obj;
		return Objects.equals(id_materiel.getValue(), other.id_materiel.getValue() );
	}


	



	
}

