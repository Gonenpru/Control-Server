/*
 * 
 */
package engine;

import db_items.Planes;
import exceptions.ProblemHappenedException;

// TODO: Auto-generated Javadoc
/**
 * The Class AirportEngine.
 */
public class AirportEngine {

	/** The Constant MAX_PLANES. */
	/* Finals */
	public final static int MAX_PLANES = 6;
	
	/** The Constant TOT_TERMINAL. */
	public final static int TOT_TERMINAL = 4;

	/* Statics */

	/** The arrival manager. */
	/* Variables */
	private ArrivalManager arrivalManager;
	
	/** The departure manager. */
	private DepartureManager departureManager;

	/**
	 * Instantiates a new airport engine.
	 */
	public AirportEngine() {
		arrivalManager = new ArrivalManager();
		departureManager = new DepartureManager();

	}

	/**
	 * Land.
	 *
	 * @param plane the plane
	 * @return the int
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
	 * Take off.
	 *
	 * @param plane the plane
	 * @return the int
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
