package projet.view.rdv;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.view.IManagerGui;
import projet.data.Donneur;
import projet.data.Rdv;
import projet.view.EnumView;
import projet.view.collecte.ModelCollecte;
import projet.view.donneur.ModelDonneur;

public class ControllerRdvListe {
	

	@FXML
	private TableView<Rdv>	tableView;
	@FXML
	private TableColumn<Rdv, Integer> columnId;
	@FXML
	private TableColumn<Rdv, String> columnNom;
	@FXML
	private TableColumn<Rdv, String> columnPrenom;
	@FXML
	private TableColumn<Rdv, LocalTime> columnHeure;	   
	@FXML
	private TableColumn<Rdv, LocalDate> columnDate;
	@FXML
	private TableColumn<Rdv, String> columnAdresse;
	@FXML
	private TableColumn<Rdv, String> columnVille;
	@FXML
	private ComboBox<String> recherche;
	@FXML
	private TextField rechercher;

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
	private ModelRdv 	modelRdv;
	@Inject
	private ModelDonneur modelDonneur;
	@Inject
	private ModelCollecte modelCollecte;
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		
		// Data binding
		tableView.setItems(  modelRdv.getRdvFiltre());
		
		columnId.setCellValueFactory( t -> t.getValue().idProperty() );
		columnDate.setCellValueFactory( t -> t.getValue().date_rdvProperty() );
		columnHeure.setCellValueFactory( t -> t.getValue().heure_rdvProperty() );
		columnNom.setCellValueFactory( t -> t.getValue().getDonneur().nomProperty() );
		columnPrenom.setCellValueFactory( t -> t.getValue().getDonneur().prenomProperty() );
		columnAdresse.setCellValueFactory( t -> t.getValue().getDonneur().adresseProperty() );
		columnVille.setCellValueFactory( t -> t.getValue().getDonneur().villeProperty() );
		
		// Configuraiton des boutons
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
				});
		configurerBoutons();
		
		// Recherche
		recherche.setItems(modelRdv.getCritereRecherche());
		rechercher.textProperty().addListener(obs ->{
			String filtre = rechercher.getText();
			modelRdv.filtreListeRdv(recherche.getValue(), filtre);
		});

	}
	
	public void refresh() {
		modelRdv.actualiserListe();
		modelDonneur.actualiserCourant();
		modelCollecte.actualiserCourant();
		tableView.requestFocus();
	}
	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelRdv.setSelection( null );
//		modelDonneur.setSelection(null);
//		modelCollecte.setSelection(null);
		managerGui.showView( EnumView.RdvForm );
	}

	@FXML
	private void doModifier() {
		modelRdv.setSelection( tableView.getSelectionModel().getSelectedItem() );
//		modelDonneur.setSelection(tableView.getSelectionModel().getSelectedItem());
//		modelCollecte.setSelection( tableView.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.RdvForm );
	}
	
	@FXML
	private void doRetour() {
		managerGui.showView( EnumView.AccueilSecretaire );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppression ?" ) ) {
			modelRdv.supprimer( tableView.getSelectionModel().getSelectedItem() );
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
