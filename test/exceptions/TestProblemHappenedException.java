package exceptions;

import org.junit.Before;
import org.junit.Test;

import db_items.Planes;
import engine.ArrivalManager;
import threads.SynchronizationFactory;
import utils.HibernateUtils;

public class TestProblemHappenedException {
	
	ArrivalManager arrivalMananger;
	
	@Before
	public void initTest(){
		HibernateUtils.start();
		SynchronizationFactory.define();
		arrivalMananger = new ArrivalManager();
	}
	
	@Test(expected=ProblemHappenedException.class)
	public void testProblemHappenedException(){
		arrivalMananger.checkAirportSpace(new Planes());
	}
	

}
