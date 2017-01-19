package exceptions;

import java.io.PrintWriter;

public class ProblemHappenedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7068741380739534526L;

	String message;
	public ProblemHappenedException(String msg) {
		this.message = msg;
	}
	
	@Override
	public void printStackTrace(PrintWriter s) {
		StackTraceElement[] ste = this.getStackTrace();
		this.setStackTrace(ste);
		super.printStackTrace();
	}
}
