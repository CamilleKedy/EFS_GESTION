package projet.view.systeme;

import javax.inject.Inject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import jfox.javafx.view.IManagerGui;
import projet.view.EnumView;
import projet.view.site_de_collecte.ModelSite_de_collecte;

public class ControllerAccueilSecretaire {
	
	@FXML
    private Button btnDonneur;

    @FXML
    private Button btnRDV;

    @FXML
    private Button btnRetour;

    @Inject
	private IManagerGui		managerGui;
    @Inject
   	private ModelSite_de_collecte modelSite;

    @FXML
    void doDonneur(ActionEvent event) {
    	if(modelSite.getSelection() == null) 
    		managerGui.showView( EnumView.Site_de_collecteListeChoix );
    	else 
    		managerGui.showView( EnumView.DonneurListe );
    }

    @FXML
    void doRDV(ActionEvent event) {
    	managerGui.showView( EnumView.CollecteListe );
    }

    @FXML
    void doRetour(ActionEvent event) {
    	managerGui.showView( EnumView.CollecteListe );
    }

    @FXML
    void mouseEntered(MouseEvent event) {
    	if(event.getSource().equals(btnDonneur)) {
    		btnDonneur.setStyle("-fx-background-color:  #162c53;");
    	}
    	if(event.getSource().equals(btnRDV)) {
    		btnRDV.setStyle("-fx-background-color:  #162c53;");
    	}
    }
    
    @FXML
    void mouseExited(MouseEvent event) {
    	if(event.getSource().equals(btnDonneur)) {
    		btnDonneur.setStyle("-fx-background-color:  #ea302b;");
    	}
    	if(event.getSource().equals(btnRDV)) {
    		btnRDV.setStyle("-fx-background-color:   #ea302b;");
    	}
    }


}
