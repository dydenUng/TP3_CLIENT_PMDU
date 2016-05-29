package modele;

import java.io.Serializable;

public class Hotellerie implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	public enum TypeGestion { CLIENT, RESERVATION }
	private Integer idClient;
	private TypeGestion type;
	
	public Hotellerie(Integer idClient, TypeGestion type) {
		this.idClient = idClient;
		this.type = type;
	}
	
	public Integer getIdClient() { return this.idClient; }

	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}

	public TypeGestion getType() { return this.type; }
	
}
