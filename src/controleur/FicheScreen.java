package controleur;

import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Client;
import modele.Reservation;
/**
 * Classe utilitaire pour ouvrir une fenêtre des fiches client et réservation.
 * 
 * @author Dyden Ung
 *
 */
public class FicheScreen {

	public static FicheClientCtrl openClientWindow(Client client) {
		FicheClientCtrl ficheClient = (new FicheClientCtrl()).loadView();
		ficheClient.setClient(client);
		Scene scene = new Scene(ficheClient.getRoot());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setWidth(415);
		stage.setHeight(710);
		stage.setResizable(false);
		return ficheClient;
	}
	
	public static FicheReserveCtrl openReserveWindow(Reservation reservation) {
		FicheReserveCtrl ficheReserve = (new FicheReserveCtrl()).loadView();
		ficheReserve.setReservation(reservation);
		Scene scene = new Scene(ficheReserve.getRoot());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		stage.setWidth(415);
		stage.setHeight(590);
		stage.setResizable(false);
		return ficheReserve;
		
	}
}
