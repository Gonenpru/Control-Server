package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

	private static final String CFG = "/hibernate.cfg.xml";
	private static SessionFactory sessionFactory;

//	static {
//		sessionFactory = new Configuration().configure(CFG).buildSessionFactory();
//	}

	public static void start() {
		try {
			sessionFactory = new Configuration().configure(CFG).buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void stop() {
		try {
			sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtils.sessionFactory = sessionFactory;
	}
}