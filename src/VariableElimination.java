import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class VariableElimination {
	private Network network;
	private Factor[] factors;
	
	public VariableElimination(Network n) {
		network=n;
		factors = new Factor[n.getNetwork().length];
	}
	public void setFactors() {
		factors = new Factor[network.getNetwork().length];
		for (int i = 0; i < factors.length; i++) {
			factors[i] = new Factor(network.getNetwork()[i]);
		}
	}
	public Factor[] getFactors() {return factors;}
	
	public String readQueries(String[] queries) {
		String res ="";
		for (int i = 0; i < queries.length; i++) { // change to len
			queries[i]=queries[i].substring(2);
			String queryVar = queries[i].split("\\|")[0];
			String _evidence = queries[i].split("\\|")[1];
			_evidence = _evidence.split("\\) ")[0];
			String _hidden = queries[i].split("\\) ")[1];
			String[] hidden = _hidden.split("-");
			String[] evidence = new String[2*_evidence.split(",").length];
			if(_evidence.length()<=1) {
				res+=executeQuery(queryVar.split("=")[0])+"\n";
				setFactors();
			}
			for (int j = 0; j < _evidence.split(",").length && _evidence.length()>1; j++) {
				evidence[2*j]=_evidence.split(",")[j].split("=")[0]; // evidence variable
				evidence[2*j+1]=_evidence.split(",")[j].split("=")[1]; // evidence value
			}
			if(_evidence.length()>1) {
				res+=executeQuery(queryVar,evidence,hidden)+"\n";
				setFactors();	
			}
		}
		return res;
	}
	
	public String executeQuery(String queryVar) {
		String res="";
		for (int i = 0; i < factors.length; i++) {
			if(factors[i].getVariables().length==1 && Utils.isVarInFactor(factors[i],queryVar)) {
				double d = Math.round(Double.parseDouble(factors[0].getTable().get(i).get("probability")) * 100000.0)/100000.0;
				res = d+",0,0";
				}
		}
		return res;
	}
	
	public String executeQuery(String queryVar, String[] evidence, String[] hidden) {
		int mulNum=0;
		int addNum=0;
		String res = "";
		for (int i = 0; i < factors.length; i++) { // set initial Factors for query
			factors[i].removeEvidence(evidence);
			factors[i].removeIrrelvant();
		}
		// remove irrelevant factors
		ArrayList<String> irrelevant = getIrrelevantHidden(queryVar, evidence, hidden);
		hidden = removeIrrelevantHidden(irrelevant, hidden);
		removeIrrelevantFactors(irrelevant);
		hidden = Utils.removeNull(hidden);
		//hidden = sortByASCII(hidden);
		factors = Utils.sortBySize(factors);
		int i=0;
		while(i<hidden.length) {
			for (int j=0; j < factors.length;j++) {
				for (int k = 0; k < factors.length; k++) {
					if(j!=k && Utils.isVarInFactor(factors[j], hidden[i])==true && Utils.isVarInFactor(factors[k], hidden[i])==true) {
						if(factors[j].getTable().size()<factors[k].getTable().size()) {
							mulNum+=join(factors[j],factors[k]);
							factors[k]=null;
							factors = Utils.removeNull(factors);
							factors = Utils.sortBySize(factors);
							break;
						}
						else if (factors[j].getTable().size()>factors[k].getTable().size()) {
							mulNum+=join(factors[k],factors[j]);
							factors[j]=null;
							factors = Utils.removeNull(factors);
							factors = Utils.sortBySize(factors);
							break;
						}
						else {
							if(Utils.varAsciiSum(factors[j])<Utils.varAsciiSum(factors[j])) {
								mulNum+=join(factors[j],factors[k]);
								factors[k]=null;
								factors = Utils.removeNull(factors);
								factors = Utils.sortBySize(factors);
								break;
							}
							else {
								mulNum+=join(factors[k],factors[j]);
								factors[j]=null;
								factors = Utils.removeNull(factors);
								factors = Utils.sortBySize(factors);
								break;
							}
						}
					}
				}
			}
			for (int j = 0; j < factors.length; j++) {
				if(Utils.isVarInFactor(factors[j], hidden[i]) && factors[j].getVariables().length>1) {
					addNum+=eliminate(factors[j], new Variable(network, hidden[i]));
					break;
				}
			}
			factors = Utils.removeOneRowFactors(factors);
			i++;
		}
		if(factors.length>1) {
			Factor x=null;
			Factor y=null;
			for (int j = 0; j < factors.length; j++) {
				if(x==null && Utils.isVarInFactor(factors[j],queryVar.split("=")[0])) {
					x = factors[j];
					continue;
				}
				if(y==null && Utils.isVarInFactor(factors[j],queryVar.split("=")[0])) {
					y = factors[j];
					continue;
				}
			}
			factors[0]=x;
			factors[1]=y;
			if(factors[0].getTable().size()<factors[1].getTable().size()) {
				mulNum+=join(factors[0],factors[1]);
				factors[1]=null;
				factors = Utils.removeNull(factors);
			}
			else {
				mulNum+=join(factors[1],factors[0]);
				factors[0]=null;
				factors = Utils.removeNull(factors);
			}
			normalise(factors[0]);
		}
		else {
			normalise(factors[0]);			
		}
		addNum+=factors[0].getTable().size()-1;
		for (int j = 0; j < factors[0].getTable().size(); j++) {
			if(factors[0].getTable().get(j).get(queryVar.split("=")[0]).equals(queryVar.split("=")[1])) {
				double d = Math.round(Double.parseDouble(factors[0].getTable().get(j).get("probability")) * 100000.0)/100000.0;
				//System.out.println(d+", "+addNum+", "+mulNum);
				res = d+","+addNum+","+mulNum;
				break;
			}
		}
		return res;
	}

	public int join(Factor x, Factor y) {
		int mulNum=0;
		// get common variables
		ArrayList<Variable> commonVars = new ArrayList<Variable>();
		for (int i = 0; i < x.getVariables().length; i++) {
			for (int j = 0; j < y.getVariables().length; j++) {
				if(x.getVariables()[i].getName().equals(y.getVariables()[j].getName()))
					commonVars.add(x.getVariables()[i]);
			}
		}
		// get all variables
		ArrayList<Variable> allVars = new ArrayList<Variable>();
		for (int i = 0; i < x.getVariables().length; i++) {
			allVars.add(x.getVariables()[i]);
		}
		for (int i = 0; i < y.getVariables().length; i++) {
			allVars.add(y.getVariables()[i]);
		}
		for (int i = 0; i < commonVars.size(); i++) {
			for (int j = 0; j < allVars.size(); j++) {
				if(commonVars.get(i).getName().equals(allVars.get(j).getName())) {
					allVars.remove(j);
					break;
				}
			}
		}
		// get not-common variables
		ArrayList<Variable> notCommon = (ArrayList<Variable>) allVars.clone();
		for (int i = 0; i < notCommon.size(); i++) {
			for (int j = 0; j < commonVars.size(); j++) {
				if(notCommon.get(i).getName().equals(commonVars.get(j).getName())) {
					notCommon.remove(i);
					break;
				}
			}
		}
		// set new factor z
		Variable[] tmp = new Variable[allVars.size()];
		Factor z = new Factor(tmp);
		int zIndex=0;
		for (int i = 0; i < tmp.length; i++) {tmp[i]=allVars.get(i);}
		// set factor z's values and probabilities
		String boolCombination;
		String boolCombCheck;
		for (int i = 0; i < x.getTable().size(); i++) {
			boolCombination="";
			ArrayList<HashMap<String, String>> xTable = x.getTable();
			HashMap<String, String> xRow = xTable.get(i);
			// get values of common variables from x
			for (int j = 0; j < commonVars.size(); j++) {
				boolCombination+=xRow.get(commonVars.get(j).getName());
			}
			
			for (int j = 0; j < y.getTable().size(); j++) {
				ArrayList<HashMap<String, String>> yTable = y.getTable();
				HashMap<String, String> yRow = yTable.get(j);
				boolCombCheck="";
				// get values of common variables from y
				for (int k = 0; k < commonVars.size(); k++) {
					boolCombCheck+=yRow.get(commonVars.get(k).getName());
				}
				if(boolCombination.equals(boolCombCheck)) {
					z.getTable().add(new HashMap<String, String>());
					// add new row to z
					// add common values
					for (int k = 0; k < commonVars.size(); k++) {
						z.getTable().get(zIndex).put(commonVars.get(k).getName(), xRow.get(commonVars.get(k).getName()));
						//z.getTable().get(zIndex).put(commonVars.get(k).getName(), yRow.get(commonVars.get(j).getName()));
					}
					// add not common values
					for (int k = 0; k < notCommon.size(); k++) {
						if(Utils.isVarInTable(x, notCommon.get(k))) {
							if(xRow.get(notCommon.get(k).getName())!=null)
								z.getTable().get(zIndex).put(notCommon.get(k).getName(), xRow.get(notCommon.get(k).getName()));
						}
						if(Utils.isVarInTable(y, notCommon.get(k))) {
							if(yRow.get(notCommon.get(k).getName())!=null) {
								String temp = yRow.get(notCommon.get(k).getName());
								z.getTable().get(zIndex).put(notCommon.get(k).getName(), yRow.get(notCommon.get(k).getName()));
							}
						}
					}
					// add probability
					double xProb = Float.parseFloat(xRow.get("probability"));
					double yProb = Double.parseDouble(yRow.get("probability"));
					z.getTable().get(zIndex).put("probability", ""+(xProb*yProb));
					zIndex++;
					mulNum++;
					//break;
				}
			}
		}
		x.setTable(z.getTable());
		x.setVariables(z.getVariables());
		return mulNum;
	}
	
	public int eliminate(Factor x, Variable y) {
		int addNum=0;
		int countRows=0;
		// create new factor without variable y
		Variable[] test = x.getVariables();
		ArrayList<String> varNames = new ArrayList<String>();
		for (int i = 0; i < test.length; i++) {
			if(!test[i].getName().equals(y.getName())) {
				varNames.add(test[i].getName());
			}
		}
		Variable[] vars = new Variable[varNames.size()];
		for (int i = 0; i < vars.length; i++) {
			vars[i]=new Variable(network, varNames.get(i));
		}
		Factor a = new Factor(network, vars);
		
		// for each combination match, sum up the probability of the row with the combination
		String boolCombination;
		String boolCombCheck;
		double prob = 0;
		for (int i = 0; i < a.getTable().size(); i++) {
			boolCombination="";
			for (int j = 0; j < vars.length; j++) {
				boolCombination+=a.getTable().get(i).get(vars[j].getName());
			}
			for (int j = 0; j < x.getTable().size(); j++) {
				boolCombCheck="";
				for (int k = 0; k < vars.length; k++) {
					boolCombCheck+=x.getTable().get(j).get(vars[k].getName());
				}
				if(boolCombCheck.equals(boolCombination)) {
					prob+=Double.parseDouble(x.getTable().get(j).get("probability"));
					if((++countRows)%2==0) addNum++;
				}
			}
			a.getTable().get(i).put("probability",""+prob);
			prob=0;
		}
		x.setTable(a.getTable());
		x.setVariables(a.getVariables());
		return addNum;
	}
	
	public void normalise(Factor x) {
		double prob=0;
		double temp;
		for (int i = 0; i < x.getTable().size(); i++) {
			prob+=Double.parseDouble(x.getTable().get(i).get("probability"));
		}
		for (int i = 0; i < x.getTable().size(); i++) {
			temp=Double.parseDouble(x.getTable().get(i).get("probability"));
			x.getTable().get(i).replace("probability", ""+(temp/prob));
		}
	}
	
	public ArrayList<String> getIrrelevantHidden(String queryVar, String[] evidence, String[] hidden) {
		BayesBall b = new BayesBall(network);
		boolean relevant;
		boolean relevant2;
		ArrayList<String> irrelevant = new ArrayList<String>();
		// is hidden variable is ancestor of query and evidence variables?
		int removed=0;
		for (int i = 0; i < hidden.length; i++) {
			relevant=true;
			relevant2=false;
			boolean[] isAncestorOfevidence = new boolean[evidence.length/2];
			BNode from = network.getNode(hidden[i]);
			BNode to = network.getNode(queryVar.split("=")[0]);
			if(!b.executeQuery(network.getNode(hidden[i]), network.getNode(queryVar.split("=")[0]))) {
				for (int j = 0; j < evidence.length; j+=2) {
					BNode tmpEv = network.getNode(evidence[j]);
					isAncestorOfevidence[j/2]=b.executeQuery(network.getNode(hidden[i]), network.getNode(evidence[j]));
				}
			}
			for (int j = 0; j < isAncestorOfevidence.length; j++) {
				if (isAncestorOfevidence[j]) {
					relevant2=true;
					break;
				}
			}
			if(!relevant && !relevant2) {
				irrelevant.add(hidden[i]);
				//hidden[i]=null;
				//hidden=Utils.removeNull(hidden);
				removed++;
				break;
			}
			
		}
		// is hidden variable cond. ind. of query variable?
		BNode[] ev = new BNode[evidence.length];
		for (int i = 0; i < ev.length; i++) {
			ev[i] = network.getNode(evidence[i]);
		}
		for (int i = 0; i < hidden.length; i++) {
			relevant=false;
			BNode from = network.getNode(hidden[i]);
			BNode to = network.getNode(queryVar.split("=")[0]);
			if(b.executeQueryVE(network.getNode(hidden[i]), network.getNode(queryVar.split("=")[0]), ev)) {
				relevant=true;
			}
			if(!relevant) {
				irrelevant.add(hidden[i]);
				//hidden[i]=null;
				//hidden=Utils.removeNull(hidden);
				removed++;
			}
		}
		return irrelevant;
	}
	public String[] removeIrrelevantHidden(ArrayList<String> irrelevant, String[] hidden) {
		for (int i = 0; i < hidden.length; i++) {
			for (int j = 0; j < irrelevant.size(); j++) {
				if(irrelevant.get(j).equals(hidden[i])){
					hidden[i]=null;
					break;
				}
			}
		}
		return hidden;
	}
	public void removeIrrelevantFactors(ArrayList<String> arr) {
		int removed=0;
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < arr.size(); j++) {
				if(Utils.isVarInFactor(factors[i], arr.get(j))){
					factors[i]=null;
					factors = Utils.removeNull(factors);
					removed++;
				}
			}
		}
	}
	public String[] sortByASCII(String[] arr) {
		for (int i = 0; i < arr.length-1; i++) {
			 for (int j = 0; j < arr.length-i-1; j++) {
				 int sum1=0;
				 int sum2=0;
				 for (int k = 0; k < arr[j].length(); k++) {
					sum1+=arr[j].codePointAt(k);
				 }
				 for (int k = 0; k < arr[j+1].length(); k++) {
						sum1+=arr[j+1].codePointAt(k);
				 }
				 if(sum1>sum2) {
					 String temp = arr[j+1];
					 arr[j+1]=arr[j];
					 arr[j]=temp;
				 }
			 }
		}
		return arr;
	}
}