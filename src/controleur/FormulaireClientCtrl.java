package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.Client;
import vue.Alerte;

public class FormulaireClientCtrl extends FenetreApp implements Initializable{

	@FXML private VBox resultClientPane;
    @FXML private Label labelTitle;
    @FXML private Label lblIdClient;
    @FXML private TextField txtFieldPrenom;
    @FXML private TextField txtFieldNom;
    @FXML private TextField txtFieldTel;
    @FXML private TextField txtFieldAdresse;
    @FXML private TextField txtFieldVille;
    @FXML private TextField txtFieldCP;
    @FXML private HBox boxBoutonAjouter;
    @FXML private Button btnAjouter;
    
    private ObservateurEcrans obs;
    private void initAjoutBouton() {
    	btnAjouter.setOnAction((action)-> { 
    		if(validateEmptyField()) {
    			if(validateFormulaire()) {
	    			if(Alerte.confirmerAlerte("Voulez-vous vraiment ajouter ce client ?")) {
		    			obs.ajouter(new Client(
			    				null,
			    				getPrenomField(),
			    				getNomField(),
			    				getTelField(),
			    				getAdresseField(),
			    				getVilleField(),
			    				getCPField()
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
    
    public boolean validateFormulaire() {
    	boolean valide = true;
    	if(!txtFieldPrenom.getText().matches("[a-zA-Z]+$")) {
    		txtFieldPrenom.setText(null);
    		valide = false;
    	}
    	if(!txtFieldNom.getText().matches("[a-zA-Z]+$")) {
    		txtFieldNom.setText(null);
    		valide = false;
    	}
    	if(!validateNoTelephone(txtFieldTel.getText())) {
    		txtFieldTel.setText(null);
    		valide = false;
    	}
    	if(!txtFieldCP.getText().matches("^(?!.*[DFIOQUdfioqu])[A-VXYa-vxy][0-9][A-Za-z] ?[0-9][A-Za-z][0-9]$")) {
    		txtFieldCP.setText(null);
    		valide = false;
    	}
    	if(!txtFieldVille.getText().matches("[a-zA-Z]+$"))	{
    		txtFieldVille.setText(null);
    		valide = false;
    	}
    	return valide;
    }
    
    public boolean validateEmptyField() {
    	if(txtFieldPrenom.getText().isEmpty()) 	return false;
    	if(txtFieldNom.getText().isEmpty()) 	return false;
    	if(txtFieldTel.getText().isEmpty())		return false;
    	if(txtFieldAdresse.getText().isEmpty()) return false;
    	if(txtFieldVille.getText().isEmpty())	return false;
    	if(txtFieldCP.getText().isEmpty())		return false;  	
    	return true;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	this.labelTitle.setText("Remplissez tous les champs pour ajouter un nouveau client :"); 	
    	initAjoutBouton();
    }
    
	public void resetField() {
		txtFieldAdresse.setText(null);
		txtFieldCP.setText(null);
		txtFieldNom.setText(null);
		txtFieldPrenom.setText(null);
		txtFieldTel.setText(null);
		txtFieldVille.setText(null);
	}
    
    public String getPrenomField() { return txtFieldPrenom.getText(); }
    public String getNomField() { return txtFieldNom.getText(); }
    public String getTelField() { return txtFieldTel.getText(); }
    public String getAdresseField() { return txtFieldAdresse.getText(); }
    public String getVilleField() { return txtFieldVille.getText(); }
    public String getCPField() { return txtFieldCP.getText(); }
    public String getIdClient() { return lblIdClient.getText(); }
    
    public void addObserver(ObservateurEcrans observer) { this.obs = observer; }

	@Override protected String getFxml() { return "FormulaireClient.fxml"; }
	@Override protected String getTitle() { return "Formulaire Client"; }
	@Override protected Parent getRoot() { return resultClientPane; }
	
	public static void main(String[] args) { launch(args); }
	
    private boolean validateNoTelephone(String number) {
        if (number.matches("\\d{10}")) return true;
        else if(number.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
        else if(number.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
        else if(number.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
        else return false;       
    }

}

