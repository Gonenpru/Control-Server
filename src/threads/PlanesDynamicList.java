package threads;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import classes.Plane;

public class PlanesDynamicList implements Runnable {

	ArrayList<Plane> planes;
	ArrayList<PlaneUpdater> planeUpdaters;
	ArrayList<Thread> threads;

	public PlanesDynamicList() {
		planes = new ArrayList<>();
		planeUpdaters = new ArrayList<>();
		threads = new ArrayList<>();
	}

	@Override
	public void run() {
		while (true) {
			for (int i = 0; i < planes.size(); i++) {
				if (!planes.get(i).isHandled()) {
					planeUpdaters.add(new PlaneUpdater(planes.get(i)));
					threads.add(new Thread(planeUpdaters.get(i)));
				}
			}

			try {
				wait(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (planes.size() > 9999) {
				break;
			}
		}
	}
}
