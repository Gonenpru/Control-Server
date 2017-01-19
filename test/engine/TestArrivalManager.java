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

public class TestArrivalManager {

	public ArrivalManager arrivalManager;
	Planes plane;

	@Before
	public void initTest() {
		HibernateUtils.start();
		SynchronizationFactory.define();
		arrivalManager = new ArrivalManager();
		plane = new Planes(5, 0, 0, 0);
	}
	
	@After
	public void endTest() {
		HibernateUtils.stop();
	}

	@Test
	public void testCheckAirportSpace() {
		boolean ret = arrivalManager.checkAirportSpace(plane);
		assertTrue(ret);
	}
	
	@Test
	public void testCheckLandingLane() {
		SynchronizationFactory.PLANE_MOVEMENTS.put(plane.getId(),
				new PlaneMovements(plane.getId(), plane.getId(), Arrival.AIRPORT.getX(), Arrival.AIRPORT.getY()));
		boolean ret = arrivalManager.checkLandingLane(plane);
		assertTrue(ret);
	}
	
	@Test
	public void testCheckLandingCurve() {
		SynchronizationFactory.PLANE_MOVEMENTS.put(plane.getId(),
				new PlaneMovements(plane.getId(), plane.getId(), Arrival.AIRPORT.getX(), Arrival.AIRPORT.getY()));
		boolean ret = arrivalManager.checkLandingCurve(plane);
		assertTrue(ret);
	}

	@Test
	public void testCheckTerminal(){
		SynchronizationFactory.PLANE_MOVEMENTS.put(plane.getId(),
				new PlaneMovements(plane.getId(), plane.getId(), Arrival.AIRPORT.getX(), Arrival.AIRPORT.getY()));
		boolean ret = arrivalManager.checkTerminal(plane);
		assertTrue(ret);
	}

	

}
