package projet.view.donneur;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterFloat;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.ConverterLocalDate;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Donneur;
import projet.data.DossierMedical;
import projet.view.EnumView;


public class ControllerDonneurForm extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField		textFieldId;
	@FXML
	private TextField		textFieldNom;
	@FXML
	private TextField		textFieldPrenom;
	@FXML
	private DatePicker		datePickerDdn;
	@FXML
	private TextField		textFieldVille;
	@FXML
    private TextField textFieldAdresse;
	@FXML
	private ComboBox<String>	comboBoxCarte;
	
	@FXML
	private TextField textFieldID1;
	@FXML
	private TextField		textFieldPoids;
	@FXML
	private ComboBox<String>	comboBoxGSanguin;
	@FXML
	private ComboBox<String>	comboBoxRhesus;
	@FXML
	private CheckBox		checkBoxAptitude;
	@FXML
	private TextArea		textAreaInaptitude;
	@FXML
	private Button			buttonEnregistrerDonneur;
	@FXML
	private Button			buttonAnnulerE1;
	@FXML
	private Button			buttonEnregistrerDossier;
	@FXML
	private Button			buttonAnnulerE2;
	

	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	
	@Inject
	private ModelDonneur modelDonneur;
	  
	@Inject
	private ModelDossierMedical modelDossierMedical;
	 


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Donneur courant = modelDonneur.getCourant();
		DossierMedical courantDossier = modelDossierMedical.getCourant();

		// Data binding

		bindBidirectional( textFieldId, courant.idProperty(), new ConverterInteger() );
		bindBidirectional( textFieldNom, courant.nomProperty() );
		bindBidirectional( textFieldPrenom, courant.prenomProperty() );
		bindBidirectional( textFieldVille, courant.villeProperty() );
		bindBidirectional( textFieldAdresse, courant.adresseProperty() );
		// Date d'échéance
				bindBidirectional( datePickerDdn, courant.dateNaissanceProperty(), new ConverterLocalDate() );

		comboBoxCarte.setItems( modelDonneur.getListeChoixCarte() );
		//bindBidirectional( comboBoxCarte, courant.demandeCarteProperty() );
		comboBoxCarte.valueProperty().bindBidirectional( courant.demandeCarteProperty() );
		
		// dossier Medical
		
		bindBidirectional( textFieldID1, courantDossier.idProperty() ,new ConverterInteger());
		comboBoxGSanguin.setItems(modelDossierMedical.getListeGroupeSanguin());
		comboBoxGSanguin.valueProperty().bindBidirectional(courantDossier.rhesusProperty());
		
		comboBoxGSanguin.setItems(modelDossierMedical.getListeGroupeSanguin());
		comboBoxGSanguin.valueProperty().bindBidirectional(courantDossier.groupeSanguinProperty());
		
		bindBidirectional( textFieldPoids, courantDossier.poidsProperty(), new ConverterFloat() );
		bindBidirectional(textAreaInaptitude, courantDossier.inaptitudeProperty());
		
	}
	
	
	public void refresh() {
		modelDonneur.actualiserCourant();
		modelDossierMedical.actualiserListe();		
	}
	
	
	// Actions

	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.DonneurListe );
	}
	
	@FXML
	private void doValider() {
		modelDonneur.validerMiseAJour();
		managerGui.showView( EnumView.DonneurListe );
	}
	
	@FXML
	void doEnregistrerDonneur() {
		modelDonneur.validerMiseAJour();
		managerGui.showView( EnumView.DonneurListe );
	}

	@FXML
	void doEnregistrerDossier() {
		//modelDossierMedical.validerMiseAJour();
		managerGui.showView( EnumView.DonneurListe );
	}	
	
	// Méthodes auxiliaires
	
	/*
	 * private void actualiserDemandeCarteDansModele() { // Modifie le statut en
	 * fonction du bouton radio sélectionné Toggle bouton =
	 * toggleGroupStatut.getSelectedToggle(); int statut =
	 * toggleGroupStatut.getToggles().indexOf( bouton );
	 * //modelDonneur.getCourant().setStatut( statut ); }
	 * 
	 * private void actualiserStatutDansVue() { // Sélectionne le bouton radio
	 * correspondant au statut //int statut = modelDonneur.getCourant().getStatut();
	 * //Toggle bouton = toggleGroupStatut.getToggles().get( statut );
	 * //toggleGroupStatut.selectToggle( bouton ); }
	 */

}
