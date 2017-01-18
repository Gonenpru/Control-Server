package threads;

import java.util.HashMap;
import java.util.Random;
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
	
	private Random rnd;

	public static void main(String[] args) {
		new Launcher();
	}

	public Launcher() {
		threadPool = Executors.newCachedThreadPool();

		try {
			HibernateUtils.start();
			session = HibernateUtils.getSessionFactory().openSession();
			PLANE_MOVEMENTS = new HashMap<>();
			rnd = new Random();
			for (int i = 1; i < 10; i++){
				threadPool.submit(new Thread(new Planes(i, 0, 0, 0)));
				Thread.sleep((rnd.nextInt(10)+11)*1000);
			}
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
