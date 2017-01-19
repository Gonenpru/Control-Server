package engine;

import db_items.Planes;
import exceptions.ProblemHappenedException;

public class AirportEngine {

	/* Finals */
	public final static int MAX_PLANES = 6;
	public final static int TOT_TERMINAL = 4;

	/* Statics */

	/* Variables */
	private ArrivalManager arrivalManager;
	private DepartureManager departureManager;

	public AirportEngine() {
		arrivalManager = new ArrivalManager();
		departureManager = new DepartureManager();

	}

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
