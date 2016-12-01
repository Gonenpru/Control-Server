package prueba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	final Lock LOCK = new ReentrantLock();
	final int TOTALPLANESALLOWED = 6, NUMBER_OF_TERMINALS = 4;

	int i = 0;

	ArrayList<PlaneRunnable> planeRunnables;
	ArrayList<Thread> planeThreads;

	Condition aerialSpaceLock = LOCK.newCondition();
	Condition landingLaneLock = LOCK.newCondition();
	Condition waitLandingLock = LOCK.newCondition();
	HashMap<Integer, Condition> laneLocks;

	// VACIO = TRUE
	boolean AerialSpace = true;
	boolean landingLane = true;
	boolean esperaAterrizaje = true;
	HashMap<Integer, Boolean> lanes;

	final Condition p1 = LOCK.newCondition();
	final Condition p2 = LOCK.newCondition();
	final Condition p3 = LOCK.newCondition();
	final Condition p4 = LOCK.newCondition();

	boolean p1_libre = true;
	boolean p2_libre = true;
	boolean p3_libre = true;
	boolean p4_libre = true;

	public void aterrizar(Plane plane) throws InterruptedException {

		while (!AerialSpace) {
			System.out.println("Aeropuerto lleno esperando avion: " + plane.getId());
			LOCK.lock();
			aerialSpaceLock.await();
			LOCK.unlock();
		}

		LOCK.lock();
		i++;
		LOCK.unlock();
		System.out.println("Entra avion, numero de aviones volando : " + i);

		if (i == TOTALPLANESALLOWED) {
			AerialSpace = false;
		}
		LOCK.lock();
		while (!landingLane) {
			System.out.println("Avion esperando a aterrizar: " + plane.getId());
			
			landingLaneLock.await();
			
		}
		// EN PISTA
		i--;
		landingLane = false;
		System.out.println("Aterrizando avion, num aviones volando : " + i);
		LOCK.unlock();
		if (!AerialSpace) {
			LOCK.lock();
			aerialSpaceLock.signal();
			LOCK.unlock();
			AerialSpace = true;
		}

		System.out.println("En pista de aterrizaje: " + plane.getId());

		while (!esperaAterrizaje) {
			LOCK.lock();
			waitLandingLock.await();
			LOCK.unlock();
		}

		// PISTA ATERRIZAJE LIBRE
		landingLane = true;
		LOCK.lock();
		landingLaneLock.signal();
		LOCK.unlock();

		// EN ESPERA ATERRIZAJE
		esperaAterrizaje = false;
		System.out.println("Espera Aterrizaje : " + plane.getId());

		LOCK.lock();
		while (!p1_libre) {
			System.out.println("Esperando en p1 : id -> " + plane.getId());
			
			p1.await();
			
		}
		LOCK.unlock();

		// ESPERA ATERRIZAJE LIBRE
		esperaAterrizaje = true;
		LOCK.lock();
		waitLandingLock.signal();
		LOCK.unlock();

		// EN P1
		p1_libre = false;
		System.out.println("P1 avion : " + plane.getId());

		// APARCAR EN TERMINAL
		buscarTerminalAparcar(plane);
	}

	public void buscarTerminalAparcar(Plane plane) throws InterruptedException {
		boolean end = false;
		while (!end) {

			if (plane.getTerminal() == 1) {
				System.out.println("Aparcando en Terminal 1 avion : " + plane.getId());
				// aparcarTer1
				LOCK.lock();
				p1.signal();
				LOCK.unlock();
				p1_libre = true;
				Thread.sleep(4000);
				break;
			}

			while (!p2_libre) {
				LOCK.lock();
				p2.await();
				LOCK.unlock();
			}

			p1_libre = true;
			LOCK.lock();
			p1.signal();
			LOCK.unlock();
			p2_libre = false;
			System.out.println("P2 avion : " + plane.getId());
			if (plane.getTerminal() == 2) {
				System.out.println("Aparcando en Terminal 2 avion : " + plane.getId());
				// aparcarTer2
				p2_libre = true;
				LOCK.lock();
				p2.signal();
				LOCK.unlock();
				break;
			}

			while (!p3_libre) {
				LOCK.lock();
				p3.await();
				LOCK.unlock();
			}

			p2_libre = true;
			LOCK.lock();
			p2.signal();
			LOCK.unlock();
			p3_libre = false;
			System.out.println("P3 avion : " + plane.getId());
			if (plane.getTerminal() == 3) {
				System.out.println("Aparcando en Terminal 3 avion : " + plane.getId());
				// aparcarTer3
				p3_libre = true;
				LOCK.lock();
				p3.signal();
				LOCK.unlock();
				break;
			}

			while (!p4_libre) {
				LOCK.lock();
				p4.await();
				LOCK.unlock();
			}

			p3_libre = true;
			LOCK.lock();
			p3.signal();
			LOCK.unlock();
			p4_libre = false;
			System.out.println("P4 avion : " + plane.getId());
			if (plane.getTerminal() == 4) {
				System.out.println("Aparcando en Terminal 4 avion : " + plane.getId());
				// aparcarTer3
				p4_libre = true;
				LOCK.lock();
				p4.signal();
				LOCK.unlock();
				break;
			}
		}
	}

	public void despegar(Plane plane) {

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main().exec();
	}

	private void exec() {

		Random rand = new Random();

		planeRunnables = new ArrayList<PlaneRunnable>();
		planeThreads = new ArrayList<Thread>();

		for (int i = 0; i < 8; i++)
			planeRunnables.add(new PlaneRunnable(new Plane(i, 1), this));

		for (PlaneRunnable p : planeRunnables)
			planeThreads.add(new Thread(p));

		for (Thread t : planeThreads)
			t.start();
	}
}