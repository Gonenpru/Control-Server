package main;

import java.sql.Statement;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;

import tables.Plane;
import threads.*;
import utils.HibernateUtils;

public class ControlServer {

	private static Session session;
	private static Scanner scanner = new Scanner(System.in);
	private static PlanesDynamicList planesDynamicList;
	private static Transaction transaction;
	static int cont;

	public static void main(String[] args) {
		session = HibernateUtils.getSessionFactory().openSession();
		planesDynamicList = new PlanesDynamicList();

		// EMERGENCY THREAD ENTER p FOR SHUTDOWN
		airportEmergencyControl();

		// READ ALL THE PLANES FIRST
		readPlanes();

		// LISTEN TO NOTIFICATIONS THREAD
		updateAirportPlanes();
	}
	
	public static void finishProgram(){
		Thread finishThread = new Thread() {
			public void run() {
				while (true) {
					String n = scanner.nextLine();
					if (n.equals("exit")) {
						session.close();
						System.exit(0);
					} 
				}
			}
		};
		finishThread.start();	
	}

	public static void airportEmergencyControl() {
		Random rand = new Random();
		Thread emergencyThread = new Thread() {
			public void run() {
				while (true) {
					String n = scanner.nextLine();
					if (n.equals("p")) {
						System.out.println("Shutdown");
						planesDynamicList.executor.shutdown();
						finishProgram();
						break;
					} else if (n.equals("+")) {
						transaction = session.beginTransaction();
						cont = cont + 1;
						createPlane(cont, rand.nextInt(4) + 1, (rand.nextBoolean()) ? "ARRIVE" : "DEPARTURE");
						transaction.commit();
					} 
				}
			}
		};
		emergencyThread.start();		
	}

	public static void updateAirportPlanes() {
		Thread listeningThread = new Thread() {
			public void run() {
				System.out.println("I'm Listening...");
				listenToNotifyMessage();
			}
		};
		listeningThread.start();
	}

	public static void createPlane(int id, int terminal, String modeStatus) {
		Plane plane = new Plane(id, terminal, modeStatus);
		session.save(plane);
	}

	@SuppressWarnings("unchecked")
	public static void readPlanes() {
		Query queryResult = session.createQuery("from Plane");
		List<Plane> allPlanes = queryResult.list();
		for (int i = 0; i < allPlanes.size(); i++) {
			Plane plane = (Plane) allPlanes.get(i);
			planesDynamicList.insertNewPlanes(plane);
			cont = (int) plane.getPlaneId();
		}
	}

	public static void listenToNotifyMessage() {
		PGDataSource dataSource = new PGDataSource();
		dataSource.setHost("localhost");
		dataSource.setPort(5432);
		dataSource.setDatabase("Gonenpru");
		dataSource.setUser("postgres");
		dataSource.setPassword("1234");

		PGNotificationListener listener = (int processId, String channelName,
				String payload) -> proccessTheData(payload);

		try (PGConnection connection = (PGConnection) dataSource.getConnection()) {
			Statement statement = connection.createStatement();
			statement.execute("LISTEN plane_notify");
			statement.close();
			connection.addNotificationListener(listener);
			while (true) {}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void proccessTheData(String payload) {
		System.out.println("Notification from PostgreSQL = " + payload);
		String[] message_parts = payload.split(" ");
		Plane plane = new Plane(Integer.parseInt(message_parts[0]), Integer.parseInt(message_parts[1]), message_parts[2]);
		planesDynamicList.insertNewPlanes(plane);
	}

}
