package utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestHibernateUtils {
	HibernateUtils hibernateUtils;

	@SuppressWarnings("static-access")
	@Test
	public void testStartFunction() {
		hibernateUtils = new HibernateUtils();
		hibernateUtils.start();

		assertNotNull("The sessionFactory was not initilized", hibernateUtils.getSessionFactory());
	}

	@SuppressWarnings("static-access")
	@Test
	public void testStopFunction() {
		hibernateUtils = new HibernateUtils();
		hibernateUtils.start();
		hibernateUtils.stop();

		assertTrue("The sessionFactory is closed", hibernateUtils.getSessionFactory().isClosed());
	}

}
