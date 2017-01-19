package action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import dao.MovesDAO;
import db_items.PlaneMovements;

public class MovesAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MovesDAO movesDao;

	public MovesAction() {
		movesDao = new MovesDAO();
	}

	public String execute() {
		if (movesDao.list() != null) {
			movesDao.list();
			return SUCCESS;
		}
		return ERROR;
	}

	public List<PlaneMovements> getMoves() {
		return movesDao.list();
	}

	public int getLastId() {
		int i = 0;
		List<PlaneMovements> moves = this.getMoves();
		i = moves.get(moves.size()-1).getId() + 1;
		return i;
	}
}
