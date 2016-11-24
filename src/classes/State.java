package classes;

public class State {

	private int id;
	private String state;
	
	public State(){
		
	}
	
	public State(int id, String name){
		this.id = id;
		this.state = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String name) {
		this.state = name;
	}
	
	
}
