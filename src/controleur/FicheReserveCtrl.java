package controleur;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import modele.Reservation;
import vue.Alerte;
/**
 * Classe contrôleur du fichier FXML "FicheReserve.fxml" qui crée la fenêtre d'une fiche de réservation.
 * 
 * @author Dyden Ung
 *
 */
public class FicheReserveCtrl extends FenetreApp implements Initializable{

	@FXML private VBox boxFiche;
    @FXML private TextField fieldNoReserve;
    @FXML private DatePicker datePickArrivee;
    @FXML private DatePicker datePickQuittee;
    @FXML private TextField fieldNoChambre;
    @FXML private TextField fieldNoClient;
    @FXML private Button btnModifier;
    @FXML private Button btnFermer;
    @FXML private Button btnSupprimer;
    @FXML private Button btnConfirmer;
    @FXML private Button btnAnnuler;
    
    private Reservation reservation = null;
	private ObservateurEcrans obs = null;
	
	public void setReservation(Reservation reserve) {
		this.reservation = reserve;
		initReservation();
	}
	
    public void closeWindow() {
    	boxFiche.getScene().getWindow().hide();
    }
    
    public void initClose() {
    	boxFiche.getScene().getWindow().setOnCloseRequest((event)->{
    		btnFermer.fire();
    	});
    }
	
	private void initReservation() {
		fieldNoReserve.setText(String.valueOf(reservation.getIdReservation()));
		fieldNoChambre.setText(String.valueOf(reservation.getNoChambre()));
		fieldNoClient.setText(String.valueOf(reservation.getIdClient()));
		datePickArrivee.setValue(reservation.getDateArrivee().toLocalDate());
		datePickQuittee.setValue(reservation.getDateQuitte().toLocalDate());
	}
	
	private void initBoutons() {
		btnModifier.setOnAction((action)-> {
			if(Alerte.confirmerAlerte("Modifier cette réservation ?")) {
				btnConfirmer.setDisable(false);
			}
		});
		btnConfirmer.setOnAction((action)->{
			if (validateEmptyField()) {
				if(validateForm()) {
					if(Alerte.confirmerAlerte("Êtes-vous certains de confirmer les changements ?")) {
						getFieldNewReservation();
						obs.modifier(reservation);
						btnConfirmer.setDisable(true);
					}
				} else {
					Alerte.afficherAlerte("Veuillez resaisir des valeurs valides", "Données invalide", AlertType.ERROR);
				}
			} else {
				Alerte.afficherAlerte("Veuillez remplir tous les champs", "Informations manquantes", AlertType.ERROR);
			}
		});
		btnSupprimer.setOnAction((action)-> {
    		if(Alerte.confirmerAlerte("Voulez-vous vraiment supprimer cet réservation de la base de donnée ?")) {
    			obs.supprimer(reservation);
    		}
		});
		btnAnnuler.setOnAction((action)-> {
			if(Alerte.confirmerAlerte("Annuler les modifications ?")) {
				btnConfirmer.setDisable(true);
				initReservation();
			}
		});
		btnFermer.setOnAction((action)->{
			obs.fermerFiche(fieldNoReserve.getText() + ";RESERVATION");
		});
	}
	
	private void liaisonsBoutons() {
		btnModifier.disableProperty().bind(btnConfirmer.disableProperty().isEqualTo(new SimpleBooleanProperty(false)));
		btnAnnuler.disableProperty().bind(btnConfirmer.disabledProperty());
		fieldNoChambre.disableProperty().bind(btnConfirmer.disabledProperty());
		fieldNoClient.disableProperty().bind(btnConfirmer.disabledProperty());
		datePickArrivee.disableProperty().bind(btnConfirmer.disabledProperty());
		datePickQuittee.disableProperty().bind(btnConfirmer.disabledProperty());
	}
	
    private boolean validateForm() {
    	boolean valide = true;
    	if(!validateNumber(fieldNoClient.getText())) {
    		fieldNoClient.setText("");
    		valide = false;
    	}
    	if(!validateNumber(fieldNoChambre.getText())) {
    		fieldNoChambre.setText("");
    		valide = false;
    	}
    	if(datePickQuittee.getValue().isBefore(datePickArrivee.getValue())) {
    		datePickQuittee.setValue(null);
    		valide = false;
    	}
    	return valide;
    }
	
    private boolean validateEmptyField() {
    	if(fieldNoClient.getText().isEmpty()) return false;
    	if(fieldNoChambre.getText().isEmpty()) return false;
    	if(datePickArrivee.getValue() == null) return false;
    	if(datePickQuittee.getValue() == null) return false;
    	return true;
    }
    
    private boolean validateNumber(String value) {
    	try { Integer.valueOf(value); } 
    	catch (Exception e){ 
    		fieldNoClient.setText("");
    		return false;
    	}
    	return true;
    }
		
    private void getFieldNewReservation() {
    	reservation.setDateArrivee(Date.valueOf(datePickArrivee.getValue()));
    	reservation.setDateQuitte(Date.valueOf(datePickQuittee.getValue()));
    	reservation.setNoChambre(Integer.valueOf(fieldNoChambre.getText()));
    	reservation.setIdClient(Integer.valueOf(fieldNoClient.getText()));
    }
    
	@Override public void initialize(URL location, ResourceBundle resources) {
		initBoutons();
		liaisonsBoutons();
	}
	
	public void addObserver(ObservateurEcrans obs) { this.obs = obs; }
	
	@Override protected String getFxml() { return "FicheReserve.fxml" ;}
	@Override protected String getTitle() { return "Reservation"; }
	@Override protected Parent getRoot() { return boxFiche; }

	public static void main(String[] args) { launch(args); }
	
}
