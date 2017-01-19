/*
 * 
 */
package action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import dao.MovesDAO;
import db_items.PlaneMovements;

// TODO: Auto-generated Javadoc
/**
 * The Class MovesAction.
 */
public class MovesAction extends ActionSupport {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The moves dao. */
	private MovesDAO movesDao;

	/**
	 * Instantiates a new moves action.
	 */
	public MovesAction() {
		movesDao = new MovesDAO();
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		if (movesDao.list() != null) {
			movesDao.list();
			return SUCCESS;
		}
		return ERROR;
	}

	/**
	 * Gets the moves.
	 *
	 * @return the moves
	 */
	public List<PlaneMovements> getMoves() {
		return movesDao.list();
	}

	/**
	 * Gets the last id.
	 *
	 * @return the last id
	 */
	public int getLastId() {
		int i = 0;
		List<PlaneMovements> moves = this.getMoves();
		i = moves.get(moves.size()-1).getId() + 1;
		return i;
	}
}
