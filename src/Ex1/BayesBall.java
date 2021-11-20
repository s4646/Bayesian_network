package Ex1;
import java.util.Stack;

public class BayesBall {
	private Stack<BBNode> s;
	private Network network;
	
	public BayesBall(Network n) {
		s = new Stack<BBNode>();
		network = n;
	}
	/**
	 * Given array of queries, read and execute them.
	 * @param queries Array of Strings
	 */
	public void readQueries(String[] queries) {
		for (int i = 1; i < queries.length; i++) {
			String[] split = queries[i].split("\\|");
			String vars = split[0];
			String givensStr = "";
			if(split.length>1) givensStr = split[1];
			
			BBNode from = new BBNode(network.getNode(vars.split("-")[0]));
			BBNode to = new BBNode(network.getNode(vars.split("-")[1]));
			
			if(givensStr!="") {
				String[] test = givensStr.split(",");
				BBNode[] givens = new BBNode[test.length];
				for (int j = 0; j < test.length; j++) {
					givens[j]= new BBNode(network.getNode(test[j].split("=")[0]));
					givens[j].setIsGiven(true);
				}
				executeQuery(from, to, givens);
			}
			else executeQuery(from, to);
		}
	}
	
	public boolean executeQuery(BBNode from, BBNode to, BBNode[] given) {
		return false;
	}
	public boolean executeQuery(BBNode from, BBNode to) {
		return false;
	}
	
}
