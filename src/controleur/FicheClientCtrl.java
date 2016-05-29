package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

import modele.Client;
import vue.Alerte;

/**
 * Classe contrôleur du fichier FXML "FicheClient.fxml" qui crée la fenêtre d'une fiche cliente.
 * 
 * @author Dyden Ung
 *
 */
public class FicheClientCtrl extends FenetreApp implements Initializable{

    @FXML private VBox boxFiche;
    @FXML private TextField fieldNoClient;
    @FXML private TextField fieldPrenom;
    @FXML private TextField fieldNom;
    @FXML private TextField fieldTelephone;
    @FXML private TextField fieldAdresse;
    @FXML private TextField fieldVille;
    @FXML private TextField fieldCodePostal;
    @FXML private Button btnModifier;
    @FXML private Button btnFermer;
    @FXML private Button btnSupprimer;
    @FXML private Button btnConfimer;
    @FXML private Button btnAnnuler;
    
    private Client client = null;
    private ObservateurEcrans obs = null;

    /**
     * Set le client sélectionné dans la fiche cliente.
     * 
     * @param client objet de CLient reçu en provenance du serveur.
     */
	public void setClient(Client client) {
		this.client = client;
		initClient();
	}
	
	/**
	 * Méthode privée
	 * Remplis les champs de texte à partir des informations de 
	 * l'objet Client. 
	 * 
	 */
	private void initClient() {
		fieldNoClient.setText(String.valueOf(client.getIdClient()));
		fieldPrenom.setText(client.getPrenom());
		fieldNom.setText(client.getNom());
		fieldTelephone.setText(client.getTelephone());
		fieldAdresse.setText(client.getAdresse());
		fieldVille.setText(client.getVille());
		fieldCodePostal.setText(client.getCodePostal());
	}
	
	/**
	 * Méthode privée
	 * 
	 * Gére les évènements du clic sur les boutons.
	 */
	private void initBoutons() {
		btnModifier.setOnAction((action) -> { 
			if(Alerte.confirmerAlerte("Modifier ce client ?")) {
				btnConfimer.setDisable(false);
			}
		});
		btnConfimer.setOnAction((action)->{
			if (validateEmptyField()) {
				if(validateFormulaire()) {
					if(Alerte.confirmerAlerte("Êtes-vous certains de confirmer les changements ?")) {
						getNewFieldClient();
						obs.modifier(client);
						btnConfimer.setDisable(true);
					}
				} else {
					Alerte.afficherAlerte("Veuillez resaisir des valeurs valides", "Données invalide", AlertType.ERROR);
				}
			} else {
				Alerte.afficherAlerte("Veuillez remplir tous les champs", "Informations manquantes", AlertType.ERROR);
			}
		});
		btnSupprimer.setOnAction((action)-> {
    		if(Alerte.confirmerAlerte("Voulez-vous vraiment supprimer ce client de la base de donnée ?")) {
    			obs.supprimer(client);
    		}
		});
		btnAnnuler.setOnAction((action)-> {
			if(Alerte.confirmerAlerte("Annuler les modifications ?")) {
				btnConfimer.setDisable(true);
				initClient();
			}
		});	
		btnFermer.setOnAction((action)-> {
			obs.fermerFiche(fieldNoClient.getText() + ";CLIENT");
		});
	}
	
	/**
	 * Méthode privée.
	 * 
	 * Gére les liaisons de l'état Disable des champs textes et boutons pour permettre ou
	 * non la modification des chamops de texte. 
	 * 
	 */
	private void liaisonsBoutons() {
		btnModifier.disableProperty().bind(btnConfimer.disableProperty().isEqualTo(new SimpleBooleanProperty(false)));
		btnAnnuler.disableProperty().bind(btnConfimer.disabledProperty());
		fieldPrenom.disableProperty().bind(btnConfimer.disabledProperty());
		fieldNom.disableProperty().bind(btnConfimer.disabledProperty());
		fieldTelephone.disableProperty().bind(btnConfimer.disabledProperty());
		fieldAdresse.disableProperty().bind(btnConfimer.disabledProperty());
		fieldVille.disableProperty().bind(btnConfimer.disabledProperty());
		fieldCodePostal.disableProperty().bind(btnConfimer.disabledProperty());
	}
	
	/**
	 * Méthode privée 
	 * 
	 * Vérifie s'il n'y a pas de champs textes vides.
	 * @return boolean vrai si tous les champs sont remplis, faux sinon
	 */
	private boolean validateEmptyField() {
		if (fieldPrenom.getText().isEmpty())	return false;
		if (fieldNom.getText().isEmpty())		return false;
		if (fieldTelephone.getText().isEmpty())	return false;
		if (fieldAdresse.getText().isEmpty())	return false;
		if (fieldVille.getText().isEmpty())		return false;
		if (fieldCodePostal.getText().isEmpty())return false;
		return true;
	}
	
	/**
	 * Méthode privée
	 * 
	 * Vérifie si les champs sont valides.
	 * 
	 * @return boolean vrai si toutes les valeurs sont valides, faux sinon.
	 */
    private boolean validateFormulaire() {
    	boolean valide = true;
    	if(!fieldPrenom.getText().matches("[a-zA-Z]+$")) {
    		fieldPrenom.setText(null);
    		valide = false;
    	}
    	if(!fieldNom.getText().matches("[a-zA-Z]+$")) {
    		fieldNom.setText(null);
    		valide = false;
    	}
    	if(!validateNoTelephone(fieldTelephone.getText())) {
    		fieldTelephone.setText(null);
    		valide = false;
    	}
    	if(!fieldCodePostal.getText().matches("^(?!.*[DFIOQUdfioqu])[A-VXYa-vxy][0-9][A-Za-z] ?[0-9][A-Za-z][0-9]$")) {
    		fieldCodePostal.setText(null);
    		valide = false;
    	}
    	if(!fieldVille.getText().matches("[a-zA-Z]+$"))	{
    		fieldVille.setText(null);
    		valide = false;
    	}
    	return valide;
    }
	
    /**
     * Méthode privée
     * 
     * fait la modification du client avec les nouvelles données 
     * saisies par le user.
     * 
     */
	private void getNewFieldClient() {
		client.setPrenom(fieldPrenom.getText());
		client.setNom(fieldNom.getText());
		client.setAdresse(fieldAdresse.getText());
		client.setTelephone(fieldTelephone.getText());
		client.setVille(fieldVille.getText());
		client.setCodePostal(fieldCodePostal.getText());
	}
	
	/**
	 * Méthode privée
	 * 
	 * vérifie la validité du numéro de téléphone.
	 * 
	 * @param number String le texte saisi dans le champs de texte fieldTelephone.
	 * @return boolean vrai si valide, faux sinon.
	 */
    private boolean validateNoTelephone(String number) {
        if (number.matches("\\d{10}")) return true;
        else if(number.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
        else if(number.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
        else if(number.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
        else return false;       
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initBoutons();	
		liaisonsBoutons();
	}
	
	/**
	 * Ferme la fenêtre de cette interface.
	 * 
	 */
	public void closeWindow() { boxFiche.getScene().getWindow().hide(); }
	
	public void initClose() {
		boxFiche.getScene().getWindow().setOnCloseRequest((event)->{
			btnFermer.fire();
		});
	}
	
	public void addObserver(ObservateurEcrans obs) { this.obs = obs; }

	@Override protected String getFxml() { return "FicheClient.fxml"; }
	@Override protected String getTitle() { return "Fiche Client"; }
	@Override protected Parent getRoot() { return boxFiche; }
	public static void main(String[] args) { launch(args); }
}
