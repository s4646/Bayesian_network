package Ex1;

public class Factor {
	private Network network;
	private BNode node;
	private double[] probabilities;
	private String[][] booleans;
	private Variable[] variables;
	
	public Factor(BNode n) {
		network=null;
		node=n;
		setFactorBooleans(); // set Booleans
		setFactorProbas(); // set probabilities
		variables = new Variable[n.getNumFathers()+1];
		setVariables();
	}
	public Factor(Network n, Variable[] v) {
		network=n;
		variables=v;
		node=null;
	}
	
	public void setVariables() {
		BNode[] temp = Utils.removeNull(node.getFathers());
		for (int i = 0; i < temp.length; i++) {
			variables[i] = new Variable(temp[i]);
		}
		variables[variables.length-1] = new Variable(node);
	}
	public void setVariables(Variable[] arr) {variables=arr;}
	
	public void setFactorBooleans() {
		BNode[] tmp = Utils.removeNull(node.getFathers());
		int[] valNum = new int[tmp.length];
		int numOfOptions = 1;
		
		for (int i = 0; i < valNum.length; i++) {
			valNum[i]=tmp[i].getValues().length;
			numOfOptions*=valNum[i];
		}
		numOfOptions*=node.getValues().length;
		int temp = node.getNumFathers();
		if(temp==0) {
			booleans = new String[2][1];
		}
		else {
			booleans = new String[numOfOptions][temp+1];
		}
		// Initialize last column.
		int init = node.getValues().length;
		for (int i = 0; i < booleans.length; i++) {
			booleans[i][booleans[i].length-1] = node.getValues()[i%init];
		}
		// initialize rest of columns by initialized columns
		for (int i = tmp.length-1; i > -1; i--) {
			init = tmp[i].getValues().length;
			String check1 = Utils.concatBooleans(booleans,i); // get combination
			int k = 0;
			for (int j = 0; j < booleans.length; j++) {
				String check2 = Utils.concatBooleans(booleans[j],i);
				if(check1.equals(check2)) {
					String insert = tmp[i].getValues()[k%init]; // insert new boolean into combination
					booleans[j][i] = insert;
					k++;
				}
				else booleans[j][i] = booleans[j-1][i];
			}
		}
	}
	
	public void setFactorProbas() {
		String x = node.getProbs();
		String[] arr = x.split(" ");
		probabilities = new double[arr.length];
		
		for (int i = 0; i < arr.length; i++) {
			probabilities[i] = Double.parseDouble(arr[i]);
		}
	}
	
	public String[][] getFactorBooleans(){return booleans;}
	public double[] getFactorProbas() {return probabilities;}
	public Variable[] getVariables() {return variables;}
	
	public String toString() {
		String s = "";
		return s;
	}
}
