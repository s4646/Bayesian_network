package Ex1;

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
	public void join(Factor x, Factor y) {
		
	}
	
	public void executeQuery(String queryVar, String[] evidence, String[] hidden) {
		for (int i = 0; i < factors.length; i++) { // set initial Factors for query
			factors[i].removeEvidence(evidence);
		}
	}
}
