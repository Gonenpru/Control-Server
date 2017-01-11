package test;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

import tables.Plane;
import threads.PlanesDynamicList;


public class SimulatorTest {

	private PlanesDynamicList planesDynamicList;
	private Plane plane_arrive, plane_departure;
	
	@Before
	public void setUp(){
		planesDynamicList = new PlanesDynamicList();
		plane_arrive = new Plane(0, 1, "ARRIVE");
		plane_departure = new Plane(0, 1, "DEPARTURE");
	}
	
	@Test 
	public void test_insertPlane() {
		planesDynamicList.insertNewPlanes(new Plane(100,1,"ARRIVE"));
	}
				
	@Test
	public void test_checkAterrizar_full() throws InterruptedException{
		assertEquals(0, planesDynamicList.aterrizar(plane_arrive));
	}
	
	@Test
	public void test_checkDespegar_full() throws InterruptedException{
		assertEquals(0, planesDynamicList.despegar(plane_departure));
	}

}
