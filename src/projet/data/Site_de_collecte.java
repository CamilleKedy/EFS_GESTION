package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Site_de_collecte  {
	

	// Données observables
	
	private final Property<Integer>	id_site_de_collecte	= new SimpleObjectProperty<>();
	private final StringProperty	ville	= new SimpleStringProperty();
	private final Property<Integer>	nbr_lits	= new SimpleObjectProperty<>();
	private final StringProperty	adresse	= new SimpleStringProperty();
	
	// Constructeurs
	
	public Site_de_collecte() {
	}

	public Site_de_collecte( final int id_site_de_collecte, final String ville, final int nbr_lits, final String adresse) {
		setId_site_de_collecte(id_site_de_collecte);
		setVille(ville);
		setNbr_lits(nbr_lits);
		setAdresse(adresse);
		
	}
	
	// Getters and Setters
	
	public final Property<Integer> id_site_de_collecteProperty() {
		return this.id_site_de_collecte;
	}
	

	public final Integer getId_site_de_collecte() {
		return this.id_site_de_collecteProperty().getValue();
	}
	

	public final void setId_site_de_collecte(final Integer id_site_de_collecte) {
		this.id_site_de_collecteProperty().setValue(id_site_de_collecte);
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
	

	public final Property<Integer> nbr_litsProperty() {
		return this.nbr_lits;
	}
	

	public final Integer getNbr_lits() {
		return this.nbr_litsProperty().getValue();
	}
	

	public final void setNbr_lits(final Integer nbr_lits) {
		this.nbr_litsProperty().setValue(nbr_lits);
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
	

	@Override
	public String toString() {
		return getAdresse()+", "+getVille();
	}

	
	
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id_site_de_collecte.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Site_de_collecte other = (Site_de_collecte) obj;
		return Objects.equals(id_site_de_collecte.getValue(), other.id_site_de_collecte.getValue() );
	}


	
	
}

