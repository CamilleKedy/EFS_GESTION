package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DossierMedical  {

	
	// Données observables
	
	private final Property<Integer>	id			= new SimpleObjectProperty<>();
	private final ObservableList<String>	groupeSanguin		=FXCollections.observableArrayList();
	private final ObservableList<String>	rhesus	= FXCollections.observableArrayList();
	private final Property<Float> 	poids = new SimpleObjectProperty<>();
	private final StringProperty 	inaptitude = new SimpleStringProperty();
	private final Property<Donneur> donneur = new SimpleObjectProperty<>();

	
	// Constructeurs
	
	public DossierMedical() {
	}

	public DossierMedical( int id, float poids, String inaptitude, Donneur donneur ) {
		setId(id);	
		setPoids(poids);
		setInaptitude(inaptitude);
		setDonneur(donneur);
	}
	
	
	



	
	// toString()
	
//	@Override
//	public String toString() {
//		return getNom();
//	}
	
	
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
		DossierMedical other = (DossierMedical) obj;
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
		

	public final Property<Float> poidsProperty() {
		return this.poids;
	}
	

	public final Float getPoids() {
		return this.poidsProperty().getValue();
	}
	

	public final void setPoids(final Float poids) {
		this.poidsProperty().setValue(poids);
	}
	

	public final StringProperty inaptitudeProperty() {
		return this.inaptitude;
	}
	

	public final String getInaptitude() {
		return this.inaptitudeProperty().get();
	}
	

	public final void setInaptitude(final String inaptitude) {
		this.inaptitudeProperty().set(inaptitude);
	}
	

	public final Property<Donneur> idDonneurProperty() {
		return this.donneur;
	}
	

	public final Donneur getDonneur() {
		return this.idDonneurProperty().getValue();
	}
	

	public final void setDonneur(final Donneur donneur) {
		this.idDonneurProperty().setValue(donneur);
	}
	
	
	
	public boolean isInGroupeSanguin( String gs ) {
		
		if ( gs != null ) {
			for ( String r : groupeSanguin ) {
				if ( gs.equals( r ) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isInRhesus( String rh ) {
		
		if ( rh != null ) {
			for ( String r : rhesus ) {
				if ( rh.equals( r ) ) {
					return true;
				}
			}
		}
		return false;
	}

	public ObservableList<String> getGroupeSanguin() {
		return groupeSanguin;
	}

	public ObservableList<String> getRhesus() {
		return rhesus;
	}
	
	

	
	
	

	
	
}
