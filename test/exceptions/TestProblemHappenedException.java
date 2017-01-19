/*
 * 
 */
package exceptions;

import org.junit.Before;
import org.junit.Test;

import db_items.Planes;
import engine.ArrivalManager;
import threads.SynchronizationFactory;
import utils.HibernateUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TestProblemHappenedException.
 */
public class TestProblemHappenedException {
	
	/** The arrival mananger. */
	ArrivalManager arrivalMananger;
	
	/**
	 * Inits the test.
	 */
	@Before
	public void initTest(){
		HibernateUtils.start();
		SynchronizationFactory.define();
		arrivalMananger = new ArrivalManager();
	}
	
	/**
	 * Test problem happened exception.
	 */
	@Test(expected=ProblemHappenedException.class)
	public void testProblemHappenedException(){
		arrivalMananger.checkAirportSpace(new Planes());
	}
	

}
