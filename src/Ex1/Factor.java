package Ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Factor {
	private Network network;
	//private BNode node;
	private ArrayList<HashMap<String, String>> table;
	//private double[] probabilities;
	//private String[][] booleans;
	private Variable[] variables;
	
	public Factor(BNode n) {
		//node=n;
		table=n.getCPT();
		//setFactorBooleans(); // set Booleans
		//setFactorProbas(); // set probabilities
		variables = new Variable[n.getNumFathers()+1];
		setVariables(n);
	}
	public Factor(Network n, Variable[] v) {
		network=n;
		//node=null;
		variables=v;
		//setFactorBooleans();
	}
	
	public void setVariables(BNode node) {
		BNode[] temp = Utils.removeNull(node.getFathers());
		for (int i = 0; i < temp.length; i++) {
			variables[i] = new Variable(temp[i]);
		}
		variables[variables.length-1] = new Variable(node);
	}
	public void setVariables(Variable[] arr) {variables=arr;}
	
	public void removeEvidence(String[] arr) {
		String str = table.get(0).keySet().toString();
		str = str.substring(1, str.length()-1);
		ArrayList<String> evidence = new ArrayList<String>(Arrays.asList(arr));
		ArrayList<String> check = new ArrayList<String>(Arrays.asList(str.split(", ")));
		for (int i = 0; i < evidence.size(); i+=2) {
			String var = evidence.get(i);
			if(check.contains(var)) {
				clean(var,evidence.get(evidence.indexOf(var)+1));
			}
		}
	}
	private void clean(String varName, String varVal) {
		ArrayList<Integer> noRemove = new ArrayList<Integer>();
		ArrayList<Integer> yesRemove = new ArrayList<Integer>();
		
		for (int i = 0; i < table.size(); i++) {
			if(table.get(i).get(varName).equals(varVal)) {
				noRemove.add(i);
			}
		}
		for (int i = 0; i < table.size(); i++) {
			if(!noRemove.contains(i)) {
				yesRemove.add(i);
			}
		}
		int remCount=0;
		for (int i = 0; i < yesRemove.size(); i++) {
			int temp = yesRemove.get(i);
			table.remove(temp-(remCount++));
		}
	}
	
	public Variable[] getVariables() {return variables;}
	public ArrayList<HashMap<String, String>> getTable(){return table;}
	
	public String toString() {
		String s = "";
		return s;
	}
}
