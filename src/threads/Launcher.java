package threads;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import db_items.PlaneMovements;
import db_items.Planes;
import engine.Enumerated.Sectors;
import utils.HibernateUtils;

public class Launcher {

	@SuppressWarnings("rawtypes")
	public static HashMap<Enum, Semaphore> SEMAPHORES;
	public static HashMap<Sectors, Lock> LOCKS;
	public static HashMap<Integer, PlaneMovements> PLANE_MOVEMENTS;
	public static ExecutorService threadPool;
	public static int AIRPORT_PLANES;
	
	private Random rnd;

	public static void main(String[] args) {
		new Launcher(9);
	}

	public Launcher(int planeAmmount) {
		threadPool = Executors.newCachedThreadPool();

		try {
			HibernateUtils.start();
			PLANE_MOVEMENTS = new HashMap<>();
			rnd = new Random();
			for (int i = 1; i <= planeAmmount; i++){
				threadPool.submit(new Thread(new Planes(i, 0, 0, 0)));
				Thread.sleep((rnd.nextInt(10)+11)*1000);
			}
			threadPool.shutdown();
			while (!threadPool.isTerminated()) {
			}

		} catch (Exception e) {
		} finally {
			System.out.println("INFO: hibernate closing session");
			HibernateUtils.stop();
			System.exit(0);
		}
	}
}
