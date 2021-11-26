package Ex1;

public class Variable {
	private Network network;
	private String name;
	String[] values;
	
	public Variable(BNode n) {
		name = n.getName();
		values=n.getValues();
	}
	public Variable(Network N, String n) {
		name = n;
		network=N;
		values=N.getNode(name).getValues();
	}
	
	public String getName() {return name;}
	public String[] getValues() {return values;}
}
