/*
 * 
 */
package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import db_items.PlaneMovements;
import utils.HibernateUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class MovesDAO.
 */
public class MovesDAO extends HibernateUtils {

	/**
	 * List.
	 *
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<PlaneMovements> list() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<PlaneMovements> moves = null;
		try {
			session.beginTransaction();
			moves = (List<PlaneMovements>) session.createQuery("from PlaneMovements order by id asc").list();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		return moves;
	}
}
