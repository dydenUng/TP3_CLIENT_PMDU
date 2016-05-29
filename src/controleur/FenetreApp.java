package controleur;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * Classe abstraite pour créer une fênetre exécutable.
 * 
 * @author Dyden Ung
 *
 */
public abstract class FenetreApp extends Application {

	protected abstract String getFxml();
	protected abstract String getTitle();
	protected abstract Parent getRoot();
	
	@Override
	public void start(Stage primaryStage) {
		FenetreApp fenetreApp = loadView();
		Parent root = fenetreApp.getRoot();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle(getTitle());
		primaryStage.getIcons().add(new Image("/ressources/icon.png"));
		primaryStage.setWidth(620);
		primaryStage.setHeight(720);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	/**
	 * Méthode privée pour retourner le chemin d'une ressource fxml.
	 * @return URL le chemin d'un fichier fxml.
	 */
	private URL fxmlLocation() {
		return FenetreApp.class.getResource("/vue/" + getFxml());
	}
	
	/**
	 * Charge la fenêtre avec FXMLLoader et retourne le contrôleur du fxml.
	 * 
	 * @return type qui hérite de FenetreApp.
	 */
	public <Type extends FenetreApp> Type loadView() {
		try {
	        FXMLLoader loaderVue = new FXMLLoader();
	        loaderVue.setLocation(fxmlLocation());
	        loaderVue.load();
	        return loaderVue.getController();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
