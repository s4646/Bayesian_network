package Ex1;

import java.util.Iterator;

public class CPT {
	private BNode node;
	private double[][] probas;
	private boolean[][] bools;
	
	public CPT(BNode n) {
		node=n;
		double[][] t = n.getCPT();
		probas=new double[t.length][t[0].length];
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t[i].length; j++) {
				probas[i][j]=t[i][j];
			}
		}
		int tmp = n.getNumFathers();
		if(tmp==0) {
			bools = new boolean[2][1];
		}
		else {
			bools = new boolean[(int)Math.pow(2, tmp)][tmp];
		}
	}
	
	public void setBools() {
		// to set a column, we need to fill 2^(n-1) cells with a boolean before switching to its opposite 
		// (n = num of vars until end of table)
		int j=0, tmp = node.getNumFathers();
		boolean bool;
		while(j<bools[0].length) {
			bool = true;
			int switchBool = tmp==0 ? 1 : (int)Math.pow(2,tmp-1-j);
			for (int i = 0; i < bools.length; i++) {
				if(switchBool==1) {
					if((i!=0) && i%switchBool==0) { // if we reached multiple of 2^(n-1), switch to the opposite of bool
						bool = !bool;					
					}
					bools[i][j]=bool;
				}
				else {
					if((i!=0 && i!=1) && i%switchBool==0) { // if we reached multiple of 2^(n-1), switch to the opposite of bool
						bool = !bool;
					}	
					bools[i][j]=bool;
				}
			}
			j++;
		}
	}
	
	public double[][] getCPT(){return probas;}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < probas.length; i++) {
			for (int j = 0; j < probas[i].length; j++) {
				 s+=probas[i][j]+" ";
			}
			s+="\n";
		}
		return s;
	}
}
