package projet.view.collecte;

import java.time.LocalDate;
import javax.inject.Inject;

import javafx.collections.ObservableList;
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
import projet.data.Collecte;
import projet.view.EnumView;


public class ControllerCollecteListe extends Controller {
	
	
	// Composants de la vue

	@FXML
	private TableView<Collecte>	tableView;
	@FXML
	private Button			buttonModifier;
	@FXML
	private Button			buttonSupprimer;
	@FXML
	private TableColumn<Collecte, Integer> columnId;
	@FXML
	private TableColumn<Collecte, LocalDate> columnDateDebut;
	@FXML
	private TableColumn<Collecte, LocalDate> columnDateFin;
	@FXML
	private TableColumn<Collecte, String> columnVille;
	@FXML
	private TableColumn<Collecte, String> columnAdresse;
	@FXML
	private TextField			textFieldRecherche;
	@FXML
	private ComboBox<String>			comboBoxFiltres;


	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelCollecte		modelCollecte;
	

	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {
		

		//Collecte courant = modelCollecte.getCourant();
		// Data binding
		tableView.setItems(  modelCollecte.getCollecteFiltre() );
		
		columnId.setCellValueFactory( t -> t.getValue().id_collecteProperty() );
		columnDateDebut.setCellValueFactory( t -> t.getValue().date_debutProperty() );
		columnDateFin.setCellValueFactory( t -> t.getValue().date_finProperty() );
		columnVille.setCellValueFactory( t -> t.getValue().getSite_de_collecte()!=null ?  t.getValue().getSite_de_collecte().villeProperty() : null );
		columnAdresse.setCellValueFactory( t -> t.getValue().getSite_de_collecte()!=null ?  t.getValue().getSite_de_collecte().adresseProperty() : null );

		// Configuraiton des boutons
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		
		// Gestion de la comboBox des filtres de recherche
		comboBoxFiltres.setItems(modelCollecte.getListeRecherche());
		textFieldRecherche.textProperty().addListener(obs ->{
			String filtre = textFieldRecherche.getText();
			modelCollecte.filtreListeCollecte(comboBoxFiltres.getValue(), filtre);
		});
		
	}
	
	public void refresh() {
		modelCollecte.actualiserListe();
	/*	UtilFX.selectInTableView( tableView, modelCollecte.getSelection() );*/
		tableView.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelCollecte.setSelection( null );
		managerGui.showView( EnumView.CollecteForm );
	}

	@FXML
	private void doModifier() {
		modelCollecte.setSelection( tableView.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.CollecteForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelCollecte.supprimer( tableView.getSelectionModel().getSelectedItem() );
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
