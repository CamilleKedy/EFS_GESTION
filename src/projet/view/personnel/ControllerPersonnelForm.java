package projet.view.personnel;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Profession;
import projet.data.Personnel;
import projet.view.EnumView;
import projet.view.collecte.ModelProfession;


public class ControllerPersonnelForm extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField		textFieldId_personnel;
	@FXML
	private TextField		textFieldNom;
	@FXML
	private TextField		textFieldPrenom;
	@FXML
	private TextField		textFieldAdresse;
	@FXML
	private ComboBox<Profession>	comboBoxProfession;

	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelPersonnel		modelPersonnel;
	@Inject
	private ModelProfession		modelProfession;


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Personnel courant = modelPersonnel.getCourant();

		// Data binding

		bindBidirectional( textFieldId_personnel, courant.idProperty(), new ConverterInteger() );
		bindBidirectional( textFieldNom, courant.nomProperty() );
		bindBidirectional( textFieldPrenom, courant.prenomProperty());
		bindBidirectional( textFieldAdresse, courant.adresseProperty() );
		comboBoxProfession.setItems( modelProfession.getListe() );
        comboBoxProfession.valueProperty().bindBidirectional( courant.professionProperty() );
		
		// Schéma
		//bindBidirectional(imageViewSchema, modelPersonnel.schemaPropert() );
		
	}
	
	
	public void refresh() {
		modelPersonnel.actualiserCourant();
		modelProfession.actualiserListe();		
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.PersonnelListe );
	}
	
	@FXML
	private void doValider() {
		modelPersonnel.validerMiseAJour();
		managerGui.showView( EnumView.PersonnelListe );
	}
	
	
	// Méthodes auxiliaires

}
