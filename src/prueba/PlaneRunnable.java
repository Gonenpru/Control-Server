package prueba;

public class PlaneRunnable implements Runnable {

	Plane plane;
	Main ctx;

	public PlaneRunnable(Plane p, Main ctx) {
		this.plane = p;
		this.ctx = ctx;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			ctx.aterrizar(plane);
			// EN EL PARKING
			ctx.despegar(plane);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}