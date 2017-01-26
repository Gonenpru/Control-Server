/*
 * 
 */
package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateUtils to use hibernate properly.
 */
public class HibernateUtils {

	/** The Configuration path. */
	private static final String CFG = "/hibernate.cfg.xml";

	/** The session factory. */
	private static SessionFactory sessionFactory;

	// static {
	// sessionFactory = new
	// Configuration().configure(CFG).buildSessionFactory();
	// }

	/**
	 * Starts a hibernate session.
	 */
	public static void start() {
		try {
			sessionFactory = new Configuration().configure(CFG).buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Stops the current session.
	 */
	public static void stop() {
		try {
			sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the session factory.
	 *
	 * @return the session factory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Sets the session factory.
	 *
	 * @param sessionFactory
	 *            the new session factory
	 */
	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtils.sessionFactory = sessionFactory;
	}
}