package main;

import java.util.Scanner;

import connection.DBConnection;
import threads.DBThread;
import threads.PlanesDynamicList;

public class ControlServer {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		DBConnection dbConnection;
		Thread threadDB;
		Thread threadPDL;
		
		dbConnection = new DBConnection();

		threadDB = new Thread(new DBThread());
		threadPDL = new Thread(new PlanesDynamicList());

		threadDB.start();
		threadPDL.start();

		try {
			threadPDL.join();
			threadDB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(true){
			if(scanner.nextLine()!="s"){
				threadDB.interrupt();
				threadPDL.interrupt();
				dbConnection.closeConnection();
			}
			
			break;
		}

	}

}
