/*
 * 
 */
package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class HibernateUtils.
 */
public class HibernateUtils {

	/** The Constant CFG. */
	private static final String CFG = "/hibernate.cfg.xml";
	
	/** The session factory. */
	private static SessionFactory sessionFactory;

//	static {
//		sessionFactory = new Configuration().configure(CFG).buildSessionFactory();
//	}

	/**
 * Start.
 */
public static void start() {
		try {
			sessionFactory = new Configuration().configure(CFG).buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Stop.
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
	 * @param sessionFactory the new session factory
	 */
	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtils.sessionFactory = sessionFactory;
	}
}