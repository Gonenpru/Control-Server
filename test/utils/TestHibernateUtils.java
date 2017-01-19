/*
 * 
 */
package utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class TestHibernateUtils.
 */
public class TestHibernateUtils {
	
	/** The hibernate utils. */
	HibernateUtils hibernateUtils;

	/**
	 * Test start function.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testStartFunction() {
		hibernateUtils = new HibernateUtils();
		hibernateUtils.start();

		assertNotNull("The sessionFactory was not initilized", hibernateUtils.getSessionFactory());
	}

	/**
	 * Test stop function.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testStopFunction() {
		hibernateUtils = new HibernateUtils();
		hibernateUtils.start();
		hibernateUtils.stop();

		assertTrue("The sessionFactory is closed", hibernateUtils.getSessionFactory().isClosed());
	}

}
