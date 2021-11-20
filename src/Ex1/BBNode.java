package Ex1;

public class BBNode {
	
	private BNode b;
	boolean direction;
	boolean isGiven;
	
	public BBNode(BNode n) {
		b=n;
		direction=true;
		isGiven=false;
	}
	public void setDirection(boolean d) {
		direction=d;
	}
	public void setIsGiven(boolean i) {
		isGiven=i;
	}
	public String toString() {
		if(direction)
			if(isGiven)
				return ""+b+", given, from father";
			else
				return ""+b+", not given, from father";
		else
			if(isGiven)
				return ""+b+", given, from child";
			else
				return ""+b+", not given, from child";
	}
}
