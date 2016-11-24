package threads;

import java.util.ArrayList;

import classes.Plane;

public class PlanesDynamicList implements Runnable{

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
		for(int i = 0; i < planes.size() ; i++){
			if(planes.get(i).isHandled()){
				planeUpdaters.add(new PlaneUpdater(planes.get(i)));
				threads.add(new Thread(planeUpdaters.get(i)));
			}
		}
		
		
	}

}
