package projet.view.collecte;

import java.time.LocalDate;
import java.util.Date;

import javax.inject.Inject;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Collecte;
import projet.data.Memo;
import projet.data.Personne;
import projet.data.Telephone;
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
	private TableColumn<Collecte, String> columnLieu;

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
		tableView.setItems(  modelCollecte.getListe() );
		
		columnId.setCellValueFactory( t -> t.getValue().id_collecteProperty() );
		columnDateDebut.setCellValueFactory( t -> t.getValue().date_debutProperty() );
		columnDateFin.setCellValueFactory( t -> t.getValue().date_finProperty() );
		columnLieu.setCellValueFactory( t -> t.getValue().getSite_de_collecte()!=null ?  t.getValue().getSite_de_collecte().villeProperty() : null );
		
		// Configuraiton des boutons
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		
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
