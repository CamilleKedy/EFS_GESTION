package projet.view.collecte;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.ConverterLocalDate;
import jfox.javafx.util.ConverterLocalTime;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Collecte;
import projet.data.Site_de_collecte;
import projet.view.EnumView;
import projet.view.site_collecte.ModelSiteCollecte;


public class ControllerCollecteForm extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField		textFieldId;
	@FXML
	private TextField		textFieldHeureDebut;
	@FXML
	private TextField		textFieldHeureFin;
	@FXML
	private DatePicker		datePickerDebut;
	@FXML
	private DatePicker		datePickerFin;
	@FXML
	private ComboBox<Site_de_collecte>	comboBoxSite;
	

	
	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelCollecte	modelCollecte;
	@Inject
	private ModelSiteCollecte	modelSite;


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Collecte courant = modelCollecte.getCourant();

		// Data binding

		bindBidirectional( textFieldId, courant.id_collecteProperty(), new ConverterInteger() );
		bindBidirectional( textFieldHeureDebut, courant.horaire_debutProperty(), new ConverterLocalTime() );
		bindBidirectional( textFieldHeureFin, courant.horaire_finProperty(), new ConverterLocalTime() );
		bindBidirectional( datePickerDebut, courant.date_debutProperty(), new ConverterLocalDate() );
		bindBidirectional( datePickerFin, courant.date_finProperty(), new ConverterLocalDate() );

		comboBoxSite.setItems( modelSite.getListe());
        comboBoxSite.valueProperty().bindBidirectional( courant.site_de_collecteProperty() );
		
		
		// Liste des personnes
		/*listViewPersonnes.setItems( courant.getPersonnes() );
		listViewPersonnes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );*/

		
	}
	
	
	public void refresh() {
		modelSite.actualiserListe();
		modelCollecte.actualiserCourant();
		
	}
	
	
	// Actions
	
	@FXML
	private void doSupprimerCategorie() {
		comboBoxSite.setValue( null );
	}
	
	/*
	@FXML
	private void doSupprimerPersonnes() {
		modelCollecte.supprimerPersonnes( listViewPersonnes.getSelectionModel().getSelectedItems() );
	}
	
	@FXML
	private void doAjouterPersonnes() {
		managerGui.showDialog( EnumView.CollecteAjoutPersonnes );
	}
	*/
	
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.CollecteListe );
	}
	
	@FXML
	private void doValider() {
		modelCollecte.validerMiseAJour();
		managerGui.showView( EnumView.CollecteListe );
	}
	
	
	// Méthodes auxiliaires
	

}
