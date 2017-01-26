/*
 * 
 */
package engine;

/**
 * Class containing the enumerates that define the layout of the airport.
 */
public class Enumerated {

	/**
	 * The Arrival lane.
	 */
	public enum Arrival {

		/** The airport. */
		AIRPORT(51.477529, -0.490220),
		/** The terminal1. */
		TERMINAL1(51.475851, -0.446425),
		/** The terminal2. */
		TERMINAL2(51.475851, -0.457461),
		/** The terminal3. */
		TERMINAL3(51.475851, -0.469114),
		/** The terminal4. */
		TERMINAL4(51.475851, -0.480882),
		/** The landing curve. */
		LANDING_CURVE(51.475851, -0.436008),
		/** The landing lane. */
		LANDING_LANE(51.477529, -0.433933);

		/** The coordinates */
		private final double x, y;

		/**
		 * Instantiates a new arrival.
		 *
		 * @param x
		 *            the x coordinate
		 * @param y
		 *            the y coordinate
		 */
		Arrival(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Gets the x coordinate.
		 *
		 * @return the x coordinate
		 */
		double getX() {
			return this.x;
		}

		/**
		 * Gets the y coordinate.
		 *
		 * @return the y coordinate
		 */
		double getY() {
			return this.y;
		}
	}

	/**
	 * The Terminals.
	 */
	public enum Terminal {

		/** The t1. */
		T1(51.472011, -0.442705),
		/** The pre T 1. */
		pT1(51.467497, -0.445614),
		/** The t2. */
		T2(51.471947, -0.453759),
		/** The pre T 2. */
		pT2(51.467497, -0.457162),
		/** The t3. */
		T3(51.471597, -0.465530),
		/** The pre T 3. */
		pT3(51.467497, -0.468695),
		/** The t4. */
		T4(51.471501, -0.477352),
		/** The pre T 4. */
		pT4(51.467497, -0.481082);

		/** The coordinates. */
		private final double x, y;

		/**
		 * Instantiates a new terminal.
		 *
		 * @param x the x coordinate
		 * @param y the y coordinate
		 */
		Terminal(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Gets the x coordinate.
		 *
		 * @return the x coordinate
		 */
		double getX() {
			return this.x;
		}

		/**
		 * Gets the y coordinate.
		 *
		 * @return the y coordinate
		 */
		double getY() {
			return this.y;
		}

		/**
		 * Gets the terminal enumerate.
		 *
		 * @param terminal  the terminal number
		 * @return the terminal enum
		 */
		public static Terminal getTerminal(int terminal) {
			Terminal r;
			switch (terminal) {
			case 1:
				r = Terminal.T1;
				break;
			case 2:
				r = Terminal.T2;
				break;
			case 3:
				r = Terminal.T3;
				break;
			default:
				r = Terminal.T4;
				break;
			}
			return r;
		}

		/**
		 * Gets the pre terminal enumerate.
		 *
		 * @param terminal the terminal number
		 * @return the terminal enum
		 */
		public static Terminal getpTerminal(int terminal) {
			Terminal r;
			switch (terminal) {
			case 1:
				r = Terminal.pT1;
				break;
			case 2:
				r = Terminal.pT2;
				break;
			case 3:
				r = Terminal.pT3;
				break;
			default:
				r = Terminal.pT4;
				break;
			}
			return r;
		}
	}

	/**
	 * The Departure lanes.
	 */
	public enum Departure {

		/** The airport. */
		AIRPORT(51.467290, -0.434884),
		/** The terminal1. */
		TERMINAL1(51.467497, -0.457162),
		/** The terminal2. */
		TERMINAL2(51.467497, -0.468695),
		/** The terminal3. */
		TERMINAL3(51.467497, -0.481082),
		/** The terminal4. */
		TERMINAL4(51.467387, -0.487393),
		/** The take off curve. */
		TAKE_OFF_CURVE(51.464816, -0.487393),
		/** The take off lane. */
		TAKE_OFF_LANE(51.465088, -0.396408);

		/** The coordinates. */
		private final double x, y;

		/**
		 * Instantiates a new departure.
		 *
		 * @param x
		 *            the x coordinate
		 * @param y
		 *            the y coordinate
		 */
		Departure(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Gets the x coordinate.
		 *
		 * @return the x coordinate
		 */
		double getX() {
			return this.x;
		}

		/**
		 * Gets the y coordinate.
		 *
		 * @return the y coordinate
		 */
		double getY() {
			return this.y;
		}
	}

	/**
	 * The Sectors.
	 */
	public enum Sectors {

		/** The departure. */
		DEPARTURE,
		/** The arrival. */
		ARRIVAL,
		/** The airport. */
		AIRPORT
	}

	/**
	 * Int to enumerate arrival.
	 *
	 * @param a
	 *            the number
	 * @return the arrival enumerate
	 */
	public static Arrival intToEnum_Arrival(int a) {
		Arrival r;
		switch (a) {
		case 1:
			r = Arrival.TERMINAL1;
			break;
		case 2:
			r = Arrival.TERMINAL2;
			break;
		case 3:
			r = Arrival.TERMINAL3;
			break;
		case 4:
			r = Arrival.TERMINAL4;
			break;
		case 5:
			r = Arrival.LANDING_CURVE;
			break;
		case 6:
			r = Arrival.LANDING_LANE;
			break;
		default:
			r = Arrival.AIRPORT;
		}
		return r;
	}

	/**
	 * Int to enumerate departure.
	 *
	 * @param d
	 *            the nuber
	 * @return the departure enum
	 */
	public static Departure intToEnum_Departure(int d) {
		Departure r;
		switch (d) {
		case 1:
			r = Departure.TERMINAL1;
			break;
		case 2:
			r = Departure.TERMINAL2;
			break;
		case 3:
			r = Departure.TERMINAL3;
			break;
		case 4:
			r = Departure.TERMINAL4;
			break;
		case 5:
			r = Departure.TAKE_OFF_CURVE;
			break;
		case 6:
			r = Departure.TAKE_OFF_LANE;
			break;
		default:
			r = Departure.AIRPORT;
		}
		return r;
	}
}
