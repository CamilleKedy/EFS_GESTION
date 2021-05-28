package projet.view.rdv;

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
import jfox.javafx.util.ConverterLocalTime;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Rdv;
import projet.data.Collecte;
import projet.data.Donneur;
import projet.data.DossierMedical;
import projet.view.EnumView;
import projet.view.collecte.ModelCollecte;
import projet.view.donneur.ModelDonneur;
import projet.view.site_collecte.ModelSiteCollecte;


public class ControllerRdvForm extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField		textFieldId;
	@FXML
	private TextField		textFieldIdDonneur;
	@FXML
	private TextField		textFieldNom;
	@FXML
	private TextField		textFieldPrenom;
	@FXML
	private DatePicker		datePickerDdRdv;
	@FXML
	private TextField		textFieldHeure;
	@FXML
	private ComboBox<String>	comboBoxPriseSang;
	
	
	@FXML
	private TextField		textFieldQuantiteSang;
	@FXML
	private TextField		textFieldSiteDeCollecte;
	@FXML
	private TextField		textFieldIdCollecte;
//	@FXML
//	private ComboBox<String>	comboBoxGSanguin;
//	@FXML
//	private ComboBox<String>	comboBoxRhesus;
//	@FXML
//	private CheckBox		checkBoxAptitude;
//	@FXML
//	private TextArea		textAreaInaptitude;
	@FXML
	private Button			buttonEnregistrerRdv;
	@FXML
	private Button			buttonAnnuler;

	

	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	
	@Inject
	private ModelRdv modelRdv;
	@Inject
	private ModelCollecte	modelCollecte;
	@Inject
	private ModelDonneur modelDonneur;
	 


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Rdv courant = modelRdv.getCourant();
		Donneur courantDonneur = modelDonneur.getCourant();
		Collecte courantCollecte = modelCollecte.getCourant();

		// Data binding

		bindBidirectional( textFieldId, courant.idProperty(), new ConverterInteger() );
		bindBidirectional( textFieldNom, courantDonneur.nomProperty() );
		bindBidirectional( textFieldPrenom, courantDonneur.prenomProperty() );
		bindBidirectional( textFieldIdDonneur, courantDonneur.idProperty(), new ConverterInteger() );
		
		// Date de Rdv
		bindBidirectional( datePickerDdRdv, courant.date_rdvProperty(), new ConverterLocalDate() );
		bindBidirectional( textFieldHeure, courant.heure_rdvProperty(), new ConverterLocalTime() );

		comboBoxPriseSang.setItems( modelRdv.getListePriseSang() );
		comboBoxPriseSang.valueProperty().bindBidirectional(courant.prise_de_sangProperty());
		//comboBoxPriseSang.getSelectionModel().selectFirst();
		
		bindBidirectional( textFieldQuantiteSang, courant.qte_sangProperty(), new ConverterInteger() );
		textFieldQuantiteSang.setDisable(true);
		
		//Lieu du RDV
		//bindBidirectional(textFieldSiteDeCollecte, )
		
		// dossier Medical
		
//		comboBoxGSanguin.setItems(modelDossierMedical.getListeGroupeSanguin());
//		comboBoxGSanguin.valueProperty().bindBidirectional(courantDossier.groupeSanguinProperty());
//		
//		comboBoxRhesus.setItems(modelDossierMedical.getListeRhesus());
//		comboBoxRhesus.valueProperty().bindBidirectional(courantDossier.rhesusProperty());
		
		
		
	}
	
	
	public void refresh() {
		modelRdv.actualiserCourant();
		modelDonneur.actualiserCourant();
		modelCollecte.actualiserListe();		
	}
	
	
	// Actions
/*	
	 @FXML
	 void aptitudeOn(ActionEvent event) {
		 if(comboBoxPriseSang.is) {
			 textAreaInaptitude.setDisable(false);
		 }
		 else {
			  textAreaInaptitude.setDisable(true);
		 }		
	 }
*/
	 
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.RdvListe );
	}
	
	@FXML
	private void doValider() {
		modelRdv.validerMiseAJour();
		managerGui.showView( EnumView.RdvListe );
	}
/*	
	@FXML
	void doEnregistrerRdv() {
		modelRdv.validerMiseAJour();
		if(modelRdv.getCourant().getRdv() == null) {
			modelRdv.affecterRdv(modelRdv.getCourant());
		}
		modelRdv.validerMiseAJour();
		managerGui.showView( EnumView.RdvListe );
	}
*/
}
