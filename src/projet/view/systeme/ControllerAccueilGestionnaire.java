package projet.view.systeme;

import javax.inject.Inject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import jfox.javafx.view.IManagerGui;
import projet.report.EnumReport;
import projet.report.ManagerReport;
import projet.view.EnumView;

public class ControllerAccueilGestionnaire {
	
	@FXML
    private Button btnGestionPersonnel;

    @FXML
    private Button btnGestionSite;

    @FXML
    private Button btnCommunication;

    @FXML
    private Button btnGestionCollecte;

    @FXML
    private Button btnStats;
    
    @Inject
	private IManagerGui		managerGui;
    @Inject
	private ManagerReport		managerReport;

    @FXML
    void doGestionPersonnel(ActionEvent event) {
    	managerGui.showView( EnumView.PersonnelListe );
    }

    @FXML
    void doGestionSite(ActionEvent event) {
    	managerGui.showView( EnumView.Site_de_collecteListe );
    }

    @FXML
    void doCollecte(ActionEvent event) {
    	managerGui.showView( EnumView.CollecteListe );
    }
    
    @FXML
    void doCommunication(ActionEvent event) {
    	managerReport.showViewer( EnumReport.DonneurParCollecte, null );
    }


    @FXML
    void mouseEntered(MouseEvent event) {
    	if(event.getSource().equals(btnGestionPersonnel)) {
    		btnGestionPersonnel.setStyle("-fx-background-color:  #162c53;");
    	}
    	if(event.getSource().equals(btnGestionSite)) {
    		btnGestionSite.setStyle("-fx-background-color:  #162c53;");
    	}
    	if(event.getSource().equals(btnGestionCollecte)) {
    		btnGestionCollecte.setStyle("-fx-background-color:  #162c53;");
    	}
    	if(event.getSource().equals(btnStats)) {
    		btnStats.setStyle("-fx-background-color:  #162c53;");
    	}
    	if(event.getSource().equals(btnCommunication)) {
    		btnCommunication.setStyle("-fx-background-color:  #162c53;");
    	}
    }
    
    @FXML
    void mouseExited(MouseEvent event) {
    	if(event.getSource().equals(btnGestionPersonnel)) {
    		btnGestionPersonnel.setStyle("-fx-background-color:  #ea302b;");
    	}
    	if(event.getSource().equals(btnGestionSite)) {
    		btnGestionSite.setStyle("-fx-background-color:  #ea302b;");
    	}
    	if(event.getSource().equals(btnGestionCollecte)) {
    		btnGestionCollecte.setStyle("-fx-background-color:  #ea302b;");
    	}
    	if(event.getSource().equals(btnStats)) {
    		btnStats.setStyle("-fx-background-color:  #ea302b;");
    	}
    	if(event.getSource().equals(btnCommunication)) {
    		btnCommunication.setStyle("-fx-background-color:  #ea302b;");
    	}
    	
    }


}
