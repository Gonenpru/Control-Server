package threads;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import tables.Plane;
  
/**
 * This class consist exclusively of the simulator. 
 * It manage all the airport with a monitor mechanism. 
 * 
 * All the threads are going to be manage by a Thread Pool if 
 * there is any problem we will be able to manage. 
 * 
 * @author Andoni Enriquez
 * @author Asier Sampietro
 * @author Gorka Gonzalo
 * @since   1.0
 */


public class PlanesDynamicList {

	/**
	 * Global definitions
	 */
	private final int TOT_PLANE = 6;
	private final int TOT_TERMINAL = 4;
	private int N_PLANES_WAITING;

	public HashMap<String, Lock> locks;
	public ExecutorService executor = Executors.newCachedThreadPool();

	private HashMap<Integer, Condition> arrivalLock;
	private HashMap<Integer, Condition> departureLock;
	private Condition cAirSpace;
	private Condition cLandingLane;
	private Condition cLanding;
	private Condition cDepartureLane;

	// EMPTY/FREE == TRUE
	private boolean freeAirSpace, freeArrivalLane, freeArrivalCurve, freeDepartureCurve, freeDepartureLane;
	private HashMap<String, Boolean> freePista;

	/**
	 * Initialize all the values that are going to be used in the dynamic list.
	 * Creates all the locks for the monitors.
	 */
	public PlanesDynamicList() {

		this.N_PLANES_WAITING = 0;
		this.locks = new HashMap<String, Lock>();
		this.locks.put("Arr", new ReentrantLock());
		this.locks.put("Dep", new ReentrantLock());

		this.cDepartureLane = locks.get("Dep").newCondition();
		this.cAirSpace = this.locks.get("Arr").newCondition();
		this.cLanding = this.locks.get("Arr").newCondition();
		this.cLandingLane = this.locks.get("Arr").newCondition();

		this.arrivalLock = new HashMap<Integer, Condition>();
		this.departureLock = new HashMap<Integer, Condition>();
		for (int i = 1; i <= TOT_TERMINAL; i++) {
			this.arrivalLock.put(i, this.locks.get("Arr").newCondition());
			this.departureLock.put(i, this.locks.get("Dep").newCondition());
		}

		freePista = new HashMap<>();
		for (int i = 1; i <= TOT_TERMINAL; i++) {
			freePista.put("d" + i, true);
			freePista.put("a" + i, true);
			freePista.put("t" + i, true);
		}
		this.freeAirSpace = true;
		this.freeArrivalLane = true;
		this.freeArrivalCurve = true;
		this.freeDepartureCurve = true;
		this.freeDepartureLane = true;
	}

	/**
	 * Inserts a new plane inside the Thread Pool. First it creates a runnable
	 * with the plane and the PlanesDynamicList Then creates the Thread Finally
	 * the Thread is insert inside the Thread Pool
	 *
	 * @param Plane : a new plane that is going to be manage by the airport
	 */
	public void insertNewPlanes(Plane plane) {
		executor.submit(new Thread(new PlaneRunnable(plane, this)));
	}

	/**
	 * Controls when the plane is going to arrive. It manage all the process.
	 * There are 4 methods inside and the lock used is the "Arr" This method
	 * returns 0 if all the process works perfectly if not it returns a -1.
	 *
	 * @param Plane : a new plane that is going to be manage by the airport
	 * @return int that tells how the process works
	 */
	public int aterrizar(Plane plane) {
		locks.get("Arr").lock();
		try {
			checkAirSpace(plane);
			inLandingLane(plane);
			inLandingCurve(plane);
			goToTerminal(plane);

		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		} finally {
			locks.get("Arr").unlock();
		}
		return 0;
	}

	/**
	 * Controls when the plane is going to departure. It manage all the process.
	 * There are 3 methods inside and the lock used is the "Dep" This method
	 * returns 0 if all the process works perfectly if not it returns a -1.
	 *
	 * @param Plane : a new plane that is going to be manage by the airport
	 * @return int that tells how the process works
	 */
	public int despegar(Plane plane) {
		locks.get("Dep").lock();
		try {
			getFromTerminal(plane);
			inDepartureCurve(plane);
			inDepartureLane(plane);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		} finally {
			locks.get("Dep").unlock();
		}
		return 0;
	}

	/**
	 * Controls when the plane is going to check the air space.
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport
	 */
	public void checkAirSpace(Plane plane) throws InterruptedException{
		while (!freeAirSpace) {
			System.out.println("Espacio aereo lleno esperando avion: " + plane.getPlaneId());
			cAirSpace.await();
		}
		N_PLANES_WAITING++;
		if (N_PLANES_WAITING == TOT_PLANE) {
			freeAirSpace = false;
		}
		System.out.println("Aviones sobrevolando espacio aereo: " + N_PLANES_WAITING);
		Thread.sleep(1000);
	}

	/**
	 * Controls when the plane is going to the landing lane for the first time.
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport
	 */
	public void inLandingLane(Plane plane) throws InterruptedException {
		if (!freeArrivalLane) {
			System.out.println("Plane " + plane.getPlaneId() + " esperando aterrizaje.");
			cLandingLane.await();
		}

		N_PLANES_WAITING--;
		freeArrivalLane = false;
		System.out.println("Avion " + plane.getPlaneId() + " aterrizando.");

		freeAirSpace = true;
		cAirSpace.signalAll();
		Thread.sleep(1000);
	}

