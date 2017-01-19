/*
 * 
 */
package engine;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import db_items.Planes;
import engine.Enumerated.Departure;
import engine.Enumerated.Sectors;
import engine.Enumerated.Terminal;
import threads.SynchronizationFactory;
import utils.HibernateUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class DepartureManager.
 */
public class DepartureManager {

	/** The Constant NONE. */
	private final static int NONE = 0;

	/**
	 * Check terminal.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	public boolean checkTerminal(Planes plane) {
		try {
			switch (plane.getTerminal()) {
			case 1:
				terminal1(plane);
			case 2:
				terminal2(plane);
			case 3:
				terminal3(plane);
			case 4:
				terminal4(plane);
			}
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	/**
	 * Terminal 1.
	 *
	 * @param plane the plane
	 * @throws InterruptedException the interrupted exception
	 */
	private void terminal1(Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trans;

		try {
			if (SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL1).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TERMINAL1 + " to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL1).acquire();

			System.out.println("In " + Departure.TERMINAL1 + " | Plane: " + plane.getId());

			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Terminal.pT1.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Terminal.pT1.getY());
			trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			plane.doMovement();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TERMINAL1.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TERMINAL1.getY());
			trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * Terminal 2.
	 *
	 * @param plane the plane
	 * @throws InterruptedException the interrupted exception
	 */
	private void terminal2(Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trans;

		try {
			if (plane.getTerminal() == Departure.TERMINAL2.ordinal()) {
				if (SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL1).availablePermits() == NONE)
					System.out.println("Waiting " + Departure.TERMINAL1 + " to get free | Plane: " + plane.getId());
				SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL1).acquire();
			}
			if (SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL2).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TERMINAL2 + " to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL2).acquire();

			System.out.println("In " + Departure.TERMINAL2 + " | Plane: " + plane.getId());

			if (plane.getTerminal() == Departure.TERMINAL2.ordinal()) {
				SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
				SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Terminal.pT2.getX());
				SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Terminal.pT2.getY());
				trans = session.beginTransaction();
				session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
				trans.commit();
				SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
				plane.doMovement();
			}
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TERMINAL2.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TERMINAL2.getY());
			trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * Terminal 3.
	 *
	 * @param plane the plane
	 * @throws InterruptedException the interrupted exception
	 */
	private void terminal3(Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trans;

		try {
			if (plane.getTerminal() == Departure.TERMINAL3.ordinal()) {
				if (SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL2).availablePermits() == NONE)
					System.out.println("Waiting " + Departure.TERMINAL2 + " to get free | Plane: " + plane.getId());
				SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL2).acquire();
			}
			if (SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL3).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TERMINAL3 + " to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL3).acquire();

			System.out.println("In " + Departure.TERMINAL3 + " | Plane: " + plane.getId());

			if (plane.getTerminal() == Departure.TERMINAL3.ordinal()) {
				SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
				SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Terminal.pT3.getX());
				SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Terminal.pT3.getY());
				trans = session.beginTransaction();
				session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
				trans.commit();
				SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
				plane.doMovement();
			}
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TERMINAL3.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TERMINAL3.getY());
			trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			if (!(plane.getTerminal() == Departure.TERMINAL3.ordinal()))
				SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL1).release();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * Terminal 4.
	 *
	 * @param plane the plane
	 * @throws InterruptedException the interrupted exception
	 */
	private void terminal4(Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trans;

		try {
			if (plane.getTerminal() == Departure.TERMINAL4.ordinal()) {
				if (SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL3).availablePermits() == NONE)
					System.out.println("Waiting " + Departure.TERMINAL3 + " to get free | Plane: " + plane.getId());
				SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL3).acquire();
			}
			if (SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL4).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TERMINAL4 + " to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL4).acquire();

			System.out.println("In " + Departure.TERMINAL4 + " | Plane: " + plane.getId());

			if (plane.getTerminal() == Departure.TERMINAL4.ordinal()) {
				SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
				SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Terminal.pT4.getX());
				SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Terminal.pT4.getY());
				trans = session.beginTransaction();
				session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
				trans.commit();
				SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
				plane.doMovement();
			}
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TERMINAL4.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TERMINAL4.getY());
			trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			if (!(plane.getTerminal() == Departure.TERMINAL4.ordinal()))
				SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL2).release();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * Check take off curve.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	public boolean checkTakeOffCurve(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();

		try {
			if (SynchronizationFactory.SEMAPHORES.get(Departure.TAKE_OFF_CURVE).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TAKE_OFF_CURVE + " to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(Departure.TAKE_OFF_CURVE).acquire();

			System.out.println("In " + Departure.TAKE_OFF_CURVE + " | Plane: " + plane.getId());
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TAKE_OFF_CURVE.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TAKE_OFF_CURVE.getY());
			Transaction trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL3).release();
			plane.doMovement();
		} catch (InterruptedException e) {
			return false;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return true;
	}

	/**
	 * Check take off lane.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	public boolean checkTakeOffLane(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();

		try {
			if (SynchronizationFactory.SEMAPHORES.get(Departure.TAKE_OFF_LANE).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TAKE_OFF_LANE + " to get free | Plane: " + plane.getId());
			SynchronizationFactory.SEMAPHORES.get(Departure.TAKE_OFF_LANE).acquire();

			System.out.println("In " + Departure.TAKE_OFF_LANE + " | Plane: " + plane.getId());
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TAKE_OFF_LANE.getX());
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TAKE_OFF_LANE.getY());
			Transaction trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
			SynchronizationFactory.SEMAPHORES.get(Departure.TERMINAL4).release();
			plane.doMovement();
		} catch (InterruptedException e) {
			return false;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return true;
	}

	/**
	 * Check airport space.
	 *
	 * @param plane the plane
	 * @return true, if successful
	 */
	public boolean checkAirportSpace(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();

		try {
			SynchronizationFactory.SEMAPHORES.get(Departure.TAKE_OFF_CURVE).release();
			SynchronizationFactory.SEMAPHORES.get(Departure.TAKE_OFF_LANE).release();
			SynchronizationFactory.SEMAPHORES.get(Sectors.AIRPORT).release();
			SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()).setOut();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).lock();
			Transaction trans = session.beginTransaction();
			session.update(SynchronizationFactory.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			SynchronizationFactory.LOCKS.get(Sectors.AIRPORT).unlock();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return true;
	}

}
