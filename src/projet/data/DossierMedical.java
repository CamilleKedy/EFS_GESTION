package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class DossierMedical  {

	
	// Données observables
	
	private final Property<Integer>	id			= new SimpleObjectProperty<>();
	private final Property<String>	groupeSanguin		=new SimpleObjectProperty<>();
	private final Property<String>	rhesus	= new SimpleObjectProperty<>();
	private final Property<Double> 	poids = new SimpleObjectProperty<>();
	private final StringProperty 	inaptitude = new SimpleStringProperty();
	private final Property<Donneur> donneur = new SimpleObjectProperty<>();

	
	// Constructeurs
	
	public DossierMedical() {
	}

	public DossierMedical( int id, String groupeSanguin, String rhesus, Double poids, String inaptitude, Donneur donneur ) {
		setId(id);
		setGroupeSanguin(groupeSanguin);
		setRhesus(rhesus);
		setPoids(poids);
		setInaptitude(inaptitude);
		setDonneur(donneur);
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
		

	public final Property<Double> poidsProperty() {
		return this.poids;
	}
	

	public final Double getPoids() {
		return this.poidsProperty().getValue();
	}
	

	public final void setPoids(final Double poids) {
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
	

	public final Property<Donneur> donneurProperty() {
		return this.donneur;
	}
	

	public final Donneur getDonneur() {
		return this.donneurProperty().getValue();
	}
	

	public final void setDonneur(final Donneur donneur) {
		this.donneurProperty().setValue(donneur);
	}
	
	
	public final Property<String> groupeSanguinProperty() {
		return this.groupeSanguin;
	}
	
	public final String getGroupeSanguin() {
		return this.groupeSanguinProperty().getValue();

	}
	

	public final void setGroupeSanguin(final String groupeSanguin) {
		this.groupeSanguinProperty().setValue(groupeSanguin);
	}
	

	public final Property<String> rhesusProperty() {
		return this.rhesus;
	}
	

	public final String getRhesus() {
		return this.rhesusProperty().getValue();
	}
	

	public final void setRhesus(final String rhesus) {
		this.rhesusProperty().setValue(rhesus);
	}
	
	
}