/*
 * 
 */
package action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.After;

import dao.MovesDAO;
import db_items.PlaneMovements;
import threads.SynchronizationFactory;
import utils.HibernateUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TestMovesAction.
 */
public class TestMovesAction {

	
	/** The moves action. */
	MovesAction movesAction;
	
	/** The moves dao. */
	MovesDAO movesDao;

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		HibernateUtils.start();
		SynchronizationFactory.define();
		movesDao = new MovesDAO();
		movesAction = new MovesAction();
	}
	
	/**
	 * End test.
	 */
	@After
	public void endTest() {
		HibernateUtils.stop();
	}


	/**
	 * Test execute return succes.
	 */
	@Test
	public void testExecuteReturnSucces() {
		String ret = movesAction.execute();
		assertNotEquals(ActionSupport.ERROR, ret);
	}
	
	/**
	 * Test get moves.
	 */
	@Test
	public void testGetMoves(){
		movesAction.execute();
		List<PlaneMovements> action = movesAction.getMoves();
		List<PlaneMovements> dao = movesDao.list();
		assertEquals(action.size(), dao.size());
	}
		
	/**
	 * Test get last id.
	 */
	@Test
	public void testGetLastId(){
		movesAction.execute();
		List<PlaneMovements> moves = movesDao.list();
		int a = moves.get(moves.size()-1).getId() + 1;
		int b = movesAction.getLastId();
		assertEquals(a, b);	
	}

}
