package threads;

import tables.Plane;

/**
 * This class is the Runnable that is used in the simulator. 
 * 
 * @author Andoni Enriquez
 * @author Asier Sampietro
 * @author Gorka Gonzalo
 * @since   1.0
 */

public class PlaneRunnable implements Runnable {

	/**
	 * Global definitions
	 */
	Plane plane;
	PlanesDynamicList ctx;

	/**
	 * Initialize all the values that are going to be used by the runnable.
	 */
	public PlaneRunnable(Plane p, PlanesDynamicList ctx) {
		this.plane = p;
		this.ctx = ctx;
	}

	/**
	 * The run method is executed by the runnable so first is looks where 
	 * the plane is going to do (Departure or Arrival) and it send it to be manage 
	 * by the PlanesDynamicList.java
	 * 
	 * When the thread finishes this is interrupted to ensure that it finished
	 */
	public void run() {
		if (this.plane.getModeStatus().equals("ARRIVE")) ctx.aterrizar(plane);
		else if (this.plane.getModeStatus().equals("DEPARTURE")) ctx.despegar(plane);
		
		System.out.println("Interrupting");
		Thread.currentThread().interrupt();
		
		if (Thread.currentThread().isInterrupted()){
			System.out.println("Finished : " + Thread.currentThread().getName());
		}
	}
}