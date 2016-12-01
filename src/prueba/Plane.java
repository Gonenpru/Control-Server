package prueba;

public class Plane {

	int id;
	int terminal; 
	
	public Plane(int id, int terminal){
		this.id = id;
		this.terminal = terminal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTerminal() {
		return terminal;
	}

	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}
}
