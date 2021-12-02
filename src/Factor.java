import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Factor {
	private Network network;
	private ArrayList<HashMap<String, String>> table;
	private Variable[] variables;
	
	public Factor(BNode n) {
		//table=n.getCPT();
		table = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < n.getCPT().size(); i++) {
			table.add((HashMap<String, String>) n.getCPT().get(i).clone());
		}
		variables = new Variable[n.getNumFathers()+1];
		setVariables(n);
	}
	public Factor(Network n, Variable[] v) {
		network=n;
		variables=v;
		setVariables(v);
		table = new ArrayList<HashMap<String, String>>();
		setTable(v);
	}
	public Factor(Variable[] v) {
		variables=v;
		table = new ArrayList<HashMap<String, String>>();
	}
	
	public void setTable(Variable[] v) {
		String[][] boolTable = Utils.CPTBooleanTable(v);
		for (int i = 0; i < boolTable.length; i++) {
			table.add(new HashMap<String,String>());
		}
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j < table.size(); j++) {
				table.get(j).put(v[i].getName(), boolTable[j][i]);
			}
		}
	}
	public void setTable(ArrayList<HashMap<String, String>> t) {table=t;}
	
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
	
	public void removeIrrelvant() {
		boolean isSame;
		for (int i = 0; i < variables.length; i++) {
			isSame=true;
			for (int j = 1; j < table.size(); j++) {
				if(!table.get(j).get(variables[i].getName()).equals(table.get(j-1).get(variables[i].getName()))) {
					isSame=false;
					break;
				}
			}
			if(isSame) {
				for (int j = 0; j < table.size(); j++) {
					table.get(j).remove(variables[i].getName());
				}
				variables[i]=null;
				variables = Utils.removeNull(variables);
			}
		}
	}
	
	public Variable[] getVariables() {return variables;}
	public ArrayList<HashMap<String, String>> getTable(){return table;}
	
	public String toString() {
		String s = "";
		return s;
	}
}
