package main;

import threads.DBThread;
import threads.PlanesDynamicList;

public class ControlServer {

	public static void main(String[] args) {

		Thread threadDB = new Thread(new DBThread());
		Thread threadPDL = new Thread(new PlanesDynamicList());

		threadDB.start();
		threadPDL.start();

		try {
			threadPDL.join();
			threadDB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
