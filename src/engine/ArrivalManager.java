/*
 * 
 */
package engine;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import action.MovesAction;
import db_items.PlaneMovements;
import db_items.Planes;
import engine.Enumerated.Arrival;
import engine.Enumerated.Sectors;
import engine.Enumerated.Terminal;
import exceptions.ProblemHappenedException;
import threads.SynchronizationFactory;
import utils.HibernateUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ArrivalManager.
 */
public class ArrivalManager {

	/** The moves action. */
	private MovesAction movesAction;

	/**
	 * Instantiates a new arrival manager.
	 */
	public ArrivalManager() {
		movesAction = new MovesAction();
	}

	/**
	 * Check airport space.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	public boolean checkAirportSpace(Planes plane) {
		int id;
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			plane.doMovement();
			if (SynchronizationFactory.SEMAPHORES.get(Sectors.AIRPORT).availablePermits() == 0)
				System.out.println("Waiting airport to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(Sectors.AIRPORT).acquire();

			System.out.println("In the airport | Plane: " + plane.getId());
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			id = movesAction.getLastId();
			SynchronizationFactory.PLANE_MOVEMENTS.put(plane.getId(),
					new PlaneMovements(id, plane.getId(), Arrival.AIRPORT.getX(), Arrival.AIRPORT.getY()));
			Transaction trans = session.beginTransaction();
			session.save(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
		} catch (Exception e) {
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	/**
	 * Check landing lane.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	public boolean checkLandingLane(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			if (SynchronizationFactory.SEMAPHORES.get(Arrival.LANDING_LANE).availablePermits() == 0)
				System.out.println("Waiting landing lane to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(Arrival.LANDING_LANE).acquire();

			System.out.println("In landing lane | Plane: " + plane.getId());

			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Arrival.LANDING_LANE.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Arrival.LANDING_LANE.getY());
			Transaction trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			plane.doMovement();

		} catch (InterruptedException e) {
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	/**
	 * Check landing curve.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	public boolean checkLandingCurve(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			if (SynchronizationFactory.SEMAPHORES.get(Arrival.LANDING_CURVE).availablePermits() == 0)
				System.out.println("Waiting landing curve to get free | Plane: " + plane.getId());

			SynchronizationFactory.SEMAPHORES.get(Arrival.LANDING_CURVE).acquire();

			System.out.println("In landing curve | Plane: " + plane.getId());
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Arrival.LANDING_CURVE.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Arrival.LANDING_CURVE.getY());
			Transaction trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			SynchronizationFactory.SEMAPHORES.get(Arrival.LANDING_LANE).release();
			plane.doMovement();
		} catch (InterruptedException e) {
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	/**
	 * Check terminal.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	public boolean checkTerminal(Planes plane) {
		try {
			System.out.println(
					"Parking in terminal " + isPlanesTerminal(Arrival.TERMINAL1, plane) + " | Plane: " + plane.getId());
		} catch (InterruptedException | ProblemHappenedException  e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if is planes terminal.
	 *
	 * @param currentLane the current lane
	 * @param plane the plane
	 * @return the int
	 * @throws InterruptedException the interrupted exception
	 * @throws ProblemHappenedException the problem happened exception
	 */
	private int isPlanesTerminal(Arrival currentLane, Planes plane)
			throws InterruptedException, ProblemHappenedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			if (SynchronizationFactory.SEMAPHORES.get(currentLane).availablePermits() == 0)
				System.out.println("Waiting " + currentLane + " to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(currentLane).acquire();

			System.out.println("In " + currentLane + " | Plane: " + plane.getId());
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(currentLane.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(currentLane.getY());
			Transaction trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			switch (currentLane) {
			case TERMINAL1:
				SynchronizationFactory.SEMAPHORES.get(Arrival.LANDING_CURVE).release();
				break;
			case TERMINAL2:
				SynchronizationFactory.SEMAPHORES.get(Arrival.TERMINAL1).release();
				break;
			case TERMINAL3:
				SynchronizationFactory.SEMAPHORES.get(Arrival.TERMINAL2).release();
				break;
			case TERMINAL4:
				SynchronizationFactory.SEMAPHORES.get(Arrival.TERMINAL3).release();
				break;
			default:
				throw new ProblemHappenedException("Terminal wrongly introduced");
			}
			plane.doMovement();

			int id = plane.getTerminal();
			if (id == currentLane.ordinal()) {
				parkPlane(plane, currentLane);
				return plane.getTerminal();
			}

			switch (currentLane) {
			case TERMINAL1:
				currentLane = Arrival.TERMINAL2;
				break;
			case TERMINAL2:
				currentLane = Arrival.TERMINAL3;
				break;
			case TERMINAL3:
				currentLane = Arrival.TERMINAL4;
				break;
			default:
				throw new ProblemHappenedException("Terminal wrongly introduced");
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return isPlanesTerminal(currentLane, plane);
	}

	/**
	 * Park plane.
	 *
	 * @param plane the plane
	 * @param currentLane the current lane
	 * @throws InterruptedException the interrupted exception
	 */
	private void parkPlane(Planes plane, Arrival currentLane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			Terminal ter = Terminal.getTerminal(plane.getTerminal());
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(ter.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(ter.getY());
			Transaction trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			SynchronizationFactory.SEMAPHORES.get(currentLane).release();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
