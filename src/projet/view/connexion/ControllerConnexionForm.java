package projet.view.connexion;

import javax.inject.Inject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import projet.data.Compte;
import projet.view.EnumView;


public class ControllerConnexionForm extends Controller {
	

	// Composants de la vue
	
	@FXML
	private TextField		fieldPseudo;
	@FXML
	private PasswordField	fieldMotDePasse;

	
	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelConnexionForm	modelConnexion;
	
	// Initialisation du Controller
	
	@FXML
	private void initialize() {
		
		// Data binding
		Compte courant = modelConnexion.getCourant();
		fieldPseudo.textProperty().bindBidirectional( courant.pseudoProperty() );
		fieldMotDePasse.textProperty().bindBidirectional( courant.motDePasseProperty() );

	}
	
	
	public void refresh() {
		// Ferem la session si elle est ouverte
		if ( modelConnexion.getCompteActif() != null ) {
			modelConnexion.fermerSessionUtilisateur();
		}
	}
	

	// Actions
	
	@FXML
	private void doConnexion() {
		managerGui.execTask( () -> {
			modelConnexion.ouvrirSessionUtilisateur();
			
			Platform.runLater( () -> {

         			if (modelConnexion.getCompteActif().getRoles().contains("GESTIONNAIRE"))
         				managerGui.showView(EnumView.AccueilGestionnaire);
         			else 
         				managerGui.showView(EnumView.AccueilSecretaire);
            }) ;
		} );
		
	}
	

}
