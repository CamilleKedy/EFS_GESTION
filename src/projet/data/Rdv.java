package projet.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rdv {
	
	private final Property<Integer> id = new SimpleObjectProperty<>();
	private final Property<LocalDate> date_rdv = new SimpleObjectProperty<>();
	private final Property<Integer> qte_sang = new SimpleObjectProperty<>();
	private final Property<LocalTime> heure_rdv = new SimpleObjectProperty<>();
	private final StringProperty prise_de_sang = new SimpleStringProperty();
	private final Property<Collecte> collecte = new SimpleObjectProperty<>();
	private final Property<Donneur> donneur = new SimpleObjectProperty<>();
	
	
	public Rdv() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Rdv( int id, LocalDate date, LocalTime heure, String prise, int qte, Donneur donneur, Collecte collecte ) {
		setId(id);
		setDate_rdv(date);
		setHeure_rdv(heure);
		setPrise_de_sang(prise);
		setQte_sang(qte);
		setCollecte(collecte);
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
		Rdv other = (Rdv) obj;
		return Objects.equals(id.getValue(), other.id.getValue() );
	}

	public final Property<Integer> idProperty() {
		return this.id;
	}
	

	public final Integer getId() {
		return this.idProperty().getValue();
	}
	

	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}
	

	public final Property<LocalDate> date_rdvProperty() {
		return this.date_rdv;
	}
	

	public final LocalDate getDate_rdv() {
		return this.date_rdvProperty().getValue();
	}
	

	public final void setDate_rdv(final LocalDate date_rdv) {
		this.date_rdvProperty().setValue(date_rdv);
	}
	

	public final Property<Integer> qte_sangProperty() {
		return this.qte_sang;
	}
	

	public final Integer getQte_sang() {
		return this.qte_sangProperty().getValue();
	}
	

	public final void setQte_sang(final Integer qte_sang) {
		this.qte_sangProperty().setValue(qte_sang);
	}
	

	public final Property<LocalTime> heure_rdvProperty() {
		return this.heure_rdv;
	}
	

	public final LocalTime getHeure_rdv() {
		return this.heure_rdvProperty().getValue();
	}
	

	public final void setHeure_rdv(final LocalTime heure_rdv) {
		this.heure_rdvProperty().setValue(heure_rdv);
	}
	

	public final StringProperty prise_de_sangProperty() {
		return this.prise_de_sang;
	}
	

	public final String getPrise_de_sang() {
		return this.prise_de_sangProperty().get();
	}
	

	public final void setPrise_de_sang(final String prise_de_sang) {
		this.prise_de_sangProperty().set(prise_de_sang);
	}
	

	public final Property<Collecte> collecteProperty() {
		return this.collecte;
	}
	

	public final Collecte getCollecte() {
		return this.collecteProperty().getValue();
	}
	

	public final void setCollecte(final Collecte collecte) {
		this.collecteProperty().setValue(collecte);
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
	

}
