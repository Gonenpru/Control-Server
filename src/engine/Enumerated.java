/*
 * 
 */
package engine;

// TODO: Auto-generated Javadoc
/**
 * The Class Enumerated.
 */
public class Enumerated {
	
	/**
	 * The Enum Arrival.
	 */
	public enum Arrival {
		
		/** The airport. */
		AIRPORT(51.477529, -0.490220), 
 /** The terminal1. */
 TERMINAL1(51.475851, -0.446425), 
 /** The terminal2. */
 TERMINAL2(51.475851, -0.457461), 
 /** The terminal3. */
 TERMINAL3(
				51.475851, -0.469114), 
 /** The terminal4. */
 TERMINAL4(51.475851,
						-0.480882), 
 /** The landing curve. */
 LANDING_CURVE(51.475851, -0.436008), 
 /** The landing lane. */
 LANDING_LANE(51.477529, -0.433933);

		/** The y. */
		private final double x, y;

		/**
		 * Instantiates a new arrival.
		 *
		 * @param x the x
		 * @param y the y
		 */
		Arrival(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Gets the x.
		 *
		 * @return the x
		 */
		double getX() {
			return this.x;
		}

		/**
		 * Gets the y.
		 *
		 * @return the y
		 */
		double getY() {
			return this.y;
		}
	}

	/**
	 * The Enum Terminal.
	 */
	public enum Terminal {
		
		/** The t1. */
		T1(51.472011, -0.442705), 
 /** The p T 1. */
 pT1(51.467497, -0.445614), 
 /** The t2. */
 T2(51.471947, -0.453759), 
 /** The p T 2. */
 pT2(51.467497, -0.457162), 
 /** The t3. */
 T3(
				51.471597, -0.465530), 
 /** The p T 3. */
 pT3(51.467497, -0.468695), 
 /** The t4. */
 T4(51.471501, -0.477352), 
 /** The p T 4. */
 pT4(51.467497, -0.481082);

		/** The y. */
		private final double x, y;

		/**
		 * Instantiates a new terminal.
		 *
		 * @param x the x
		 * @param y the y
		 */
		Terminal(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Gets the x.
		 *
		 * @return the x
		 */
		double getX() {
			return this.x;
		}

		/**
		 * Gets the y.
		 *
		 * @return the y
		 */
		double getY() {
			return this.y;
		}

		/**
		 * Gets the terminal.
		 *
		 * @param a the a
		 * @return the terminal
		 */
		public static Terminal getTerminal(int a) {
			Terminal r;
			switch (a) {
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
		 * Gets the p terminal.
		 *
		 * @param a the a
		 * @return the p terminal
		 */
		public static Terminal getpTerminal(int a) {
			Terminal r;
			switch (a) {
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
	 * The Enum Departure.
	 */
	public enum Departure {
		
		/** The airport. */
		AIRPORT(51.467290, -0.434884), 
 /** The terminal1. */
 TERMINAL1(51.467497, -0.457162), 
 /** The terminal2. */
 TERMINAL2(51.467497, -0.468695), 
 /** The terminal3. */
 TERMINAL3(
				51.467497, -0.481082), 
 /** The terminal4. */
 TERMINAL4(51.467387,
						-0.487393), 
 /** The take off curve. */
 TAKE_OFF_CURVE(51.464816, -0.487393), 
 /** The take off lane. */
 TAKE_OFF_LANE(51.465088, -0.396408);

		/** The y. */
		private final double x, y;

		/**
		 * Instantiates a new departure.
		 *
		 * @param x the x
		 * @param y the y
		 */
		Departure(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Gets the x.
		 *
		 * @return the x
		 */
		double getX() {
			return this.x;
		}

		/**
		 * Gets the y.
		 *
		 * @return the y
		 */
		double getY() {
			return this.y;
		}
	}

	/**
	 * The Enum Sectors.
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
	 * Int to enum arrival.
	 *
	 * @param a the a
	 * @return the arrival
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
	 * Int to enum departure.
	 *
	 * @param d the d
	 * @return the departure
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
