package engine;

import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import db_items.Planes;
import engine.Enumerated.Arrival;
import engine.Enumerated.Departure;
import engine.Enumerated.Sectors;
import exceptions.ProblemHappenedException;
import threads.Launcher;

public class AirportEngine {

	/* Finals */
	public final static int MAX_PLANES = 6;
	public final static int TOT_TERMINAL = 4;

	/* Statics */

	/* Variables */
	private ArrivalManager arrivalManager;
	private DepartureManager departureManager;

	public AirportEngine() {
		Launcher.LOCKS = new HashMap<>();
		Launcher.LOCKS.put(Sectors.AIRPORT, new ReentrantLock());

		Launcher.SEMAPHORES = new HashMap<>();
		
		Launcher.SEMAPHORES.put(Sectors.AIRPORT, new Semaphore(MAX_PLANES));
		
		Launcher.SEMAPHORES.put(Arrival.LANDING_LANE, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.LANDING_CURVE, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.TERMINAL1, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.TERMINAL2, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.TERMINAL3, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.TERMINAL4, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.TERMINAL1, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.TERMINAL2, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.TERMINAL3, new Semaphore(1));
		Launcher.SEMAPHORES.put(Arrival.TERMINAL4, new Semaphore(1));
		
		Launcher.SEMAPHORES.put(Departure.TAKE_OFF_LANE, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TAKE_OFF_CURVE, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TERMINAL1, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TERMINAL2, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TERMINAL3, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TERMINAL4, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TERMINAL1, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TERMINAL2, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TERMINAL3, new Semaphore(1));
		Launcher.SEMAPHORES.put(Departure.TERMINAL4, new Semaphore(1));

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
