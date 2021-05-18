package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;


public class Materieldecollecte  {
	

	// Données observables
	
	private final Property<Integer>	id_collecte			= new SimpleObjectProperty<>();
	private final Property<Integer>	id_materiel		= new SimpleObjectProperty<>();
	private final Property<Integer>	quantite		= new SimpleObjectProperty<>();
	
	
	// Constructeurs
	
	public Materieldecollecte() {
	}

	public Materieldecollecte( final int id_collecte, final int id_materiel, final int quantite ) {
		setId_collecte(id_collecte);
		setId_materiel(id_materiel);
		setQuantite(quantite);
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
	
	public final Property<Integer> id_materielProperty() {
		return this.id_materiel;
	}
	

	public final Integer getId_materiel() {
		return this.id_materielProperty().getValue();
	}
	

	public final void setId_materiel(final Integer id_materiel) {
		this.id_materielProperty().setValue(id_materiel);
	}
	
	public final Property<Integer> quantiteProperty() {
		return this.quantite;
	}
	

	public final Integer getQuantite() {
		return this.quantiteProperty().getValue();
	}
	

	public final void setQuantite(final Integer quantite) {
		this.quantiteProperty().setValue(quantite);
	}
		
	
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id_collecte.getValue(), id_materiel.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Materieldecollecte other = (Materieldecollecte) obj;
		return Objects.equals(id_collecte.getValue(), other.id_materiel.getValue() );
	}


	
}

