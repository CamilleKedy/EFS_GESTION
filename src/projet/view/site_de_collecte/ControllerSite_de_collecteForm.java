package projet.view.site_de_collecte;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Site_de_collecte;
import projet.view.EnumView;


public class ControllerSite_de_collecteForm extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField		textFieldId;
	@FXML
	private TextField		textFieldVille;
	@FXML
	private TextField		textFieldNbr_lits;
	@FXML
	private TextField		textFieldAdresse;

	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelSite_de_collecte		modelSite_de_collecte;


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Site_de_collecte courant = modelSite_de_collecte.getCourant();

		// Data binding

		bindBidirectional( textFieldId, courant.id_site_de_collecteProperty(), new ConverterInteger() );
		bindBidirectional( textFieldVille, courant.villeProperty() );
		bindBidirectional( textFieldNbr_lits, courant.nbr_litsProperty(), new ConverterInteger());
		bindBidirectional( textFieldAdresse, courant.adresseProperty() );

		
		// Schéma
		//bindBidirectional(imageViewSchema, modelSite_de_collecte.schemaPropert() );
		
	}
	
	
	public void refresh() {
		modelSite_de_collecte.actualiserCourant();
		//modelSite_de_collecte.actualiserListe();		
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.Site_de_collecteListe );
	}
	
	@FXML
	private void doValider() {
		modelSite_de_collecte.validerMiseAJour();
		managerGui.showView( EnumView.Site_de_collecteListe );
	}
	
	
	// Méthodes auxiliaires

}
