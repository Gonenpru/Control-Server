/*
 * 
 */
package action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import dao.MovesDAO;
import db_items.PlaneMovements;

/**
 * The Class MovesAction.
 */
public class MovesAction extends ActionSupport {

	/** The Constant serialVersionUID to avoid warnings. */
	private static final long serialVersionUID = 1L;

	/** The DAO to moves. */
	private MovesDAO movesDao;

	/**
	 * Instantiates a new moves action.
	 */
	public MovesAction() {
		movesDao = new MovesDAO();
	}

	/**
	 * A default implementation that does nothing an returns "success".
	 * @return "success", if correct
	 */
	public String execute() {
		if (movesDao.list() != null) {
			movesDao.list();
			return SUCCESS;
		}
		return ERROR;
	}

	/**
	 * Gets the list of moves from the MovesDAO.
	 *
	 * @return the moves
	 */
	public List<PlaneMovements> getMoves() {
		return movesDao.list();
	}

	/**
	 * Gets the last id of moves.
	 *
	 * @return the last ID
	 */
	public int getLastId() {
		int id = 0;
		List<PlaneMovements> moves = this.getMoves();
		id = moves.get(moves.size()-1).getId() + 1;
		return id;
	}
}
