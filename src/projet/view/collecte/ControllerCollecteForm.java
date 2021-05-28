package projet.view.collecte;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.ConverterLocalDate;
import jfox.javafx.util.ConverterLocalTime;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Collecte;

import projet.data.Personnel;
import projet.data.Profession;

import projet.data.Site_de_collecte;
import projet.view.EnumView;

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
	@FXML
	private ListView<Personnel>	listViewPersonnelCollecte;
	@FXML
	private Button			buttonAjouterPersonnel;
	@FXML
	private Button			buttonSupprimerPersonnel;
	@FXML
	private TextField		textFieldQtePersonnel;
	
	
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
        comboBoxProfession.getSelectionModel().selectFirst();
        
        listViewPersonnel.setItems( modelCollecte.getPersonnelFiltre() );
        UtilFX.setCellFactory( listViewPersonnel, item -> item.toString() );
        listViewPersonnel.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );

        
        listViewPersonnelCollecte.setItems( modelCollecte.getPersonnelCollecteFiltre() );
        UtilFX.setCellFactory( listViewPersonnelCollecte, item -> item.toString() );
		listViewPersonnelCollecte.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		
		// Configuration des boutons
		listViewPersonnel.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		listViewPersonnelCollecte.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		//configurerQte();
		
	}
	
	public void refresh() {
		modelSite.actualiserListe();
		modelProfession.actualiserListe();
		modelCollecte.actualiserCourant();	
	//	configurerQte();
		
	}
	
	
	// Actions
	@FXML
	private void doPersonnelProfession() {
		configurerQte();
		if (comboBoxProfession.getValue()!=null)
		{
			modelPersonnel.actualiserListeParProfession(comboBoxProfession.getValue().getLibelle());
			modelCollecte.actualiserPersonnelListe();
			modelCollecte.filtrePersonnelParProfession(comboBoxProfession.getValue().getLibelle());
			modelCollecte.filtrePersonnelCollecteParProfession(comboBoxProfession.getValue().getLibelle());
			
				
			UtilFX.selectInListView( listViewPersonnel, modelPersonnel.getSelection() );
			listViewPersonnel.requestFocus();
		}
		
	}
	

	
	@FXML
	private void doSupprimerPersonnel() {
		modelCollecte.supprimerPersonnel( listViewPersonnelCollecte.getSelectionModel().getSelectedItems() );
	}
	
	@FXML
	private void doAjouterPersonnel() {
		modelCollecte.ajouterPersonnel(listViewPersonnel.getSelectionModel().getSelectedItems());
		configBoutonAjout();
	}
	
	
	
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
	private void configurerBoutons() {
		
    	if( listViewPersonnel.getSelectionModel().getSelectedItems().isEmpty() ) {
			buttonAjouterPersonnel.setDisable(true);
		} else {
			buttonAjouterPersonnel.setDisable(false);
		}
    	
    	if( listViewPersonnelCollecte.getSelectionModel().getSelectedItems().isEmpty() ) {
			buttonSupprimerPersonnel.setDisable(true);
		} else {
			buttonSupprimerPersonnel.setDisable(false);
		}
	}
	
	private void configurerQte() {
		 if (comboBoxProfession.getValue()!=null)
	        {
	            switch ( comboBoxProfession.getValue().getLibelle() ) {
	            case "Secrétaire":
	            	System.out.println( modelCollecte.getCourant().getNbre_secretaire());
	                textFieldQtePersonnel.setText( modelCollecte.getCourant().getNbre_secretaire().toString() );
	                break;
	            case "Infirmière":
	                textFieldQtePersonnel.setText( modelCollecte.getCourant().getNbre_infirmiers().toString() );
	                break;
	            case "Médecin":
	                textFieldQtePersonnel.setText( modelCollecte.getCourant().getNbre_medecins().toString() );
	                break;
	            case "Agent de collation":
	                textFieldQtePersonnel.setText( modelCollecte.getCourant().getNbre_agents_collation().toString() );
	                break;
	            }
	            
	            configBoutonAjout();	           
	        }
		else
		{
			System.out.println("Valeur combobox profession nulle");
			textFieldQtePersonnel.setText(" ");
		}
	}

	private void configBoutonAjout()
	{
		 // configure le bouton d'ajout d'un personnel à une collecte en fonction de la quantité max.
        if (listViewPersonnelCollecte.getItems().size()== Integer.parseInt(textFieldQtePersonnel.getText()))
        {
        	System.out.println("toto");
        	buttonAjouterPersonnel.setDisable(true);
        }
       /* else
        {
        	buttonAjouterPersonnel.setDisable(false);
        }*/
	}
	

}