	/**
	 * Controls when the plane is going to the landing curve.
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport
	 */
	public void inLandingCurve(Plane plane) throws InterruptedException {
		if (!freeArrivalCurve) {
			System.out.println("Avion " + plane.getPlaneId() + " esperando en pista.");
			cLanding.await();
		}

		// PISTA ATERRIZAJE LIBRE
		freeArrivalLane = true;
		cLandingLane.signal();

		// EN CURVA ATERRIZAJE
		freeArrivalCurve = false;
		System.out.println("Plane " + plane.getPlaneId() + " en curva de aterrizaje.");
	}

	/**
	 * Controls when the plane is going to a lane that is near to the terminal
	 * It finds the terminal that the plane is looking for.
	 * If not it return the lane + 1
	 *
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport.
	 * @param Lane : The lane where the plane it is.
	 * @return int the lane + 1 if in the lane that it is at the moment is not the lane that
	 * the plane is looking for.
	 */
	public int arrivalControl(Plane plane, int lane) throws InterruptedException {
		if (!freePista.get("a" + lane)) {
			System.out.println("Plane " + plane.getPlaneId() + " esperando a P" + lane);
			arrivalLock.get(lane).await();
		}
		
		if (lane == 1) {
			freeArrivalCurve = true;
			cLanding.signal();
		} else {
			freePista.put("a" + (lane - 1), true);
			arrivalLock.get(lane - 1).signal();
		}

		freePista.put("a" + lane, false);
		System.out.println("Plane " + plane.getPlaneId() + " en P" + lane);

		if (plane.getTerminal() == lane) {
			freePista.put("a" + lane, true);
			arrivalLock.get(lane).signal();
			return lane;
		}
		return arrivalControl(plane, lane + 1);
	}

	/**
	 * Controls when the plane finds the terminal
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport.
	 */
	public void goToTerminal(Plane plane) throws InterruptedException {
		System.out.println("Plane " + plane.getPlaneId() + " aparcando en Terminal " + arrivalControl(plane, 1) + ".");
	}

	/**
	 * Controls when the plane is going to departure
	 * First is going to look the lane that is going to take 
	 *
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport.
	 * @param d : the departure lane 
	 * @return int : the departure lane + 1 if the next one is not the departure curve 
	 */
	public int departureControl(Plane plane, int d) throws InterruptedException {
		if (!freePista.get("d" + d)) {
			System.out.println("Plane " + plane.getPlaneId() + " esperando a entrar en D" + d);
			departureLock.get(d).await();
		}

		freePista.put("d" + d, false);
		System.out.println("Plane " + plane.getPlaneId() + " en D" + d);

		if (d != 1) {
			freePista.put("d" + (d - 1), true);
			departureLock.get(d - 1).signal();
		}

		if (d == TOT_TERMINAL) {
			System.out.println("Listo para curva avion " + plane.getPlaneId());
			return 0;
		} else {
			return departureControl(plane, ++d);
		}

	}

	/**
	 * Controls when the plane go away from the terminal
	 *
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport.
	 */
	public void getFromTerminal(Plane plane) throws InterruptedException {
		if (departureControl(plane, plane.getTerminal()) != 0) {
			throw new InterruptedException();
		}
	}

	/**
	 * Controls when the plane is in the departure curve.
	 * If the next lane it is free is goes to the next. If not it waits.
	 * 
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport.
	 */
	public void inDepartureCurve(Plane plane) throws InterruptedException {
		if (!freeDepartureCurve) {
			System.out.println("Plane " + plane.getPlaneId() + " esperando en D" + TOT_TERMINAL);
			departureLock.get(TOT_TERMINAL).await();
		}

		// A4 LIBRE
		freePista.put("d" + TOT_TERMINAL, true);
		departureLock.get(TOT_TERMINAL - 1).signal();

		// EN CURVA DESPEGUE
		freeDepartureCurve = false;
		System.out.println("Plane " + plane.getPlaneId() + " en Curva de Despegue");
	}

	/**
	 * Controls when the plane is in the departure lane.
	 * If it is free the lane departure from the airport.
	 * 
	 * @throws InterruptedException if the Thread is interrupted 
	 * @param Plane : a new plane that is going to be manage by the airport.
	 */
	public void inDepartureLane(Plane plane) throws InterruptedException {
		if (!freeDepartureLane) {
			System.out.println("Plane " + plane.getPlaneId() + " esperando en Curva de Despegue");
			cDepartureLane.await();
		}

		// CURVA DESPEGUE LIBRE
		freeDepartureCurve = true;
		departureLock.get(4).signal();

		// EN PISTA DESPEGUE
		freeDepartureLane = false;
		System.out.println("Plane " + plane.getPlaneId() + " en Pista de Despegue");

		// SALIENDO DE ESPACIO AEREO
		freeDepartureLane = true;
		cDepartureLane.signal();
		System.out.println("Plane " + plane.getPlaneId() + " salio del Espacio Aereo");
	}
}
