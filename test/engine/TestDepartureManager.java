/*
 * 
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class TestDepartureManager.
 */
public class TestDepartureManager {

	/** The departure manager. */
	DepartureManager departureManager;
	
	/** The plane. */
	Planes plane;
	
	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		HibernateUtils.start();
		SynchronizationFactory.define();
		departureManager = new DepartureManager();
		plane = new Planes(1, 0, 0, 0);

		SynchronizationFactory.PLANE_MOVEMENTS.put(plane.getId(),
				new PlaneMovements(plane.getId(), plane.getId(), Arrival.AIRPORT.getX(), Arrival.AIRPORT.getY()));
	}
	
	/**
	 * End test.
	 */
	@After
	public void endTest() {
		HibernateUtils.stop();
	}
	
	
	/**
	 * Test check terminal.
	 */
	@Test
	public void testCheckTerminal() {
		boolean ret = departureManager.checkTerminal(plane);
		assertTrue(ret);
	}
	
	/**
	 * Test check take off curve.
	 */
	@Test
	public void testCheckTakeOffCurve() {
		boolean ret = departureManager.checkTakeOffCurve(plane);
		assertTrue(ret);
	}

	/**
	 * Test check take off lane.
	 */
	@Test
	public void testCheckTakeOffLane() {
		boolean ret = departureManager.checkTakeOffLane(plane);
		assertTrue(ret);
	}
	
	/**
	 * Test check airport space.
	 */
	@Test
	public void testCheckAirportSpace() {
		boolean ret = departureManager.checkAirportSpace(plane);
		assertTrue(ret);
	}

}
