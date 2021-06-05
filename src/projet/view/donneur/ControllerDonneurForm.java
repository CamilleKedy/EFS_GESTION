package projet.view.donneur;

import javax.inject.Inject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterDouble;
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
	private TextField textFieldSexe;
	@FXML
	private TextField textFieldTelephone;
	@FXML
	private TextField textFieldEmail;
	@FXML
	private TextField		textFieldVille;
	@FXML
	private TextField textFieldAdresse;
	@FXML
	private ComboBox<String>	comboBoxCarte;


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
		bindBidirectional( textFieldSexe, courant.sexeProperty()) ;
		bindBidirectional( textFieldTelephone, courant.telephoneProperty() );
		bindBidirectional( textFieldEmail, courant.emailProperty() );
		bindBidirectional( textFieldAdresse, courant.adresseProperty() );
		
		// Date de naissance
		bindBidirectional( datePickerDdn, courant.dateNaissanceProperty(), new ConverterLocalDate() );

		comboBoxCarte.setItems( modelDonneur.getListeChoixCarte() );
		comboBoxCarte.valueProperty().bindBidirectional( courant.demandeCarteProperty() );

		// dossier Medical

		comboBoxGSanguin.setItems(modelDossierMedical.getListeGroupeSanguin());
		comboBoxGSanguin.valueProperty().bindBidirectional(courantDossier.groupeSanguinProperty());

		comboBoxRhesus.setItems(modelDossierMedical.getListeRhesus());
		comboBoxRhesus.valueProperty().bindBidirectional(courantDossier.rhesusProperty());

		bindBidirectional( textFieldPoids, courantDossier.poidsProperty(), new ConverterDouble() );
		bindBidirectional(textAreaInaptitude, courantDossier.inaptitudeProperty());

		textAreaInaptitude.setDisable(true);

	}


	public void refresh() {
		modelDonneur.actualiserCourant();
		modelDossierMedical.actualiserCourant();
		modelDossierMedical.actualiserListe();		
	}


	// Actions

	@FXML
	void aptitudeOn(ActionEvent event) {
		if(checkBoxAptitude.isSelected()) {
			textAreaInaptitude.setDisable(false);
		}
		else {
			textAreaInaptitude.setDisable(true);
		}

	}

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
		if(modelDossierMedical.getCourant().getDonneur() == null) {
			modelDossierMedical.affecterDonneur(modelDonneur.getCourant());
		}
		modelDossierMedical.validerMiseAJour();
		managerGui.showView( EnumView.DonneurListe );
	}

}
