package Prueba;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	final Lock lock = new ReentrantLock();

	int i = 0;
	int numeroTotalAviones = 6;

	final Condition espacio_aereo = lock.newCondition();
	final Condition pista_aterrizaje = lock.newCondition();
	final Condition espera_aterrizaje = lock.newCondition();

	final Condition p1 = lock.newCondition();
	final Condition p2 = lock.newCondition();
	final Condition p3 = lock.newCondition();
	final Condition p4 = lock.newCondition();

	// VACIO = TRUE
	boolean espacioAereo = true;
	boolean pistaAterrizaje = true;
	boolean esperaAterrizaje = true;
	boolean p1_libre = true;
	boolean p2_libre = true;
	boolean p3_libre = true;
	boolean p4_libre = true;

	public void aterrizar(Plane plane) throws InterruptedException {

		while (!espacioAereo) {
			System.out.println("Aeropuerto lleno esperando avion: " + plane.id);
			lock.lock();
			espacio_aereo.await();
			lock.unlock();
		}

		lock.lock();
		i++;
		lock.unlock();
		System.out.println("Entra avion, numero de aviones volando : " + i);
		
		if (i == numeroTotalAviones) {
			espacioAereo = false;
		}
		lock.lock();
		while (!pistaAterrizaje) {
			System.out.println("Avion esperando a aterrizar: " + plane.id);
			lock.lock();
			pista_aterrizaje.await();
			lock.unlock();
		}

		// EN PISTA
		i--;
		pistaAterrizaje = false;
		System.out.println("Aterrizando avion, num aviones volando : " + i);
		lock.unlock();
		if (!espacioAereo) {
			lock.lock();
			espacio_aereo.signal();
			lock.unlock();
			espacioAereo = true;
		}
		
		System.out.println("En pista de aterrizaje: " + plane.id);
		
		while (!esperaAterrizaje) {
			lock.lock();
			espera_aterrizaje.await();
			lock.unlock();
		}

		// PISTA ATERRIZAJE LIBRE
		pistaAterrizaje = true;
		lock.lock();
		pista_aterrizaje.signal();
		lock.unlock();
		
		// EN ESPERA ATERRIZAJE
		esperaAterrizaje = false;
		System.out.println("Espera Aterrizaje : " + plane.id);

		while (!p1_libre) {
			System.out.println("Esperando en p1 : id -> " + plane.id);
			lock.lock();
			p1.await();
			lock.unlock();
		}

		// ESPERA ATERRIZAJE LIBRE
		esperaAterrizaje = true;
		lock.lock();
		espera_aterrizaje.signal();
		lock.unlock();

		// EN P1
		p1_libre = false;
		System.out.println("P1 avion : " + plane.id);

		// APARCAR EN TERMINAL
		buscarTerminalAparcar(plane);
	}

	public void buscarTerminalAparcar(Plane plane) throws InterruptedException {
		boolean end = false;
		while (!end) {

			if (plane.terminal == 1) {
				System.out.println("Aparcando en Terminal 1 avion : " +plane.id);
				// aparcarTer1
				lock.lock();
				p1.signal();
				lock.unlock();
				p1_libre = true;
				Thread.sleep(4000);
				break;
			}

			while (!p2_libre) {
				lock.lock();
				p2.await();
				lock.unlock();
			}

			p1_libre = true;
			lock.lock();
			p1.signal();
			lock.unlock();
			p2_libre = false;
			System.out.println("P2 avion : " + plane.id);
			if (plane.terminal == 2) {
				System.out.println("Aparcando en Terminal 2 avion : " +plane.id);
				// aparcarTer2
				p2_libre = true;
				lock.lock();
				p2.signal();
				lock.unlock();
				break;
			}

			while (!p3_libre) {
				lock.lock();
				p3.await();
				lock.unlock();
			}

			p2_libre = true;
			lock.lock();
			p2.signal();
			lock.unlock();
			p3_libre = false;
			System.out.println("P3 avion : " + plane.id);
			if (plane.terminal == 3) {
				System.out.println("Aparcando en Terminal 3 avion : " +plane.id);
				// aparcarTer3
				p3_libre = true;
				lock.lock();
				p3.signal();
				lock.unlock();
				break;
			}

			while (!p4_libre) {
				lock.lock();
				p4.await();
				lock.unlock();
			}

			p3_libre = true;
			lock.lock();
			p3.signal();
			lock.unlock();
			p4_libre = false;
			System.out.println("P4 avion : " + plane.id);
			if (plane.terminal == 4) {
				System.out.println("Aparcando en Terminal 4 avion : " +plane.id);
				// aparcarTer3
				p4_libre = true;
				lock.lock();
				p4.signal();
				lock.unlock();
				break;
			}
		}
	}

	public void despegar(Plane plane) {
		
	}
	public class PlaneThread implements Runnable {

		Plane plane;

		public PlaneThread(int id, int terminal) {
			plane = new Plane(id, terminal);
		}

		@Override
		public void run() {
			try {
				aterrizar(plane);
				// EN EL PARKING
				despegar(plane);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	static PlaneThread plane1, plane2, plane3, plane4, plane5, plane6, plane7, plane8, plane9;
	static Thread thread1, thread2, thread3, thread4, thread5, thread6, thread7, thread8, thread9;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Main a = new Main();
		a.exec();
	}

	private void exec() {
		// TODO Auto-generated method stub
		plane1 = new PlaneThread(0, 1);
		plane2 = new PlaneThread(1, 3);
		plane3 = new PlaneThread(2, 3);
		plane4 = new PlaneThread(3, 3);
		plane5 = new PlaneThread(4, 1);
		plane6 = new PlaneThread(5, 1);
		plane7 = new PlaneThread(6, 1);
		plane8 = new PlaneThread(7, 2);
		plane9 = new PlaneThread(8, 4);

		thread1 = new Thread(plane1);
		thread2 = new Thread(plane2);
		thread3 = new Thread(plane3);
		thread4 = new Thread(plane4);
		thread5 = new Thread(plane5);
		thread6 = new Thread(plane6);
		thread7 = new Thread(plane7);
		thread8 = new Thread(plane8);
		thread9 = new Thread(plane9);

		thread1.start();
		thread2.start();
		thread3.start();
		/*
		thread4.start();
		thread5.start();
		thread6.start();
		thread7.start();
		thread8.start();
		thread9.start();
		*/
	}
}