package dao;

import java.util.List;

import org.hibernate.HibernateException;

import db_items.PlaneMovements;
import threads.Launcher;
import utils.HibernateUtils;

public class MovesDAO extends HibernateUtils {

	@SuppressWarnings("unchecked")
	public List<PlaneMovements> list() {
		Launcher.session.beginTransaction();
	
		List<PlaneMovements> moves = null;
		try {
			moves = (List<PlaneMovements>) Launcher.session.createQuery("from PlaneMovements order by id asc").list();
		} catch (HibernateException e) {
			e.printStackTrace();
			Launcher.session.getTransaction().rollback();
		}
		return moves;
	}
}
