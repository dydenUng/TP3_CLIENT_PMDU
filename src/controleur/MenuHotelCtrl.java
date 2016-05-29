package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import vue.Alerte;

public class MenuHotelCtrl extends FenetreApp implements Initializable{

    @FXML private VBox menuPane;
    @FXML private MenuBar menuBarre;
    @FXML private MenuItem clickMenuConnecter;
    @FXML private MenuItem clickMenuDeconnecter;
    @FXML private MenuItem clickClientSearch;
    @FXML private MenuItem clickClientCreate;
    @FXML private MenuItem clickReserveSearch;
    @FXML private MenuItem clickReserveCreate;
    @FXML private MenuItem clickReseau;
    @FXML private MenuItem clickQuitter;
    @FXML private Menu menuClient;
    @FXML private Menu menuReserver;
    
    private ObservateurEcrans obs;
    private SimpleBooleanProperty isConnect;

    public void initClicMenu() {
    	clickMenuConnecter.setOnAction((action)-> { 
    		obs.connexionServeur();
    	});
    	clickMenuDeconnecter.setOnAction((actionEvent)-> {
    		if(Alerte.confirmerAlerte("Confirmer la déconnexion ?")) 
    			obs.deconnexionServeur();
    	});
    	clickClientSearch.setOnAction((action)-> { 
    		runLater(()->{ obs.setBarreRecherche("Entrez le numéro, le nom, le prenom ou le no de tel du client :", true); }); 
    	});
    	
    	clickClientCreate.setOnAction((action)-> {
    		runLater(()-> { obs.setClientFormulaire(); });
    	});
    	
    	clickReserveSearch.setOnAction((action)-> { 
    		runLater(()->{ obs.setBarreRecherche("Entrez le numéro de réservation, la date, le no de chambre ou le numéro du client :", false); }); 
    	});
    	
    	clickReserveCreate.setOnAction((action)-> {
    		runLater(()-> { obs.setReserverFormulaire();});
    	});
    	clickQuitter.setOnAction((action)->{
    		obs.quitter();
    	});
    	clickReseau.setOnAction((action)-> {
    		obs.setParametreEcran();
    	});
    }
    
    private void initMenus() {
    	clickMenuDeconnecter.setDisable(true);
    	clickMenuConnecter.disableProperty().bind(isConnect);
    	clickMenuDeconnecter.disableProperty().bind(clickMenuConnecter.disableProperty().isEqualTo(new SimpleBooleanProperty(false)));
    	menuClient.disableProperty().bind(isConnect.isEqualTo(new SimpleBooleanProperty(false)));
    	menuReserver.disableProperty().bind(isConnect.isEqualTo(new SimpleBooleanProperty(false)));
    }
    
    @Override public void initialize(URL location, ResourceBundle resources) {
    	isConnect = new SimpleBooleanProperty(false);
    	initClicMenu();
    	initMenus();
    }
    
	@Override protected String getFxml() { return "MenuHotel.fxml"; }
	@Override protected String getTitle() { return "Menu"; }
	@Override protected Parent getRoot() { return menuPane; }
	
	public void setConnectState(boolean connectee) { this.isConnect.setValue(connectee); }
	private void runLater(Runnable runnable) { Platform.runLater(runnable); }
	public void addObserver(ObservateurEcrans observer) { this.obs = observer; }

	public static void main(String[] args) { launch(args); }

}

