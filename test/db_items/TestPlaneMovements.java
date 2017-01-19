package db_items;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestPlaneMovements {

	PlaneMovements planeMovements;

	@Before
	public void initTest() {
		planeMovements = new PlaneMovements();
		planeMovements = new PlaneMovements(0, 1);
		planeMovements = new PlaneMovements(0, 1, 0.1d, 0.1d);
	}

	@Test
	public void testGetSetId() {
		int id = 2;
		planeMovements.setId(id);
		assertEquals(id, planeMovements.getId());
	}

	@Test
	public void testGetSetPlanes() {
		int id = 2;
		planeMovements.setPlanes(id);
		assertEquals(id, planeMovements.getPlanes());
	}

	@Test
	public void testGetSetOutBool() {
		planeMovements.setOut();
		assertEquals(true, planeMovements.isOut());
	}

	@Test
	public void testGetSetOut() {
		int id = 1;
		planeMovements.setOut(id);
		assertEquals(id, planeMovements.getOut());
	}

	@Test
	public void testGetSetPosX() {
		double id = 0.02d;
		planeMovements.setPosx(id);
		assertEquals(String.valueOf(id), String.valueOf(planeMovements.getPosx()));
	}

	@Test
	public void testGetSetPosY() {
		double id = 0.02d;
		planeMovements.setPosy(id);
		assertEquals(String.valueOf(id), String.valueOf(planeMovements.getPosy()));
	}
}
