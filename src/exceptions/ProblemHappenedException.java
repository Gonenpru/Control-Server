/*
 * 
 */
package exceptions;

import java.io.PrintWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class ProblemHappenedException.
 */
public class ProblemHappenedException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7068741380739534526L;

	/** The message. */
	final String message;
	
	/**
	 * Instantiates a new problem happened exception.
	 *
	 * @param msg the message to print
	 */
	public ProblemHappenedException(String msg) {
		this.message = msg;
	}
	
	/**
	 * Prints this throwable and its backtrace to the specified print writer.
	 * 
	 * @param s PrintWriter to use for output
	 */
	@Override
	public void printStackTrace(PrintWriter s) {
		StackTraceElement[] ste = this.getStackTrace();
		this.setStackTrace(ste);
		super.printStackTrace();
	}
}
