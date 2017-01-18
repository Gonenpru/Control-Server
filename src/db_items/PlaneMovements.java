package db_items;
// Generated 04-ene-2017 11:51:08 by Hibernate Tools 5.1.0.Alpha1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PlaneMovements generated by hbm2java
 */

@Entity
@Table(name="PlaneMovements")
public class PlaneMovements implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private static final int IN = 0, OUT = 1;

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="plane_id")
	private int plane_id;
	
	@Column(name="posx")
	private Double posx;
	
	@Column(name="posy")
	private Double posy;
	
	@Column(name="out")
	private int out;

	public PlaneMovements() {
	}

	public PlaneMovements(int id, int plane_id) {
		this.id = id;
		this.plane_id = plane_id;
		this.out = IN;
	}

	public PlaneMovements(int id, int plane_id, Double posx, Double posy) {
		this.id = id;
		this.plane_id = plane_id;
		this.posx = posx;
		this.posy = posy;
		this.out = IN;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlanes() {
		return this.plane_id;
	}

	public void setPlanes(int plane_id) {
		this.plane_id = plane_id;
	}

	public Double getPosx() {
		return this.posx;
	}

	public void setPosx(Double posx) {
		this.posx = posx;
	}

	public Double getPosy() {
		return this.posy;
	}

	public void setPosy(Double posy) {
		this.posy = posy;
	}
	
	public boolean isOut() {
		return (this.out == OUT);
	}
	
	public void setOut() {
		this.out = OUT;
	}

	public int getOut() {
		return out;
	}

	public void setOut(int out) {
		this.out = out;
	}
	
}
