package Ex1;

import java.util.ArrayList;

public class VariableElimination {
	private Network network;
	private Factor[] factors;
	
	public VariableElimination(Network n) {
		network=n;
		factors = new Factor[n.getNetwork().length];
	}
	public void setFactors() {
		for (int i = 0; i <network.getNetwork().length; i++) {
			factors[i] = new Factor(network.getNetwork()[i]);
		}
	}
	public Factor[] getFactors() {return factors;}
	
	public void readQueries(String[] queries) {
		for (int i = 0; i < 1; i++) { // change to len
			queries[i]=queries[i].substring(2);
			String queryVar = queries[i].split("\\|")[0];
			String _evidence = queries[i].split("\\|")[1];
			_evidence = _evidence.split("\\) ")[0];
			String _hidden = queries[i].split("\\) ")[1];
			String[] hidden = _hidden.split("-");
			String[] evidence = new String[2*_evidence.split(",").length];
			for (int j = 0; j < _evidence.split(",").length; j++) {
				evidence[2*j]=_evidence.split(",")[j].split("=")[0]; // evidence variable
				evidence[2*j+1]=_evidence.split(",")[j].split("=")[1]; // evidence value
			}
			executeQuery(queryVar,evidence,hidden);
			//setFactors();
		}
	}
	
	public void executeQuery(String queryVar, String[] evidence, String[] hidden) {
		for (int i = 0; i < factors.length; i++) { // set initial Factors for query
			factors[i].removeEvidence(evidence);
		}
		
	
	
	}
	
	
	public void join(Factor x, Factor y) {
		
		
	}
	public void eliminate(Factor x, Variable y) {
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
				}
			}
			a.getTable().get(i).put("probability",""+prob);
			prob=0;
		}
		x.setTable(a.getTable());
		x.setVariables(a.getVariables());
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
}
