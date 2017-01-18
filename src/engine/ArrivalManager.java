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
import threads.Launcher;
import utils.HibernateUtils;

public class ArrivalManager {

	private MovesAction movesAction;

	public ArrivalManager() {
		movesAction = new MovesAction();
	}

	public boolean checkAirportSpace(Planes plane) {
		int id;
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			plane.doMovement();
			if (Launcher.SEMAPHORES.get(Sectors.AIRPORT).availablePermits() == 0)
				System.out.println("Waiting airport to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(Sectors.AIRPORT).acquire();

			System.out.println("In the airport | Plane: " + plane.getId());
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			id = movesAction.getLastId();
			Launcher.PLANE_MOVEMENTS.put(plane.getId(),
					new PlaneMovements(id, plane.getId(), Arrival.AIRPORT.getX(), Arrival.AIRPORT.getY()));
			Transaction trans = session.beginTransaction();
			session.save(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
		} catch (InterruptedException e) {
			return false;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return true;
	}

	public boolean checkLandingLane(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			if (Launcher.SEMAPHORES.get(Arrival.LANDING_LANE).availablePermits() == 0)
				System.out.println("Waiting landing lane to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(Arrival.LANDING_LANE).acquire();

			System.out.println("In landing lane | Plane: " + plane.getId());

			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Arrival.LANDING_LANE.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Arrival.LANDING_LANE.getY());
			Transaction trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
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

	public boolean checkLandingCurve(Planes plane) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			if (Launcher.SEMAPHORES.get(Arrival.LANDING_CURVE).availablePermits() == 0)
				System.out.println("Waiting landing curve to get free | Plane: " + plane.getId());

			Launcher.SEMAPHORES.get(Arrival.LANDING_CURVE).acquire();

			System.out.println("In landing curve | Plane: " + plane.getId());
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(Arrival.LANDING_CURVE.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(Arrival.LANDING_CURVE.getY());
			Transaction trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			Launcher.SEMAPHORES.get(Arrival.LANDING_LANE).release();
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

	public boolean checkTerminal(Planes plane) {
		try {
			System.out.println(
					"Parking in terminal " + isPlanesTerminal(Arrival.TERMINAL1, plane) + " | Plane: " + plane.getId());
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	private int isPlanesTerminal(Arrival currentLane, Planes plane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			if (Launcher.SEMAPHORES.get(currentLane).availablePermits() == 0)
				System.out.println("Waiting " + currentLane + " to get free | Plane: " + plane.getId());
			Launcher.SEMAPHORES.get(currentLane).acquire();

			System.out.println("In " + currentLane + " | Plane: " + plane.getId());
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(currentLane.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(currentLane.getY());
			Transaction trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			switch (currentLane) {
			case TERMINAL1:
				Launcher.SEMAPHORES.get(Arrival.LANDING_CURVE).release();
				break;
			case TERMINAL2:
				Launcher.SEMAPHORES.get(Arrival.TERMINAL1).release();
				break;
			case TERMINAL3:
				Launcher.SEMAPHORES.get(Arrival.TERMINAL2).release();
				break;
			case TERMINAL4:
				Launcher.SEMAPHORES.get(Arrival.TERMINAL3).release();
				break;
			default:
				throw new InterruptedException();
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
				throw new InterruptedException();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return isPlanesTerminal(currentLane, plane);
	}

	private void parkPlane(Planes plane, Arrival currentLane) throws InterruptedException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			Terminal ter = Terminal.getTerminal(plane.getTerminal());
			Launcher.LOCKS.get(Sectors.AIRPORT).lock();
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosx(ter.getX());
			Launcher.PLANE_MOVEMENTS.get(plane.getId()).setPosy(ter.getY());
			Transaction trans = session.beginTransaction();
			session.update(Launcher.PLANE_MOVEMENTS.get(plane.getId()));
			trans.commit();
			Launcher.LOCKS.get(Sectors.AIRPORT).unlock();
			Launcher.SEMAPHORES.get(currentLane).release();
			plane.doMovement();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
