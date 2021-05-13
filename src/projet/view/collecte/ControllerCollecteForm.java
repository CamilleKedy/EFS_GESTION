package projet.view.collecte;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import jfox.javafx.util.ConverterDouble;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.ConverterLocalDate;
import jfox.javafx.util.ConverterLocalTime;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Categorie;
import projet.data.Collecte;
import projet.data.Memo;
import projet.data.Personne;
import projet.data.Personnel;
import projet.data.Profession;
import projet.data.Site_de_collecte;
import projet.view.EnumView;
import projet.view.personne.ModelCategorie;
import projet.view.personnel.ModelPersonnel;
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
	@FXML
	private ComboBox<Profession>	comboBoxProfession;
	@FXML
	private ListView<Personnel>	listViewPersonnel;

	
	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelCollecte	modelCollecte;
	@Inject
	private ModelSiteCollecte	modelSite;
	@Inject
	private ModelProfession	modelProfession;
	@Inject
	private ModelPersonnel	modelPersonnel;


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Collecte courant = modelCollecte.getCourant();

		// Data binding
		// TabPane 1
		bindBidirectional( textFieldId, courant.id_collecteProperty(), new ConverterInteger() );
		bindBidirectional( textFieldHeureDebut, courant.horaire_debutProperty(), new ConverterLocalTime() );
		bindBidirectional( textFieldHeureFin, courant.horaire_finProperty(), new ConverterLocalTime() );
		bindBidirectional( datePickerDebut, courant.date_debutProperty(), new ConverterLocalDate() );
		bindBidirectional( datePickerFin, courant.date_finProperty(), new ConverterLocalDate() );
		comboBoxSite.setItems( modelSite.getListe());
		comboBoxSite.valueProperty().bindBidirectional( courant.site_de_collecteProperty() );
        
		// TabPane 2
        comboBoxProfession.setItems( modelProfession.getListe());
        
        listViewPersonnel.setItems( modelPersonnel.getListe() );
        UtilFX.setCellFactory( listViewPersonnel, item -> item.toString() );
       
		// Liste des personnes
		/*listViewPersonnes.setItems( courant.getPersonnes() );
		listViewPersonnes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );*/

		
	}
	
	
	public void refresh() {
		modelSite.actualiserListe();
		modelProfession.actualiserListe();
		modelCollecte.actualiserCourant();
		
		
	}
	
	
	// Actions
	@FXML
	private void doPersonnelProfession() {
		modelPersonnel.actualiserListeParProfession(comboBoxProfession.getValue().getLibelle());
		UtilFX.selectInListView( listViewPersonnel, modelPersonnel.getSelection() );
		listViewPersonnel.requestFocus();
	}
	
	
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
