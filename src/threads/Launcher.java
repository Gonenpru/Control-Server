package threads;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import db_items.Planes;
import utils.HibernateUtils;

public class Launcher {

	public static ExecutorService threadPool;
	
	private Random rnd;

	public static void main(String[] args) {
		new Launcher(9);
	}

	public Launcher(int planeAmmount) {
		threadPool = Executors.newCachedThreadPool();
		SynchronizationFactory.define();
		try {
			HibernateUtils.start();
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
