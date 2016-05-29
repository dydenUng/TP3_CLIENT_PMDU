package controleur;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class HomeScreenCtrl extends FenetreApp{

    @FXML private VBox homePane;

	@Override protected String getFxml() { return "HomeScreen.fxml"; }
	@Override protected String getTitle() { return "Home"; }
	@Override protected Parent getRoot() { return homePane; }
	
	public static void main(String[] args) { launch(args); }

}