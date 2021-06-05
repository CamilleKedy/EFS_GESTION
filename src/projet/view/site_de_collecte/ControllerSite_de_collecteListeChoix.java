package projet.view.site_de_collecte;

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
import projet.view.EnumView;
import projet.view.collecte.ModelCollecte;
import projet.view.rdv.ModelRdv;


public class ControllerSite_de_collecteListeChoix extends Controller {
	
	
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
	private ModelSite_de_collecte		modelSite_de_collecte;
	@Inject
	private ModelRdv		modelRdv;
	@Inject
	private ModelCollecte		modelCcollecte;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		// Data binding
		tableView.setItems(  modelSite_de_collecte.getListe() );
		comboBoxCollecte.setItems(modelSite_de_collecte.getListeCollecte());
		setCellFactory(comboBoxCollecte, item -> item.getDate_debut().toString());
		
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
	private void doDonneur() {
		modelSite_de_collecte.setSelection( tableView.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.DonneurListe );
	}
	@FXML
    void setSelectionCollecte() {
		modelCcollecte.setSelection(comboBoxCollecte.getValue());
		modelRdv.setSelection(null);
		modelRdv.getSelection().setCollecte(comboBoxCollecte.getValue());
    }

	
	@FXML
	private void doRetour() {
		managerGui.showView( EnumView.AccueilSecretaire );
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
