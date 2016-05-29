package modele;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.logging.*;
import javax.net.ssl.*;

/**
 * Classe qui permet la connexion à un serveur et gère le déroulement 
 * d'une session connectée.
 * 
 * @author Dyden Ung
 *
 */

public class Session {

	private SSLSocket connexion = null;
	private BufferedOutputStream bos = null;
	private BufferedInputStream bis = null;
	private boolean isConnecte;
	private ObservateurSession obs = null;
	private Handler fileHandler = null;
	private Formatter simpleFormatter = null;
	private static Logger logger = Logger.getLogger("modele.Session");

	public Session() {
		creerLog();
	}
	
	/**
	 * Tentative de connexion à un serveur sécurisé SSL.
	 * 
	 * @param host String l'adresse IP du serveur 
	 * @param port int le numéro du port
	 */
	public void connexion(String host, int port) {
		 try { 
			 SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
			 connexion = (SSLSocket) f.createSocket(host, port);
			 connexion.startHandshake();
			 isConnecte = true;
			 logger.log(Level.FINE, "Connexion au serveur -- IP:" + connexion.getInetAddress().toString() + " -- Port:"+connexion.getPort());
			 obs.sessionConnectee(isConnecte);
		 } catch (Exception e) {
			 logger.log(Level.SEVERE, "Erreur dans la connexion au serveur");
		 }
	}
	
	/**
	 * Envoi d'une demande d'insertion dans la base de données du serveur.
	 * 
	 * @param hotelObject Hotellerie objet représentant le record à insérer dans la BD.
	 */
	public void insertData(Hotellerie hotelObject) {
		try {			
			bos = new BufferedOutputStream(connexion.getOutputStream());
			bis = new BufferedInputStream(connexion.getInputStream());
			bos.write("INSERT".getBytes()); // envoie le type de requ�te en bytes en premier et au fait flush;
			bos.flush();
			GestionRequete.sendObject(bos, hotelObject); // voir la classe en question.
			String reponse = this.read();
			obs.getServerResponse(reponse);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erreur dans l'ouverture d'un stream ou d'un envoi par le stream");
		}
	}
	
	/**
	 * Demande d'un record dans la base de données.
	 * 
	 * @param value String renvoie l'id du record et la table.
	 */
	public void selectInBD(String value) {
		try {	
				bos = new BufferedOutputStream(connexion.getOutputStream());
				bis = new BufferedInputStream(connexion.getInputStream());
				bos.write("SELECT".getBytes());
				bos.flush();
				bos.write(value.getBytes());
				bos.flush();
				String reponse = this.read();
				if(reponse.equals("TRUE")) {
					Hotellerie hotelObject = GestionRequete.receiveObject(bis);
					if(hotelObject != null) 
						obs.receptionRecherche(hotelObject);
					else
						obs.receptionRecherche(null);
				} else {
					obs.getServerResponse("CLOSE;FALSE");
				}
						
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erreur dans l'ouverture d'un stream ou d'un envoi par le stream");
		}
	}
	
	/**
	 * Mise à jour d'un record dans la base de donnée.
	 * 
	 * @param hotelObject Hotellerie objet représentant le record à insérer dans la BD.
	 */
	public void updateData(Hotellerie hotelObject) {
		try {			
			bos = new BufferedOutputStream(connexion.getOutputStream());
			bis = new BufferedInputStream(connexion.getInputStream());
			bos.write("UPDATE".getBytes());
			bos.flush();
			GestionRequete.sendObject(bos, hotelObject);
			String reponse = this.read();
			obs.getServerResponse(reponse);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erreur dans l'ouverture d'un stream ou d'un envoi par le stream");
		}
	}
	
	/**
	 * Suppression d'un record dans la base de donnée.
	 * 
	 * @param hotelObject Hotellerie objet représentant le record à insérer dans la BD.
	 */
	public void deleteData(Hotellerie hotelObject) {
		try {			
			bos = new BufferedOutputStream(connexion.getOutputStream());
			bis = new BufferedInputStream(connexion.getInputStream());
			bos.write("DELETE".getBytes());
			bos.flush();	
			GestionRequete.sendObject(bos, hotelObject);
			String reponse = this.read();
			obs.getServerResponse(reponse);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erreur dans l'ouverture d'un stream ou d'un envoi par le stream");
		}
	}
	
	/**
	 * Envoi au serveur que la fiche ouverte a été fermée.
	 * 
	 * @param valueIdTable String renvoie le Id et la table.
	 */
	public void sendCloseSelect(String valueIdTable) {
		try {			
			bos = new BufferedOutputStream(connexion.getOutputStream());
			bos.write("CLOSE".getBytes());
			bos.flush();	
			bos.write(valueIdTable.getBytes());
			bos.flush();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erreur dans l'ouverture d'un stream ou d'un envoi par le stream");
		}
	}
	
	/**
	 * Ferme la connexion et termine la session.
	 * 
	 */
	public void closeConnexion() {
		try {
				bos = new BufferedOutputStream(connexion.getOutputStream());
				bos.write("DECONNECT".getBytes());
				bos.flush();
				connexion.close();
				isConnecte = false;
				obs.sessionConnectee(isConnecte);
				logger.log(Level.FINE, "Deconnexion au serveur -- IP:" + connexion.getInetAddress().toString() + " -- Port:"+connexion.getPort());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erreur de déconnexion au serveur");
		}
	}
	
	/**
	 * Méthode privée
	 * crée un objet Logger pour supporter la journalisation de la session.
	 */
	private void creerLog(){
		   try{
				fileHandler = new FileHandler("./log.log",true);
				simpleFormatter = new SimpleFormatter();
				logger.addHandler(fileHandler);				
				fileHandler.setFormatter(simpleFormatter);
				//fileHandler.setLevel(Level.ALL);
				logger.setLevel(Level.ALL);
				logger.log(Level.FINE, "Connexion au serveur");			
			}catch(IOException exception){
				logger.log(Level.SEVERE, "Erreur d'écriture.", exception);
			}
	}
	
	/**
	 * Ajoute un observateur sur la session.
	 * 
	 * @param obs ObservateurSession recoit une implémentation de cette interface.
	 */
	public void addObserver(ObservateurSession obs) { this.obs = obs; }
	
	/**
	 * Méthode privée
	 * Lecture du input stream et renvoit dans un String le message reçu du serveur. 
	 * 
	 * @return String message reçu du serveur.
	 * @throws IOException
	 */
	private String read() throws IOException {
		String reponse = "";
		int stream = 0;
		byte[] b = new byte[4096];
		stream = bis.read(b);
		reponse = new String(b, 0, stream);
		return reponse;
	}
}

