package engine;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import db_items.Planes;
import engine.Enumerated.Departure;
import engine.Enumerated.Sectors;
import engine.Enumerated.Terminal;
import threads.Launcher;
import utils.HibernateUtils;

public class DepartureManager {

	private final static int NONE = 0;

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

	private void terminal1(Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trans;

		try {
			if (Launcher.SEMAPHORES.get(Departure.TERMINAL1).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TERMINAL1 + " to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(Departure.TERMINAL1).acquire();

			System.out.println("In " + Departure.TERMINAL1 + " | Plane: " + plane.getId());

			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Terminal.pT1.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Terminal.pT1.getY());
			trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			plane.doMovement();
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TERMINAL1.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TERMINAL1.getY());
			trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void terminal2(Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trans;

		try {
			if (plane.getTerminal() == Departure.TERMINAL2.ordinal()) {
				if (Launcher.SEMAPHORES.get(Departure.TERMINAL1).availablePermits() == NONE)
					System.out.println("Waiting " + Departure.TERMINAL1 + " to get free | Plane: " + plane.getId());
				Launcher.SEMAPHORES.get(Departure.TERMINAL1).acquire();
			}
			if (Launcher.SEMAPHORES.get(Departure.TERMINAL2).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TERMINAL2 + " to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(Departure.TERMINAL2).acquire();

			System.out.println("In " + Departure.TERMINAL2 + " | Plane: " + plane.getId());

			if (plane.getTerminal() == Departure.TERMINAL2.ordinal()) {
				Launcher.LOCKS.get(Sectors.AIRPORT).lock();
				Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Terminal.pT2.getX());
				Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Terminal.pT2.getY());
				trans = session.beginTransaction();
				session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
				trans.commit();
				Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
				plane.doMovement();
			}
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TERMINAL2.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TERMINAL2.getY());
			trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void terminal3(Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trans;

		try {
			if (plane.getTerminal() == Departure.TERMINAL3.ordinal()) {
				if (Launcher.SEMAPHORES.get(Departure.TERMINAL2).availablePermits() == NONE)
					System.out.println("Waiting " + Departure.TERMINAL2 + " to get free | Plane: " + plane.getId());
				Launcher.SEMAPHORES.get(Departure.TERMINAL2).acquire();
			}
			if (Launcher.SEMAPHORES.get(Departure.TERMINAL3).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TERMINAL3 + " to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(Departure.TERMINAL3).acquire();

			System.out.println("In " + Departure.TERMINAL3 + " | Plane: " + plane.getId());

			if (plane.getTerminal() == Departure.TERMINAL3.ordinal()) {
				Launcher.LOCKS.get(Sectors.AIRPORT).lock();
				Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Terminal.pT3.getX());
				Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Terminal.pT3.getY());
				trans = session.beginTransaction();
				session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
				trans.commit();
				Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
				plane.doMovement();
			}
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TERMINAL3.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TERMINAL3.getY());
			trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			if (!(plane.getTerminal() == Departure.TERMINAL3.ordinal()))
				Launcher.SEMAPHORES.get(Departure.TERMINAL1).release();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void terminal4(Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trans;

		try {
			if (plane.getTerminal() == Departure.TERMINAL4.ordinal()) {
				if (Launcher.SEMAPHORES.get(Departure.TERMINAL3).availablePermits() == NONE)
					System.out.println("Waiting " + Departure.TERMINAL3 + " to get free | Plane: " + plane.getId());
				Launcher.SEMAPHORES.get(Departure.TERMINAL3).acquire();
			}
			if (Launcher.SEMAPHORES.get(Departure.TERMINAL4).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TERMINAL4 + " to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(Departure.TERMINAL4).acquire();

			System.out.println("In " + Departure.TERMINAL4 + " | Plane: " + plane.getId());

			if (plane.getTerminal() == Departure.TERMINAL4.ordinal()) {
				Launcher.LOCKS.get(Sectors.AIRPORT).lock();
				Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Terminal.pT4.getX());
				Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Terminal.pT4.getY());
				trans = session.beginTransaction();
				session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
				trans.commit();
				Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
				plane.doMovement();
			}
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TERMINAL4.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TERMINAL4.getY());
			trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			if (!(plane.getTerminal() == Departure.TERMINAL4.ordinal()))
				Launcher.SEMAPHORES.get(Departure.TERMINAL2).release();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public boolean checkTakeOffCurve(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();

		try {
			if (Launcher.SEMAPHORES.get(Departure.TAKE_OFF_CURVE).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TAKE_OFF_CURVE + " to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(Departure.TAKE_OFF_CURVE).acquire();

			System.out.println("In " + Departure.TAKE_OFF_CURVE + " | Plane: " + plane.getId());
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TAKE_OFF_CURVE.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TAKE_OFF_CURVE.getY());
			Transaction trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			Launcher.SEMAPHORES.get(Departure.TERMINAL3).release();
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

	public boolean checkTakeOffLane(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();

		try {
			if (Launcher.SEMAPHORES.get(Departure.TAKE_OFF_LANE).availablePermits() == NONE)
				System.out.println("Waiting " + Departure.TAKE_OFF_LANE + " to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(Departure.TAKE_OFF_LANE).acquire();

			System.out.println("In " + Departure.TAKE_OFF_LANE + " | Plane: " + plane.getId());
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Departure.TAKE_OFF_LANE.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Departure.TAKE_OFF_LANE.getY());
			Transaction trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			Launcher.SEMAPHORES.get(Departure.TERMINAL4).release();
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

	public boolean checkAirportSpace(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();

		try {
			Launcher.SEMAPHORES.get(Departure.TAKE_OFF_CURVE).release();
			Launcher.SEMAPHORES.get(Departure.TAKE_OFF_LANE).release();
			Launcher.SEMAPHORES.get(Sectors.AIRPORT).release();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setOut();
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Transaction trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return true;
	}

}
