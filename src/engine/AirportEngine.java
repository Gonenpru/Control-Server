/*
 * 
 */
package engine;

import db_items.Planes;
import exceptions.ProblemHappenedException;

/**
 * The class AirportEngine takes a plane through the airport.
 */
public class AirportEngine {

	/* Finals */
	/** Maximum planes on the airport. */
	public final static int MAX_PLANES = 6;
	
	/** Total amount of terminals. */
	public final static int TOT_TERMINAL = 4;

	/* Statics */
	
	/* Variables */
	/** The arrival manager. */
	private ArrivalManager arrivalManager;
	
	/** The departure manager. */
	private DepartureManager departureManager;

	/**
	 * Initiates the variables.
	 */
	public AirportEngine() {
		arrivalManager = new ArrivalManager();
		departureManager = new DepartureManager();

	}

	/**
	 * Guides a plane through the landing stage until the terminal he is defined
	 *
	 * @param plane the plane
	 * @return 0, if everything is right
	 */
	public synchronized int land(Planes plane) {
		try {
			if (!arrivalManager.checkAirportSpace(plane))
				throw new ProblemHappenedException("Airport Space Error");
			if (!arrivalManager.checkLandingLane(plane))
				throw new ProblemHappenedException("Landing Lane Error");
			if (!arrivalManager.checkLandingCurve(plane))
				throw new ProblemHappenedException("Landing Curve Error");
			if (!arrivalManager.checkTerminal(plane))
				throw new ProblemHappenedException("Terminal Error");

		} catch (ProblemHappenedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Guides a plane through the take of stage from the terminal it is.
	 *
	 * @param plane the plane
	 * @return 0, if everything is right
	 */
	public synchronized int takeOff(Planes plane) {
		try {
			if (!departureManager.checkTerminal(plane))
				throw new ProblemHappenedException("Terminal Error");
			if (!departureManager.checkTakeOffCurve(plane))
				throw new ProblemHappenedException("Take off curve Error");
			if (!departureManager.checkTakeOffLane(plane))
				throw new ProblemHappenedException("Take off lane Error");
			if (!departureManager.checkAirportSpace(plane))
				throw new ProblemHappenedException("TLeaving airport Error");

		} catch (ProblemHappenedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
