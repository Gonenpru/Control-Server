package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import tables.Plane;
import threads.PlaneRunnable;
import threads.PlanesDynamicList;

public class RunnableTest {

	private PlaneRunnable planeRunnable_a, planeRunnable_d;
	private PlanesDynamicList planesDynamicList;
	private Plane plane_arrive, plane_departure;
	private ExecutorService service;
	
	@Before 
	public void setUp(){
		planesDynamicList = new PlanesDynamicList();
		plane_arrive = new Plane(0, 1, "ARRIVE");
		plane_departure = new Plane(1, 1, "DEPARTURE");
		planeRunnable_a = new PlaneRunnable(plane_arrive, planesDynamicList);
		planeRunnable_d = new PlaneRunnable(plane_departure, planesDynamicList);
		service = Executors.newSingleThreadExecutor();
	}
	
	@Test
	public void checkRun_Arrive() {
		service.execute(planeRunnable_a);
	}
	
	@Test
	public void checkRun_Departure() {
		service.execute(planeRunnable_d);
	}
}
