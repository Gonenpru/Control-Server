package main;

import threads.PlanesDynamicList;

public class ControlServer {

	public static void main(String[] args) {
		
		Thread threadPDL = new Thread(new PlanesDynamicList());
		//Call Planes Thread

	}

}
