package controleur;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import modele.Reservation;
import vue.Alerte;

public class FormulaireReserverCtrl extends FenetreApp implements Initializable{

	@FXML private VBox resultReserverPane;
    @FXML private Label lblTtile;
    @FXML private TextField txtFieldNoClient;
    @FXML private TextField txtFieldNoChambre;
    @FXML private DatePicker datePickArrivee;
    @FXML private DatePicker datePickQuittee;
    @FXML private Button btnAjouter;
    
    private ObservateurEcrans obs;
    
    private void initBoutons() {
    	btnAjouter.setOnAction((action) -> {
    		if(validateEmptyField()) {
    			if(validateForm()) {
	    			if(Alerte.confirmerAlerte("Voulez-vous vraiment ajouter cette réservation ?")) {
	    				obs.ajouter(new Reservation (
	    						null,
	    						Date.valueOf(getDateArrivee()),
	    						Date.valueOf(getDateDepart()),
	    						Integer.valueOf(getNoChambre()),
	    						Integer.valueOf(getNoClient())
	    						));
	    			}
    			} else {
    				Alerte.afficherAlerte("Veuillez resaisir des valeurs valides", "Données invalide", AlertType.ERROR);
    			}
    		} else {
    			Alerte.afficherAlerte("Veuillez remplir tous les champs", "Informations manquantes", AlertType.ERROR);
    		}
    	});
    }
    
    private boolean validateForm() {
    	boolean valide = true;
    	if(!validateNumber(txtFieldNoClient.getText())) {
    		txtFieldNoClient.setText("");
    		valide = false;
    	}
    	if(!validateNumber(txtFieldNoChambre.getText())) {
    		txtFieldNoChambre.setText("");
    		valide = false;
    	}
    	if(datePickQuittee.getValue().isBefore(datePickArrivee.getValue())) {
    		datePickQuittee.setValue(null);
    		valide = false;
    	}
    	return valide;
    }
    	
    private boolean validateNumber(String value) {
    	try { Integer.valueOf(value); } 
    	catch (Exception e){ 
    		txtFieldNoClient.setText("");
    		return false;
    	}
    	return true;
    }
    
    private boolean validateEmptyField() {
    	if(txtFieldNoClient.getText().isEmpty()) return false;
    	if(txtFieldNoChambre.getText().isEmpty()) return false;
    	if(datePickArrivee.getValue() == null) return false;
    	if(datePickQuittee.getValue() == null) return false;
    	return true;
    }
    
    @Override public void initialize(URL location, ResourceBundle resources) {
    	this.lblTtile.setText("Remplissez tous les champs pour ajouter une nouvelle réservation :");	
    	initBoutons();
    }
    
    public void resetField() {
    	txtFieldNoChambre.setText(null);
    	txtFieldNoClient.setText(null);
    	datePickArrivee.setValue(null);
    	datePickQuittee.setValue(null);
    }

    public String getNoClient() { return txtFieldNoClient.getText(); }
    public String getNoChambre() { return txtFieldNoChambre.getText(); }
    public LocalDate getDateArrivee() { return datePickArrivee.getValue(); }
    public LocalDate getDateDepart() { return datePickQuittee.getValue(); }
    
    public void addObserver(ObservateurEcrans obs) { this.obs = obs; }
    
	@Override protected String getFxml() { return "FormulaireReserver.fxml"; }
	@Override protected String getTitle() {	return "Screen Résultat Réservation"; }
	@Override protected Parent getRoot() { return resultReserverPane; }

	public static void main(String[] args) { launch(args); }
	
}

