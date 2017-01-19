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

public class TestMovesAction {

	
	MovesAction movesAction;
	MovesDAO movesDao;

	@Before
	public void initTest() {
		HibernateUtils.start();
		SynchronizationFactory.define();
		movesDao = new MovesDAO();
		movesAction = new MovesAction();
	}
	
	@After
	public void endTest() {
		HibernateUtils.stop();
	}


	@Test
	public void testExecuteReturnSucces() {
		String ret = movesAction.execute();
		assertNotEquals(ActionSupport.ERROR, ret);
	}
	
	@Test
	public void testGetMoves(){
		movesAction.execute();
		List<PlaneMovements> action = movesAction.getMoves();
		List<PlaneMovements> dao = movesDao.list();
		assertEquals(action.size(), dao.size());
	}
		
	@Test
	public void testGetLastId(){
		movesAction.execute();
		List<PlaneMovements> moves = movesDao.list();
		int a = moves.get(moves.size()-1).getId() + 1;
		int b = movesAction.getLastId();
		assertEquals(a, b);	
	}

}
