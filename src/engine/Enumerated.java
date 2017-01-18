package engine;

public class Enumerated {
	public enum Arrival {
		AIRPORT(51.477529, -0.490220), TERMINAL1(51.475851, -0.446425), TERMINAL2(51.475851, -0.457461), TERMINAL3(
				51.475851, -0.469114), TERMINAL4(51.475851,
						-0.480882), LANDING_CURVE(51.475851, -0.436008), LANDING_LANE(51.477529, -0.433933);

		private final double x, y;

		Arrival(double x, double y) {
			this.x = x;
			this.y = y;
		}

		double getX() {
			return this.x;
		}

		double getY() {
			return this.y;
		}
	}

	public enum Terminal {
		T1(51.472011, -0.442705), pT1(51.467497, -0.445614), T2(51.471947, -0.453759), pT2(51.467497, -0.457162), T3(
				51.471597, -0.465530), pT3(51.467497, -0.468695), T4(51.471501, -0.477352), pT4(51.467497, -0.481082);

		private final double x, y;

		Terminal(double x, double y) {
			this.x = x;
			this.y = y;
		}

		double getX() {
			return this.x;
		}

		double getY() {
			return this.y;
		}

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

	public enum Departure {
		AIRPORT(51.467290, -0.434884), TERMINAL1(51.467497, -0.457162), TERMINAL2(51.467497, -0.468695), TERMINAL3(
				51.467497, -0.481082), TERMINAL4(51.467387,
						-0.487393), TAKE_OFF_CURVE(51.464816, -0.487393), TAKE_OFF_LANE(51.465088, -0.396408);

		private final double x, y;

		Departure(double x, double y) {
			this.x = x;
			this.y = y;
		}

		double getX() {
			return this.x;
		}

		double getY() {
			return this.y;
		}
	}

	public enum Sectors {
		DEPARTURE, ARRIVAL, AIRPORT
	}

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
