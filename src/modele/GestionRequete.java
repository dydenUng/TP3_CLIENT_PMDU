package modele;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe utilitaire contenant les méthodes pour envoyer
 * des demandes aux serveurs
 * 
 * @author Dyden Ung
 *
 */
public class GestionRequete {

	/**
	 * 
	 * @param bos le bufferedOutputStream de notre socket.
	 * @param hotelObject un objet h�rit� de la classe Hotellerie.
	 */
	public static void sendObject(BufferedOutputStream bos, Hotellerie hotelObject){
		try {
				ObjectOutputStream oos = new ObjectOutputStream(bos); 
				oos.writeObject(hotelObject); 
				oos.flush(); 
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public static Hotellerie receiveObject(BufferedInputStream bis) {
		Hotellerie hotelObject = null;
		try {
			ObjectInputStream objStream = new ObjectInputStream(bis);
		    hotelObject = (Hotellerie) objStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return hotelObject;
	}
}
