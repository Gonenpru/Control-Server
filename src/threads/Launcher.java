package threads;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import org.hibernate.Session;

import db_items.PlaneMovements;
import db_items.Planes;
import engine.Enumerated.Sectors;
import utils.HibernateUtils;

public class Launcher {

	@SuppressWarnings("rawtypes")
	public static HashMap<Enum, Semaphore> SEMAPHORES;
	public static HashMap<Sectors, Lock> LOCKS;
	public static HashMap<Integer, PlaneMovements> PLANE_MOVEMENTS;
	public static int AIRPORT_PLANES;
	public static Session session;
	public static ExecutorService threadPool;

	public static void main(String[] args) {
		new Launcher();
	}

	public Launcher() {
		threadPool = Executors.newCachedThreadPool();

		try {
			HibernateUtils.start();
			session = HibernateUtils.getSessionFactory().openSession();
			PLANE_MOVEMENTS = new HashMap<>();
			threadPool.submit(new Thread(new Planes(1, 0, 0, 0)));
			threadPool.submit(new Thread(new Planes(2, 0, 0, 0)));
			threadPool.submit(new Thread(new Planes(3, 0, 0, 0)));
			threadPool.submit(new Thread(new Planes(4, 0, 0, 0)));
			threadPool.submit(new Thread(new Planes(5, 0, 0, 0)));
			threadPool.submit(new Thread(new Planes(6, 0, 0, 0)));
			threadPool.submit(new Thread(new Planes(7, 0, 0, 0)));
			threadPool.submit(new Thread(new Planes(8, 0, 0, 0)));
			threadPool.submit(new Thread(new Planes(9, 0, 0, 0)));
			while (!threadPool.isTerminated()) {
			}

		} catch (Exception e) {
		} finally {
			System.out.println("INFO: hibernate closing session");
			session.close();		
			System.exit(0);
		}
	}
}
