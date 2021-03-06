package projet.view.site_de_collecte;

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
import projet.data.Site_de_collecte;
import projet.view.EnumView;


public class ControllerSite_de_collecteListe extends Controller {
	
	
	// Composants de la vue
	
	@FXML
	private TableView<Site_de_collecte>	tableView;
	@FXML
	private Button				buttonModifier;
	@FXML
	private Button				buttonSupprimer;
	
	@FXML
	private TableColumn<Site_de_collecte, Integer> columnId_site_de_collecte;
	@FXML
	private TableColumn<Site_de_collecte, String> columnVille;
	@FXML
	private TableColumn<Site_de_collecte, Integer> columnNbr_lits;
	@FXML
	private TableColumn<Site_de_collecte, String> columnAdresse;
	@FXML
	private TextField			textFieldRecherche;
	@FXML
	private ComboBox<String>			comboBoxFiltres;

	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelSite_de_collecte		modelSite_de_collecte;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		// Data binding
tableView.setItems(  modelSite_de_collecte.getSite_de_collecteFiltre() );
		
		columnId_site_de_collecte.setCellValueFactory( t -> t.getValue().id_site_de_collecteProperty() );
		columnVille.setCellValueFactory( t -> t.getValue().villeProperty() );
		columnNbr_lits.setCellValueFactory( t -> t.getValue().nbr_litsProperty() );
		columnAdresse.setCellValueFactory( t -> t.getValue().adresseProperty() );

		// Configuraiton des boutons
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		
		// Gestion de la comboBox des filtres de recherche
		comboBoxFiltres.setItems(modelSite_de_collecte.getListeRecherche());
		textFieldRecherche.textProperty().addListener(obs ->{
			String filtre = textFieldRecherche.getText();
			modelSite_de_collecte.filtreListeSite_de_collecte(comboBoxFiltres.getValue(), filtre);
		});
	}
	
	public void refresh() {
		modelSite_de_collecte.actualiserListe();
		//UtilFX.selectInTableView( tableView, modelSite_de_collecte.getSelection() );
		tableView.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelSite_de_collecte.setSelection( null );
		managerGui.showView( EnumView.Site_de_collecteForm );
	}

	@FXML
	private void doModifier() {
		modelSite_de_collecte.setSelection( tableView.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.Site_de_collecteForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion???" ) ) {
			modelSite_de_collecte.supprimer( tableView.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	@FXML
	private void doRetour() {
		managerGui.showView( EnumView.AccueilGestionnaire );
	}
	// Gestion des ??v??nements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tableView.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun ??l??ment n'est s??lectionn?? dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}

	
	// M??thodes auxiliaires
	
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
