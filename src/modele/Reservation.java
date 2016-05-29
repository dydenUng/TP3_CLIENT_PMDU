package modele;

import java.sql.Date;

/**
 * Classe Reservation
 * 
 * @author Dyden Ung
 *
 */
public class Reservation extends Hotellerie{
	
	private static final long serialVersionUID = 1L;
	private Integer idReservation;
	private Date dateArrivee; 
	private Date dateQuitte;
	private int NoChambre;
	
	public Reservation(Integer idRe, Date dateArr, Date dateQuit, int noCham, Integer idClient) {
		super(idClient, TypeGestion.RESERVATION);
		this.idReservation = idRe;
		this.dateArrivee = dateArr;
		this.dateQuitte = dateQuit;
		this.NoChambre = noCham;
	}

	public Integer getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(Integer idReservation) {
		this.idReservation = idReservation;
	}

	public Date getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(Date dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public Date getDateQuitte() {
		return dateQuitte;
	}

	public void setDateQuitte(Date dateQuitte) {
		this.dateQuitte = dateQuitte;
	}

	public int getNoChambre() {
		return NoChambre;
	}

	public void setNoChambre(int noChambre) {
		NoChambre = noChambre;
	}
	
	
}
