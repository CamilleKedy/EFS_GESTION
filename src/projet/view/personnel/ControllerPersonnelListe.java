package projet.view.personnel;

import java.time.LocalDate;
import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Personnel;
import projet.view.EnumView;


public class ControllerPersonnelListe extends Controller {
	
	
	// Composants de la vue

	@FXML
	private TableView<Personnel>	tableView;
	@FXML
	private Button			buttonModifier;
	@FXML
	private Button			buttonSupprimer;
	@FXML
	private TableColumn<Personnel, Integer> columnId_personnel;
	@FXML
	private TableColumn<Personnel, String> columnNom;
	@FXML
	private TableColumn<Personnel, String> columnPrenom;
	@FXML
	private TableColumn<Personnel, String> columnAdresse;
	@FXML
	private TableColumn<Personnel, String> columnProfession;
	@FXML
	private TextField			textFieldRecherche;
	@FXML
	private ComboBox<String>			comboBoxFiltres;
	
	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelPersonnel		modelPersonnel;
	

	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		Personnel courant = modelPersonnel.getCourant();
		// Data binding
		tableView.setItems(  modelPersonnel.getPersonnelFiltre() );
		
		columnId_personnel.setCellValueFactory( t -> t.getValue().idProperty() );
		columnNom.setCellValueFactory( t -> t.getValue().nomProperty() );
		columnPrenom.setCellValueFactory( t -> t.getValue().prenomProperty() );
		columnAdresse.setCellValueFactory(  t -> t.getValue().adresseProperty() );
		columnProfession.setCellValueFactory( t -> t.getValue().getProfession()!=null ?  t.getValue().getProfession().libelleProperty() : null );

		// Configuraiton des boutons
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		
		// Gestion de la comboBox des filtres de recherche
		comboBoxFiltres.setItems(modelPersonnel.getListeRecherche());
		textFieldRecherche.textProperty().addListener(obs ->{
			String filtre = textFieldRecherche.getText();
			modelPersonnel.filtreListePersonnel(comboBoxFiltres.getValue(), filtre);
		});
		
	}
	
	public void refresh() {
		modelPersonnel.actualiserListe();
	/*	UtilFX.selectInTableView( tableView, modelPersonnel.getSelection() );*/
		tableView.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelPersonnel.setSelection( null );
		managerGui.showView( EnumView.PersonnelForm );
	}

	@FXML
	private void doModifier() {
		modelPersonnel.setSelection( tableView.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.PersonnelForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelPersonnel.supprimer( tableView.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	@FXML
	private void doRetour() {
		managerGui.showView( EnumView.AccueilGestionnaire );
	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tableView.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
		
    	if( tableView.getSelectionModel().getSelectedItems().isEmpty() ) {
			buttonModifier.setDisable(true);
			buttonSupprimer.setDisable(true);
		} else {
			buttonModifier.setDisable(false);
			buttonSupprimer.setDisable(false);
		}
	}

}
