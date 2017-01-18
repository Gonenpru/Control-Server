package db_items;
// Generated 04-ene-2017 11:51:08 by Hibernate Tools 5.1.0.Alpha1

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Query;

import engine.AirportEngine;
import engine.Enumerated.Sectors;
import threads.Launcher;

/**
 * Planes generated by hbm2java
 */

@Entity
@Table(name = "Planes")
public class Planes implements Serializable, Runnable {

	private static final long serialVersionUID = 1L;

	@Transient
	public AirportEngine ae;
	
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "Airline_id")
	private int airline_id;

	@Column(name = "Plane_Model_id")
	private int planeModel_id;

	@Column(name = "Plane_Status_id")
	private int planeStatu_id;

	@Transient
	private int terminal_id = -1;

	@Transient
	private final int STATUS_ARRIVE = 0;

	@Transient
	private final int STATUS_DEPART = 1;

	public Planes() {
	}

	public Planes(int id, int airlines, int planeModels, int planeStatus) {
		this.id = id;
		this.airline_id = airlines;
		this.planeModel_id = planeModels;
		this.planeStatu_id = planeStatus;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAirline_id() {
		return airline_id;
	}

	public void setAirline_id(int airline_id) {
		this.airline_id = airline_id;
	}

	public int getPlaneModel_id() {
		return planeModel_id;
	}

	public void setPlaneModel_id(int planeModel_id) {
		this.planeModel_id = planeModel_id;
	}

	public int getPlaneStatu_id() {
		return planeStatu_id;
	}

	public void setPlaneStatu_id(int planeStatu_id) {
		this.planeStatu_id = planeStatu_id;
	}
	
	

	public AirportEngine getAe() {
		return ae;
	}

	public void setAe(AirportEngine ae) {
		this.ae = ae;
	}

	public int getTerminal() {
		if (terminal_id == -1) {
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Query query = Launcher.session.createSQLQuery("select getTerminal(:plane)").setInteger("plane", this.id);
			terminal_id = Integer.parseInt(query.list().get(0).toString());
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
		}
		return terminal_id;
	}

	public void doMovement() throws InterruptedException {
		Thread.sleep(12000);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ae = new AirportEngine();
		ae.land(this);
		try {this.doMovement();} catch (InterruptedException e) {e.printStackTrace();}
		ae.takeOff(this);
		Thread.currentThread().interrupt();
	}
	
}
