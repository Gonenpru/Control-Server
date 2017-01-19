import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import action.TestMovesAction;
import db_items.TestPlaneMovements;
import db_items.TestPlanes;
import engine.TestArrivalManager;
import engine.TestDepartureManager;

@RunWith(Suite.class)

@SuiteClasses({ TestMovesAction.class, TestPlaneMovements.class, TestPlanes.class, TestArrivalManager.class,
		TestDepartureManager.class })

public class SuiteClass {

	@BeforeClass
	public static void setUp() {
		System.out.println("Starting hibernate connection...");
	}

	@AfterClass
	public static void finish() {
		System.out.println("Finishing with tests...");
	}
}
