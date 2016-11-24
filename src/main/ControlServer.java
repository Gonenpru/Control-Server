package main;

import threads.PlanesDynamicList;

public class ControlServer {

	public static void main(String[] args) {
		
		// Call DB Thread
		Thread threadPDL = new Thread(new PlanesDynamicList());

	}

}
