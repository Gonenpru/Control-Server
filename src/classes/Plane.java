package classes;

public class Plane {
	
	private int planeId;
	private int airlineId;
	private State state;
	private String airline;
	private String model;
	private String manufacturer;
	private int maxPass;
	private int passengers;
	private int terminal; 
	private boolean handled = false;
	
	public Plane(int planeId, int airlineId, String airline, String model, String manufacturer, int maxPass, int passengers, int terminal){
		this.planeId = planeId;
		this.airlineId = airlineId;
		this.state = new State();
		this.state.setState("Arrived");
		this.airline = airline;
		this.model = model;
		this.manufacturer = manufacturer;
		this.maxPass = maxPass;
		this.passengers = passengers;
		this.terminal = terminal;
	}

	public int getPlaneId() {
		return planeId;
	}

	public void setPlaneId(int planeId) {
		this.planeId = planeId;
	}

	public int getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getMaxPass() {
		return maxPass;
	}

	public void setMaxPass(int maxPass) {
		this.maxPass = maxPass;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	
	public boolean isHandled(){
		return this.handled;
	}

	public int getTerminal() {
		return terminal;
	}

	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}
	
	
	

}
