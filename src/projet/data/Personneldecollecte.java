package projet.data;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;


public class Personneldecollecte  {
	

	// Données observables
	
	private final Property<Integer>	id_personnel		= new SimpleObjectProperty<>();
	private final Property<Integer>	id_collecte			= new SimpleObjectProperty<>();
	
	
	// Constructeurs
	
	public Personneldecollecte() {
	}

	public Personneldecollecte( final int id_personnel, final int id_collecte ) {
		setId_personnel(id_personnel);
		setId_collecte(id_collecte);
	}
	
	
	// Getters et Setters

	public final Property<Integer> id_personnelProperty() {
		return this.id_personnel;
	}
	

	public final Integer getId_personnel() {
		return this.id_personnelProperty().getValue();
	}
	

	public final void setId_personnel(final Integer id_personnel) {
		this.id_personnelProperty().setValue(id_personnel);
	}
	

	public final Property<Integer> id_collecteProperty() {
		return this.id_collecte;
	}
	

	public final Integer getId_collecte() {
		return this.id_collecteProperty().getValue();
	}
	

	public final void setId_collecte(final Integer id_collecte) {
		this.id_collecteProperty().setValue(id_collecte);
	}
	


	
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id_personnel.getValue(), id_collecte.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personneldecollecte other = (Personneldecollecte) obj;
		return Objects.equals(id_personnel.getValue(), other.id_collecte.getValue() );
	}

	
}

