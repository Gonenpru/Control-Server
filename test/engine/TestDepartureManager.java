package engine;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.interceptor.annotations.After;

import db_items.PlaneMovements;
import db_items.Planes;
import engine.Enumerated.Arrival;
import threads.SynchronizationFactory;
import utils.HibernateUtils;

public class TestDepartureManager {

	DepartureManager departureManager;
	Planes plane;
	
	@Before
	public void initTest() {
		HibernateUtils.start();
		SynchronizationFactory.define();
		departureManager = new DepartureManager();
		plane = new Planes(1, 0, 0, 0);

		SynchronizationFactory.PLANE_MOVEMENTS.put(plane.getId(),
				new PlaneMovements(plane.getId(), plane.getId(), Arrival.AIRPORT.getX(), Arrival.AIRPORT.getY()));
	}
	
	@After
	public void endTest() {
		HibernateUtils.stop();
	}
	
	
	@Test
	public void testCheckTerminal() {
		boolean ret = departureManager.checkTerminal(plane);
		assertTrue(ret);
	}
	
	@Test
	public void testCheckTakeOffCurve() {
		boolean ret = departureManager.checkTakeOffCurve(plane);
		assertTrue(ret);
	}

	@Test
	public void testCheckTakeOffLane() {
		boolean ret = departureManager.checkTakeOffLane(plane);
		assertTrue(ret);
	}
	
	@Test
	public void testCheckAirportSpace() {
		boolean ret = departureManager.checkAirportSpace(plane);
		assertTrue(ret);
	}

}
