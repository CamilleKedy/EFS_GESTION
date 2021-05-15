package projet.view.donneur;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.view.IManagerGui;
import projet.data.Donneur;
import projet.view.EnumView;

public class ControllerDonneurListe {
	

	@FXML
	private TableView<Donneur>	tableView;
	@FXML
	private TableColumn<Donneur, String> columnNom;
	   
	@FXML
	private TableColumn<Donneur, String> columnPrenom;

	@FXML
	private TableColumn<Donneur, String> columnAdresse;

	@FXML
	private TableColumn<Donneur, String> columnVille;

	@FXML
	private Button			buttonAjouter;
	@FXML
	private Button			buttonModifier;
	@FXML
	private Button			buttonSupprimer;

	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelDonneur 	modelDonneur;
	@Inject
	private ModelDossierMedical modelDossierMedical;
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		
		// Data binding
		tableView.setItems(  modelDonneur.getListe() );

		columnNom.setCellValueFactory( t -> t.getValue().nomProperty() );
		columnPrenom.setCellValueFactory( t -> t.getValue().prenomProperty() );
		columnAdresse.setCellValueFactory( t -> t.getValue().adresseProperty() );
		columnVille.setCellValueFactory( t -> t.getValue().villeProperty() );
		
		// Configuraiton des boutons
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
				});
		configurerBoutons();

	}
	
	public void refresh() {
		modelDonneur.actualiserListe();
	/*	UtilFX.selectInTableView( tableView, modelCollecte.getSelection() );*/
		tableView.requestFocus();
	}
	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelDonneur.setSelection( null );
		managerGui.showView( EnumView.DonneurForm );
	}

	@FXML
	private void doModifier() {
		modelDonneur.setSelection( tableView.getSelectionModel().getSelectedItem() );
		modelDossierMedical.getCourant().setDonneur(tableView.getSelectionModel().getSelectedItem());
		managerGui.showView( EnumView.DonneurForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelDonneur.supprimer( tableView.getSelectionModel().getSelectedItem() );
			refresh();
		}
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
