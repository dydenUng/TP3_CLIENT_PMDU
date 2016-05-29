package vue;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
/**
 * Classe pour faire afficher des alertes personnalisées.
 * 
 * @author Dyden Ung
 *
 */
public class Alerte {

	public static void afficherAlerte(String msg, String title, AlertType type) {
		Alert alert = new Alert(type);
		alert.setHeaderText(msg);
		alert.setTitle(title);
		alert.show();
	}
	
	public static boolean confirmerAlerte(String value) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(value);
		alert.showAndWait();
		ButtonType reponse = alert.getResult();
		if (reponse == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
}
