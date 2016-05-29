package modele;

public class Client extends Hotellerie{
	
	private static final long serialVersionUID = 1L;
	private String prenom;
	private String nom;
	private String telephone;
	private String adresse;
	private String ville;
	private String codePostal;
	
	public Client(Integer idClient, String prenom, String nom, String noTel, String adresse, String ville, String cp) {
		super(idClient, TypeGestion.CLIENT);
		this.prenom = prenom;
		this.nom = nom;
		this.telephone = noTel;
		this.adresse = adresse;
		this.ville = ville;
		this.codePostal = cp;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	@Override
	public String toString() {
		return "Client [prenom=" + prenom + ", nom=" + nom + ", telephone=" + telephone + ", adresse=" + adresse
				+ ", ville=" + ville + ", codePostal=" + codePostal + "]";
	}
	
	
	
	
}
