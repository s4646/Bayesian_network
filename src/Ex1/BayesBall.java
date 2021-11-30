package Ex1;
import java.util.Stack;
// Bayes Ball Algorithm
public class BayesBall {
	private Stack<BNode> s1;
	private Stack<BBNode> s2;
	private Network network;
	
	public BayesBall(Network n) {
		s1 = new Stack<BNode>();
		s2 = new Stack<BBNode>();
		network = n;
	}
	/**
	 * Given array of queries, read and execute them.
	 * @param queries Array of Strings
	 */
	public void readQueries(String[] queries) {
		for (int i = 0; i < queries.length; i++) {
			String[] split = queries[i].split("\\|");
			String vars = split[0];
			String givensStr = "";
			if(split.length>1) givensStr = split[1];
			
			BNode from = network.getNode(vars.split("-")[0]);
			BNode to = network.getNode(vars.split("-")[1]);
			
			if(givensStr!="") {
				String[] test = givensStr.split(",");
				BNode[] givens = new BNode[test.length];
				for (int j = 0; j < test.length; j++) {
					givens[j]= network.getNode(test[j].split("=")[0]);
					givens[j].setIsGiven(true);
				}
				executeQuery(from, to, givens);
				network.resetVisited();
			}
			else {
				executeQuery(from, to);
			}
		}
	}
	/**
	 * Execute query with given BNodes.
	 * @param from BNode
	 * @param to BNode
	 * @param given Array of BNodes
	 * @return boolean
	 */
	public boolean executeQuery(BNode from, BNode to, BNode[] given) {
		if(s2.isEmpty()) {
			s2.push(new BBNode(from));
		}
		boolean fromFather = true;
		while(!s2.isEmpty()) {
			BBNode temp = s2.pop();
			//System.out.println(temp.getName());
			if(temp.getBNode().equals(to)) {
				System.out.println(true);
				return true;				
			}
			
			BNode[] tmpKids = temp.getBNode().getKids();
			BNode[] tmpDads = temp.getBNode().getFathers();
			
			BBNode[] kids = new BBNode[tmpKids.length];
			BBNode[] fathers = new BBNode[tmpDads.length];
			
			for (int i = 0; i < fathers.length; i++) {
				if(tmpKids[i]!=null)
					kids[i]=new BBNode(tmpKids[i]);
				if(tmpDads[i]!=null)
					fathers[i]=new BBNode(tmpDads[i],false);
			}
			
			if(!temp.getBNode().getIsGiven()) {
				if(temp.getDirection() == fromFather) { // BBNode is not given and we came from a father
					if(temp.getBNode().getVisitedFromDad())
						continue;
					
					for (int i = 0; i < kids.length; i++) {
						if(kids[i]!=null)
							s2.push(kids[i]);
					}
					temp.getBNode().setVisitedFromDad(true);
				}
				else { // BBNode is not given and we came from a child
					if(temp.getBNode().getVisitedFromKid())
						continue;
					
					for (int i = 0; i < kids.length; i++) {
						if(kids[i]!=null)
							s2.push(kids[i]);
						if(fathers[i]!=null)
							s2.push(fathers[i]);
					}
					temp.getBNode().setVisitedFromKid(true);
				}
			}
			else {
				if(temp.getDirection() == fromFather) { // BBNode is given and we came from a father
					if(temp.getBNode().getVisitedFromDad())
						continue;
					for (int i = 0; i < fathers.length; i++) {
						if(fathers[i]!=null)
							s2.push(fathers[i]);
					}
					temp.getBNode().setVisitedFromDad(true);
				}
				else { // BBNode is given and we came from a child
					temp.getBNode().setVisitedFromKid(true);
					continue;
				}
			}
		}
		System.out.println(false);
		return false;
	}
	/**
	 * Execute query without given BNodes.
	 * @param from BNode
	 * @param to BNode
	 * @return boolean
	 */
	public boolean executeQuery(BNode from, BNode to) {
		if(s1.isEmpty()) {
			s1.push(from);
		}
		while(!s1.isEmpty()) {
			BNode temp = s1.pop();
			if(temp==to) {
				System.out.println(true);
				return true;				
			}
			BNode[] kids = temp.getKids();
			for (int i = 0; i < kids.length; i++) {
				if(kids[i]!=null)
					s1.push(kids[i]);
			}
		}
		System.out.println(false);
		return false;
	}
	
}
