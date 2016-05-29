package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import modele.Client;
import modele.Hotellerie;
import modele.ObservateurSession;
import modele.Reservation;
import modele.Session;
import vue.Alerte;

/**
 * Classe Principale qui contrôle tous les contrôleurs FXML.
 * 
 * @author Dyden Ung
 *
 */
public class MainHotelCtrl extends FenetreApp implements Initializable, ObservateurEcrans, ObservateurSession{

    @FXML private VBox root;
    @FXML private VBox menuSlot;
    @FXML private VBox searchSlot;
    @FXML private VBox mainSlot;
    
    private final String IP = "localhost";
    private final int PORT = 5558;
    
    private Session session = null;
    private String adresseIP = IP;
    private int noPort = PORT;
    private boolean isClient = false;
    private ScaleTransition st;  
    private MenuHotelCtrl menuCtrl;
    private HomeScreenCtrl homeCtrl = (new HomeScreenCtrl()).loadView();
    private ParametreCtrl parametreCtrl = (new ParametreCtrl()).loadView();
    private RecherchePaneCtrl searchPaneCtrl = (new RecherchePaneCtrl()).loadView();
    private VBox searchBar = (VBox) searchPaneCtrl.getRoot();
    private FormulaireClientCtrl formClientCtrl = (new FormulaireClientCtrl()).loadView();
    private FormulaireReserverCtrl formReserve = (new FormulaireReserverCtrl()).loadView();
    private FicheReserveCtrl ficheReserve = null;
    private FicheClientCtrl ficheClient = null;
    
    private void initBox() {
    	menuCtrl = (new MenuHotelCtrl()).loadView();
    	menuSlot.getChildren().add(menuCtrl.getRoot());
    	searchSlot.getChildren().add(searchBar);	
    	searchBar.setScaleY(0);
    	st = new ScaleTransition(Duration.millis(150), searchBar);
    }
    
    private void setHomeScreen() {
    	if(!mainSlot.getChildren().contains(homeCtrl.getRoot())) {
    		if(!mainSlot.getChildren().isEmpty()) {
    			mainSlot.getChildren().remove(0);
    		}
	    	mainSlot.getChildren().add(homeCtrl.getRoot());
	    	VBox.setVgrow(homeCtrl.getRoot(), Priority.SOMETIMES);
    	}
    }
    
    private void setParametreScreen() {
    	if(searchBar.getScaleY() == 1) { transitionSearchBar(); }   	
    	mainSlot.getChildren().remove(0);
    	mainSlot.getChildren().add(parametreCtrl.getRoot());
    	parametreCtrl.setFieldIp(adresseIP);
    	parametreCtrl.setFieldPort(noPort);
    }
    
    private void transitionSearchBar() {
    	st.setFromY(searchBar.getScaleY());
    	st.setToY((searchBar.getScaleY() == 0) ? 1 : 0);
    	runLater(()->{
    		if(searchBar.getScaleY() == 1) { searchPaneCtrl.setLabelSearch(""); }
    		st.play();
    		searchPaneCtrl.resetField();
    	});  	
    }
    
    @Override public void setBarreRecherche(String searchType, boolean boolClient) {
    	isClient = boolClient;
    	searchPaneCtrl.setLabelSearch(searchType);
    	searchPaneCtrl.resetField();
    	if(searchBar.getScaleY() == 0) { transitionSearchBar(); } 	
    	setHomeScreen();
    }
      
    @Override public void gererRecherche(String value) {
    	if(isClient) 
    		runLater(()->{ session.selectInBD(value+"CLIENT"); });
    	else 
    		runLater(()->{ session.selectInBD(value+"RESERVATION"); });
    }
    
    @Override public void receptionRecherche(Hotellerie hotelObject) {
    	if (hotelObject != null) {
    		switch(hotelObject.getType()) {
    		case CLIENT:
    			Client client = (Client) hotelObject;
    			if(ficheClient == null) {
	    			ficheClient = FicheScreen.openClientWindow(client);
	    			ficheClient.addObserver(this);
	    			ficheClient.initClose();
    			} else {
    				Alerte.afficherAlerte("Une fenêtre déjà ouverte en cours", "Information", AlertType.WARNING);
    			}
    			break;
    		case RESERVATION:
    			Reservation reserve = (Reservation) hotelObject;
    			if(ficheReserve == null) {
	    			ficheReserve = FicheScreen.openReserveWindow(reserve);
	    			ficheReserve.addObserver(this);
	    			ficheReserve.initClose();
    			} else {
    				Alerte.afficherAlerte("Une fenêtre déjà ouverte en cours", "Information", AlertType.WARNING);
    			}
    			break;
    		}
    	} else {
    			Alerte.afficherAlerte("La recherche n'a rien donnée", "Résultat", AlertType.INFORMATION);
    	}
    }
    
