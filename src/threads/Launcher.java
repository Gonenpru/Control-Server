/*
 * 
 */
package threads;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import db_items.Planes;
import utils.HibernateUtils;

// TODO: Auto-generated Javadoc
/**
 * The main is in this class.
 */
public class Launcher {

	/** The thread pool. */
	final public static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private Random rnd;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		new Launcher(9);
	}

	/**
	 * Constructor of launcher.
	 *
	 * @param planeAmount the amount of planes that will pass through the airport
	 */
	public Launcher(int planeAmount) {
		SynchronizationFactory.define();
		try {
			HibernateUtils.start();
			rnd = new Random();
			for (int i = 1; i <= planeAmount; i++){
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
