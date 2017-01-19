/*
 * 
 */
package threads;

import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import db_items.PlaneMovements;
import engine.AirportEngine;
import engine.Enumerated.Arrival;
import engine.Enumerated.Departure;
import engine.Enumerated.Sectors;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Synchronization objects.
 */
public class SynchronizationFactory {

	/** The semaphores. */
	@SuppressWarnings("rawtypes")
	public static HashMap<Enum, Semaphore> SEMAPHORES;
	
	/** The locks. */
	public static HashMap<Sectors, Lock> LOCKS;
	
	/** The plane movements. */
	public static HashMap<Integer, PlaneMovements> PLANE_MOVEMENTS;
	
	/**
	 * Define.
	 */
	public static void define() {
		SynchronizationFactory.LOCKS = new HashMap<>();
		SynchronizationFactory.LOCKS.put(Sectors.AIRPORT, new ReentrantLock());

		SynchronizationFactory.PLANE_MOVEMENTS = new HashMap<>();
		
		SynchronizationFactory.SEMAPHORES = new HashMap<>();
		
		SynchronizationFactory.SEMAPHORES.put(Sectors.AIRPORT, new Semaphore(AirportEngine.MAX_PLANES));
		
		SynchronizationFactory.SEMAPHORES.put(Arrival.LANDING_LANE, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.LANDING_CURVE, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.TERMINAL1, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.TERMINAL2, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.TERMINAL3, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.TERMINAL4, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.TERMINAL1, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.TERMINAL2, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.TERMINAL3, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Arrival.TERMINAL4, new Semaphore(1));
		
		SynchronizationFactory.SEMAPHORES.put(Departure.TAKE_OFF_LANE, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TAKE_OFF_CURVE, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TERMINAL1, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TERMINAL2, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TERMINAL3, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TERMINAL4, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TERMINAL1, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TERMINAL2, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TERMINAL3, new Semaphore(1));
		SynchronizationFactory.SEMAPHORES.put(Departure.TERMINAL4, new Semaphore(1));
	}

}
