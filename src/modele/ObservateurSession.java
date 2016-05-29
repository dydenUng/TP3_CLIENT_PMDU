package modele;

public interface ObservateurSession {

	public void receptionRecherche(Hotellerie hotelObject);
	public void getServerResponse(String reponse);
	public void sessionConnectee(boolean isConnect);
	
}
