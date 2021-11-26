package Ex1;
// Bayesian Node
import java.util.ArrayList;
import java.util.HashMap;
public class BNode {
	
	private String name;
	private String[] values;
	private String probs;
	//private double[][] CPT;
	private ArrayList<HashMap<String, String>> CPT;
	private BNode[] kids;
	private BNode[] fathers;
	private boolean isGiven;
	private boolean visitedFromDad;
	private boolean visitedFromKid;
	
	public BNode(String n, String p, int len) {
		name = n;
		probs = p;
		CPT = new ArrayList<HashMap<String, String>>();
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
	
	public void setCPT(String probabilities) {
		String[] p = probabilities.split(" ");
		for (int i = 0; i < p.length; i++) {
			CPT.add(new HashMap<String,String>());
			CPT.get(i).put("probability", p[i]);
		}
		String[][] boolTable = Utils.CPTBooleanTable(this);
		int temp = getNumFathers();
		for (int i = 0; i < CPT.size(); i++) {
			for (int j = 0; j < temp; j++) {
				CPT.get(i).put(fathers[j].name, boolTable[i][j]);
			}
		}
		for (int i = 0; i < CPT.size(); i++) {
			CPT.get(i).put(name, boolTable[i][boolTable[0].length-1]);
		}
	}
	
	public void setValues(String[] arr) {
		values = new String[arr.length-2];
		for (int i = 0; i < values.length; i++) {
			values[i]=arr[i+2].split("\t")[1];
			//test[1].split("\t")[1]
		}
	}
	public void setIsGiven(boolean i) {isGiven=i;}
	public void setVisitedFromDad(boolean b) {visitedFromDad=b;}
	public void setVisitedFromKid(boolean b) {visitedFromKid=b;}
	
	public String getName() {return name;}
	public String getProbs() {return probs;}
	public BNode[] getFathers() {return fathers;}
	public BNode[] getKids() {return kids;}
	public boolean getIsGiven() {return isGiven;}
	public boolean getVisitedFromDad() {return visitedFromDad;}
	public boolean getVisitedFromKid() {return visitedFromKid;}
	public String[] getValues() {return values;}
	public ArrayList<HashMap<String, String>> getCPT() {return CPT;}
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
