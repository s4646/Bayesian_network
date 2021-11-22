package Ex1;
// Bayesian Node
public class BNode {
	
	private String name;
	private String probs;
	private double[][] CPT;
	private BNode[] kids;
	private BNode[] fathers;
	private boolean isGiven;
	private boolean visitedFromDad;
	private boolean visitedFromKid;
	
	public BNode(String n, String p, int len) {
		name = n;
		probs = p;
		kids = new BNode[len];
		fathers = new BNode[len];
		isGiven=false;
		visitedFromDad=false;
		visitedFromKid=false;
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
	 * Given a String of probabilities and the number of the probabilities, set this BNode's 2D array of probabilities.
	 * @param probs String
	 * @param len int
	 */
	public void setCPT(String probs, int len) {
		int tmp = getNumFathers();
		int numOfVars = tmp==0 ? 1 : tmp==1 ? 2 : tmp;
		String[] test = probs.split(" ");
		CPT = new double[len/numOfVars][];
		
		for (int i = 0; i < CPT.length; i++) { // Initiate 2D array of probabilities.
			CPT[i] = new double[len/(len/numOfVars)];
		}
		
		for (int i = 0; i < CPT.length; i++) {
			for (int j = 0; j < CPT[i].length; j++) {
				CPT[i][j] = Double.valueOf(test[numOfVars*i+j]);
			}
		}	
	}
	public void setIsGiven(boolean i) {isGiven=i;}
	public void setVisitedFromDad(boolean b) {visitedFromDad=b;}
	public void setVisitedFromKid(boolean b) {visitedFromKid=b;}
	
	public String getName() {return name;}
	public String getProbs() {return probs;}
	public double[][] getProbsTable() {return CPT;}
	public BNode[] getFathers() {return fathers;}
	public BNode[] getKids() {return kids;}
	public boolean getIsGiven() {return isGiven;}
	public boolean getVisitedFromDad() {return visitedFromDad;}
	public boolean getVisitedFromKid() {return visitedFromKid;}
	public double[][] getCPT() {return CPT;}
	public int getNumFathers() {
		int count=0;
		for (int i = 0; i < fathers.length; i++)
			if(fathers[i]!=null)
				count++;
		return count;
	}

	
	public String toString() {
		return name;
	}
}
