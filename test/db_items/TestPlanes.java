package db_items;

import static org.junit.Assert.assertEquals;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import engine.AirportEngine;
import threads.SynchronizationFactory;
import utils.HibernateUtils;

public class TestPlanes {

	Planes plane;

	@Before
	public void initTest() {
		SynchronizationFactory.define();
		plane = new Planes();
		plane = new Planes(1, 0, 0, 0);
	}

	@Test
	public void testGetSetId() {
		int id = 2;
		plane.setId(id);
		assertEquals(id, plane.getId());
	}

	@Test
	public void testGetSetAirlineId() {
		int id = 2;
		plane.setAirline_id(id);
		assertEquals(id, plane.getAirline_id());
	}

	@Test
	public void testGetSetPlaneModelId() {
		int id = 2;
		plane.setPlaneModel_id(id);
		assertEquals(id, plane.getPlaneModel_id());
	}

	@Test
	public void testGetSetPlaneStatuId() {
		int id = 2;
		plane.setPlaneStatu_id(id);
		assertEquals(id, plane.getPlaneStatu_id());
	}

	@Test
	public void testGetSetAe() {
		AirportEngine ae = new AirportEngine();
		plane.setAe(ae);
		assertEquals(ae.getClass(), plane.getAe().getClass());
	}

	@Test
	public void testGetTerminal() {
		int terminal;
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery("select getTerminal(:plane)").setInteger("plane", plane.getId());
			terminal = Integer.parseInt(query.list().get(0).toString());
		} finally {
			session.close();
		}
		assertEquals(terminal, plane.getTerminal());
	}
}
