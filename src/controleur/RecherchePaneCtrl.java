package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RecherchePaneCtrl extends FenetreApp implements Initializable{

    @FXML private VBox searchPane;
    @FXML private Label lblTitleSearch;
    @FXML private TextField champSearch;
    @FXML private Button btnSearch;
  
    private ObservateurEcrans obs;
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	btnSearch.setOnAction((action) -> { 
    		if(!champSearch.getText().isEmpty())
    			obs.gererRecherche(champSearch.getText()+";"); 
    	});
    }
    
    public void resetField() { champSearch.setText(""); }
    public void setLabelSearch(String value) { this.lblTitleSearch.setText(value); }
    public void addObserver(ObservateurEcrans observer) { this.obs = observer; }
    
	@Override protected String getFxml() { return "RecherchePane.fxml"; }
	@Override protected String getTitle() { return "Barre de recherche"; }
	@Override protected Parent getRoot() { return searchPane; }
	
	public static void main(String[] args) { launch(args); }


}

