package projet.view.communication;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Collecte;
import projet.data.Site_de_collecte;
import projet.report.EnumReport;
import projet.report.ManagerReport;
import projet.view.EnumView;
import projet.view.collecte.ModelCollecte;
import projet.view.rdv.ModelRdv;
import projet.view.site_de_collecte.ModelSite_de_collecte;


public class ControllerChoixSite extends Controller {
	
	
	// Composants de la vue
	
	@FXML
	private TableView<Site_de_collecte>	tableView;
	@FXML
	private Button				buttonDonneur;
	
	@FXML
	private TableColumn<Site_de_collecte, Integer> columnId_site_de_collecte;
	@FXML
	private TableColumn<Site_de_collecte, String> columnVille;
	@FXML
	private TableColumn<Site_de_collecte, Integer> columnNbr_lits;
	@FXML
	private TableColumn<Site_de_collecte, String> columnAdresse;
	@FXML
	private ComboBox<Collecte> comboBoxCollecte;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	 @Inject
	private ManagerReport		managerReport;
	@Inject
	private ModelSite_de_collecte		modelSite_de_collecte;
	@Inject
	private ModelRdv		modelRdv;
	@Inject
	private ModelCollecte		modelCollecte;
	
	
	// Initialisation du Controller

	
	@FXML
	private void initialize() {

		// Data binding
		tableView.setItems(  modelSite_de_collecte.getListe() );
		
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
	}
	
	public void refresh() {
		modelSite_de_collecte.actualiserListe();
		//UtilFX.selectInTableView( tableView, modelSite_de_collecte.getSelection() );
		tableView.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doValider() {
		Map<String, Object> params = new HashMap<>();
		System.out.println(tableView.getSelectionModel().getSelectedItem().getId_site_de_collecte() );
		params.put( "idSite", tableView.getSelectionModel().getSelectedItem().getId_site_de_collecte() );
		managerReport.showViewer( EnumReport.DonneurParCollecte, params );
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
					//doModifier();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
		
    	if( tableView.getSelectionModel().getSelectedItems().isEmpty() ) {
			buttonDonneur.setDisable(true);
			
    	} else {
			buttonDonneur.setDisable(false);
		}
	}
	
	public static <T> void setCellFactory( ComboBox<T> comboBox, Callback< T, String > extractor ) {
		comboBox.setCellFactory( param -> new ListCell<T>() {
		    @Override
		    protected void updateItem(T item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null ) {
		            setText(null);
		        } else {
		            setText( extractor.call( item ) );
		        }
		    }
		});	
		
		comboBox.setButtonCell(comboBox.getCellFactory().call(null));
	}

}
