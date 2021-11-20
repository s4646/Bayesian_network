package Ex1;

public class BNode {
	
	private String name;
	private String probs;
	private double[][] probas;
	private BNode[] kids;
	private BNode[] fathers;
	
	public BNode(String n, String p, int len) {
		name = n;
		probs = p;
		kids = new BNode[len];
		fathers = new BNode[len];
	}
	
	/**
	 * Given array of BNodes, set them as fathers of this BNode.
	 * @param a Array of BNodes
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
	 * @param k BNode
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
	/**
	 * Given String of probabilities, set this Bnode's probabilities.
	 * @param s String
	 */
	public void setProbs(String s) {
		probs = s;
	}
	
	/**
	 * Get the number of this BNode's fathers.
	 * @return len int
	 */
	private int getFathersLen() {
		int len = 0;
		for (int i = 0; i < fathers.length; i++) {
			if(fathers[i]!=null)
				len++;
		}
		return len;
	}
	/**
	 * Given a String of probabilities and the number of the probabilities, set this BNode's 2D array of probabilities.
	 * @param probs String
	 * @param len int
	 */
	public void setProbasTable(String probs, int len) {
		int numOfVars = getFathersLen()==0 ? 1 : getFathersLen();
		String[] test = probs.split(" ");
		probas = new double[len/(len/numOfVars)][];
		
		for (int i = 0; i < probas.length; i++) { // Initiate 2D array of probabilities.
			probas[i] = new double[len/numOfVars];
		}
		
		for (int i = 0; i < probas.length; i++) {
			for (int j = 0; j < probas[i].length; j++) {
				probas[i][j] = Double.valueOf(test[numOfVars*i+j]);
			}
		}	
	}
	
	public String getName() {return name;}
	public String getProbs() {return probs;}
	public double[][] getProbsTable() {return probas;}
	public BNode[] getFathers() {return fathers;}
	public BNode[] getKids() {return kids;}
	
	public String toString() {
		return name;
	}
}
