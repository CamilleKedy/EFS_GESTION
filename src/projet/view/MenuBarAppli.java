package projet.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import jfox.javafx.view.IManagerGui;
import projet.commun.Roles;
import projet.data.Compte;
import projet.report.EnumReport;
import projet.report.ManagerReport;
import projet.view.connexion.ModelConnexionForm;



public class MenuBarAppli extends MenuBar {

	
	// Champs 
	
	private Menu	menuDonneur;
	private Menu	menuTests;
	private Menu	menuCollecte;
	
	private MenuItem itemDeconnecter;

	private MenuItem itemCategories;
	private MenuItem itemComptes;
	
	@Inject
	private IManagerGui 	managerGui;
	@Inject
	private ManagerReport	managerReport;
	@Inject
	private ModelConnexionForm modelConnexion;
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {

		//la méthode est aappelée avant que l'utilisateur ne soit connecté, c'est pourquoi ca marche pas
		
		// Variables de travail
		Menu menu;
		MenuItem item;
		
		
		// Manu Système
		
		menu =  new Menu( "Système" );;
		this.getMenus().add(menu);
		
		item = new MenuItem( "Se déconnecter" );
		item.setOnAction(  e -> managerGui.showView( EnumView.Connexion )  );
		menu.getItems().add( item );
		itemDeconnecter = item;
		
		item = new MenuItem( "Quitter" );
		item.setOnAction(  e -> managerGui.exit()  );
		menu.getItems().add( item );

		
		// Menu Secretaire
		
		menu =  new Menu( "Donneur" );;
		this.getMenus().add(menu);
		menuDonneur = menu;
		
		item = new MenuItem( "Rendez-vous" );
		//item.setOnAction(  e -> managerGui.showView( EnumView.RDVListe )  );
		menu.getItems().add( item );
		
		item = new MenuItem( "Rechercher un donneur" );
		item.setOnAction(  e -> managerGui.showView( EnumView.DonneurListe )  );
		menu.getItems().add( item );
		
		item = new MenuItem( "Retour" );
		item.setOnAction(  e -> managerGui.showView( EnumView.AccueilSecretaire )  );
		menu.getItems().add( item );
		
		
		item = new MenuItem( "Comptes" );
		item.setOnAction(  e -> managerGui.showView( EnumView.CompteListe )  );
		menu.getItems().add( item );
		itemComptes = item;

		
		// Manu Etats
		
		menu =  new Menu( "Collecte" );;
		this.getMenus().add(menu);
		menuCollecte = menu;
		
		item = new MenuItem( "Liste des collectes" );
		item.setOnAction(  e -> managerGui.showView( EnumView.CollecteListe )  );
		menu.getItems().add( item );
		
		item = new MenuItem( "Sites de collectes" );
		item.setOnAction(  e -> managerGui.showView( EnumView.Site_de_collecteListe )  );
		menu.getItems().add( item );

		item = new MenuItem( "Liste du Personnel" );
		item.setOnAction(  e -> managerGui.showView( EnumView.PersonnelListe ) );
		menu.getItems().add( item );
		

		// Configuration initiale du menu
		configurerMenu( modelConnexion.getCompteActif() );

		// Le changement du compte connecté modifie automatiquement le menu
		modelConnexion.compteActifProperty().addListener( (obs) -> {
			
					Platform.runLater( () -> configurerMenu( modelConnexion.getCompteActif() ) );
					System.out.println("Yo");
		}
			); 
		
	}

	
	// Méthodes auxiliaires
	
	private void configurerMenu( Compte compteActif  ) {

		System.out.println("12");
		itemDeconnecter.setDisable(true);
		
		menuDonneur.setVisible(false);
		menuCollecte.setVisible( false );
		
		if( compteActif != null ) {
			
			itemDeconnecter.setDisable(false);
			if( compteActif.isInRole( Roles.SECRETAIRE) ) {
				menuDonneur.setVisible(true);
			}
			if( compteActif.isInRole( Roles.GESTIONAIRE ) ) {
				System.out.println(compteActif.getRoles());
				menuCollecte.setVisible(true);
			}
		}
	}
	
}
