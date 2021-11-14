package Ex1;

public class BNode {
	
	private String name;
	private String probs;
	private BNode[] kids;
	private BNode[] fathers;
	
	public BNode(String n, String p, int len) {
		name = n;
		probs = p;
		kids = new BNode[len];
		fathers = new BNode[len];
	}
	
	/**
	 * Given array of Nodes, set them as fathers of this Node.
	 * @param a Array of Nodes
	 */
	public void setFathers(BNode[] a) {
		for (int i = 0; i < a.length; i++) {
			if(a[i]!=null)
				//fathers[i] = new BNode(a[i].name, a[i].probs, fathers.length);
				fathers[i]=a[i];
		}
	}
	
	/**
	 * Given BNode, set it as kid of this BNode.
	 * @param k Array of Nodes
	 */
	public void setKid(BNode k) {
		for (int i = 0; i < kids.length; i++) {
			if(kids[i]!=null)
				continue;
			else {
				kids[i]=k;
				break;
			}
		}
	}
	
	public String getName() {return name;}
	public String getProbs() {return probs;}
	public BNode[] getFathers() {return fathers;}
	public BNode[] getKids() {return kids;}
	
	public String toString() {
		return name;
	}
}
