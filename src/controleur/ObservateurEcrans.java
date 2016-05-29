package controleur;

import modele.Hotellerie;

public interface ObservateurEcrans {
	public void connexionServeur();
	public void deconnexionServeur();
	public void setBarreRecherche(String searchType, boolean isClient);
	public void gererRecherche(String value);
	public void setClientFormulaire();
	public void setReserverFormulaire();
	public void setParametreEcran();
	public void setIP(String ip);
	public void setPort(int port);
	public void ajouter(Hotellerie hotelObject);
	public void modifier(Hotellerie hotelObject);
	public void supprimer(Hotellerie hotelObject);
	public void fermerFiche(String valueIdTable);
	public void quitter();
	public void currentIP();
	public void currentPort();
}
