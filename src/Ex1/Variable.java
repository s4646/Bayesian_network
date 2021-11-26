package Ex1;

public class Variable {
	private String name;
	String[] values;
	
	public Variable(BNode n) {
		name = n.getName();
		values=n.getValues();
		
	}
	
	public String getName() {return name;}
	public String[] getValues() {return values;}
}
