package Ex1;

public class Variable {
	private String name;
	private double[] probabilities;
	
	public Variable(BNode n) {
		name = n.getName();
		
		String x = n.getProbs();
		String[] arr = x.split(" ");
		probabilities = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			probabilities[i] = Double.parseDouble(arr[i]);
		}
	}
	
	public String getName() {return name;}
	public double[] getProbas() {return probabilities;}
}
