package Ex1;

public class BBNode {
	
	private String name;
	private BNode b;
	private boolean direction;
	
	public BBNode(BNode n) {
		name=n.getName();
		b=n;
		direction=true; // from father
	}
	public BBNode(BNode n, boolean dir) {
		b=n;
		direction=dir;
	}
	public void setDirection(boolean d) {
		direction=d;
	}
	public BNode getBNode() {return b;}
	public boolean getDirection() {return direction;}
	public String getName() {return name;}
	
	public String toString() {
		return ""+b+" , "+direction;
	}
}
