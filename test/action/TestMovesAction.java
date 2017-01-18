package action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;

import dao.MovesDAO;
import db_items.PlaneMovements;
import utils.HibernateUtils;

public class TestMovesAction {

	
	MovesAction movesAction;
	MovesDAO movesDao;

	@Before
	public void initTest() {
		HibernateUtils.start();
		movesDao = new MovesDAO();
		movesAction = new MovesAction();
	}


	@Test
	public void testExecuteReturnSucces() {
		String ret = movesAction.execute();
		assertNotEquals(ActionSupport.ERROR, ret);
	}
	
	@Test
	public void testGetMoves(){
		List<PlaneMovements> ret = movesAction.getMoves();
		List<PlaneMovements> valid = movesDao.list();
		assertEquals(valid, ret);
	}
	
	@Test
	public void testSetMoves(){
		List<PlaneMovements> moves = new ArrayList<>();
		movesAction.setMoves(moves);
		assertEquals(moves, movesAction.getMoves());
	}

}
