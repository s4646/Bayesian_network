package Ex1;
public class Network {
	
	private BNode[] netArr;
	
	public Network(int i) {	
		netArr = new BNode[i];
	}
	
	/**
	 * Given array of BNodes, set the network.
	 * @param net
	 */
	public void setNodes(BNode[] net) {
		for (int i = 0; i < netArr.length; i++) {
			netArr[i]=net[i];
		}
	}
//	/**
//	 * Given a Node and array of Nodes, set the array of Nodes
//	 * to be the fathers of the Node
//	 * @param n Node
//	 * @param a Array of Nodes
//	 */
//	public void setNodeAncestors(BNode n, BNode[] a) {
//		n.setAncestors(a);
//	}
	
	public BNode getNode(String name) {
		int i;
		for (i = 0; i < netArr.length; i++) {
			if(netArr[i].getName().equals(name))
				return netArr[i];
		}
		return null;
	}
	public BNode[] getNetwork() {return netArr;}
	
	public String toString() {
		String net = "";
		String fathers = "";
		String kids = "";
		for (int i = 0; i < netArr.length; i++) {
			net+=netArr[i].getName()+" ";
			fathers+=netArr[i].getName()+" fathers: ";
			kids+=netArr[i].getName()+" kids: ";
			for (int j = 0; j < netArr[i].getFathers().length; j++) {
				if(netArr[i].getFathers()[j]==null)
					continue;
				else
					fathers+=netArr[i].getFathers()[j].getName()+" ";
			}
			for (int j = 0; j < netArr[i].getKids().length; j++) {
				if(netArr[i].getKids()[j]==null)
					continue;
				else
					kids+=netArr[i].getKids()[j].getName()+" ";
			}
			fathers+="\n";
			kids+="\n";
		}
		return net+"\n"+fathers+kids;
	}	
}