    @Override public void connexionServeur() {
		session = new Session();
		session.addObserver(this);
		runLater(()->{ session.connexion(adresseIP, noPort); });
    }
    
    @Override public void deconnexionServeur() {
		session.closeConnexion();
		session = null; 	
		setHomeScreen();
		formClientCtrl.resetField();
		formReserve.resetField();
		if(searchBar.getScaleY()==1) { transitionSearchBar(); }
    }
      
    @Override public void setClientFormulaire() {
    	if(searchBar.getScaleY() == 1) { transitionSearchBar(); }   	
    	mainSlot.getChildren().remove(0);
    	mainSlot.getChildren().add(formClientCtrl.getRoot());
    }
    
    @Override public void setReserverFormulaire() { 
    	if(searchBar.getScaleY() == 1) { transitionSearchBar(); } 	
    	mainSlot.getChildren().remove(0);
    	mainSlot.getChildren().add(formReserve.getRoot());
    }
   
    @Override public void ajouter(Hotellerie hotelObject) { runLater(()->{ session.insertData(hotelObject); }); }
    @Override public void supprimer(Hotellerie hotelObject) { runLater(()->{ session.deleteData(hotelObject); }); }
    @Override public void modifier(Hotellerie hotelObject) { runLater(()->{ session.updateData(hotelObject); }); }
    
    @Override public void getServerResponse(String reponse) {
    	String[] value = reponse.split(";");
    	String msg = "";
    	switch (value[0]) {
	    	case "INSERT":
	    		if (value[1].equals("TRUE")) {
	    			msg += "Ajout réussi";
	    			formClientCtrl.resetField();
	    			formReserve.resetField();
	    			setHomeScreen();
	    		}
	    		else
	    			msg += "Échec de l'ajout";
	    		break;	
	    	case "UPDATE" :
	    		if (value[1].equals("TRUE")) {
	        		msg += "Modification réussie";
	    		}
	        	else {
	        		msg += "Échec de la modification";
	        	}
	        	break;
	    	case "DELETE" :
	    		if (value[1].equals("TRUE")) {
	        		msg += "Suppression réussie";
	        		if(ficheClient != null) 
	        			ficheClient.closeWindow();
	        		if(ficheReserve != null)
	        			ficheReserve.closeWindow();
	    			setHomeScreen();
	    		}
	        	else { 
	        		msg += "Échec de la suppression";
	    			setHomeScreen();
	        	}
	        	break;
	    	case "CLOSE" :
	    		msg+="Impossible. La fiche est présentement ouverte ailleurs.";
    	}  
    	Alerte.afficherAlerte(msg, "Information", AlertType.INFORMATION);
    }
    
    @Override public void sessionConnectee(boolean isConnect) {	
    	menuCtrl.setConnectState(isConnect);
    	if(isConnect) { Alerte.afficherAlerte("Connexion établie", "Information", AlertType.INFORMATION); }
    }
    
    @Override public void fermerFiche(String valueIdTable) {
    	session.sendCloseSelect(valueIdTable);
    	if(valueIdTable.contains("CLIENT")) {
    		ficheClient.closeWindow();
    		ficheClient = null;
    	}
    	else if(valueIdTable.contains("RESERVATION")) {
    		ficheReserve.closeWindow();
    		ficheReserve = null;
    	}
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	initBox();
    	setHomeScreen();
    	menuCtrl.addObserver(this);
    	searchPaneCtrl.addObserver(this);
    	formClientCtrl.addObserver(this);
    	formReserve.addObserver(this);
    	parametreCtrl.addObserver(this);
    }
    
    private void runLater(Runnable run) { Platform.runLater(run); }
    
    @Override public void quitter() { root.getScene().getWindow().hide(); }
    @Override public void setIP(String ip) { this.adresseIP = ip; }
    @Override public void setPort(int port) { this.noPort = port; }
    @Override public void currentIP() { parametreCtrl.setFieldIp(adresseIP); }
    @Override public void currentPort() { parametreCtrl.setFieldPort(noPort); }
    @Override public void setParametreEcran() { setParametreScreen(); }
    
	@Override protected String getFxml() { return "MainHotel.fxml"; }
	@Override protected String getTitle() { return "Hotel Application"; }
	@Override protected Parent getRoot() { return root; }
	
	public static void main(String[] args) { launch(args); }
}
