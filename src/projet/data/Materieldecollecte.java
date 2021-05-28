package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;


public class Materieldecollecte  {
	

	// Données observables
	
	private final Property<Collecte>	collecte		= new SimpleObjectProperty<>();
	private final Property<Materiel>	materiel		= new SimpleObjectProperty<>();
	private final Property<Integer>	    quantite		= new SimpleObjectProperty<>();
	
	
	// Constructeurs
	
	public Materieldecollecte() {
	}

	public Materieldecollecte( final Collecte collecte, final Materiel materiel, final int quantite ) {
		setCollecte(collecte);
		setMateriel(materiel);
		setQuantite(quantite);
	}
	
	
	// Getters et Setters	
	
	public final Property<Collecte> collecteProperty() {
		return this.collecte;
	}
	

	public final Collecte getCollecte() {
		return this.collecteProperty().getValue();
	}
	

	public final void setCollecte(final Collecte collecte) {
		this.collecteProperty().setValue(collecte);
	}
	

	public final Property<Materiel> materielProperty() {
		return this.materiel;
	}
	

	public final Materiel getMateriel() {
		return this.materielProperty().getValue();
	}
	

	public final void setMateriel(final Materiel materiel) {
		this.materielProperty().setValue(materiel);
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
	public String toString() {
		return  quantite.getValue() + " " + materiel.getValue();
	}

	@Override
	public int hashCode() {
		return Objects.hash(collecte.hashCode(), materiel.hashCode() );
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
		return Objects.equals(collecte.getValue(), other.materiel.getValue() );
	}


	


	
}

