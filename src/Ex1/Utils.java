package Ex1;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

	public static BNode[] removeNull(BNode[] arr) {
		int count=0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i]!=null) count++;
		}
		BNode[] ret = new BNode[count];
		for (int i = 0; i < ret.length; i++) {
			ret[i]=arr[i];
		}
		return ret;
	}
	public static Variable[] removeNull(Variable[] arr) {
		int count=0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i]!=null) count++;
		}
		Variable[] ret = new Variable[count];
		for (int i = 0; i < ret.length; i++) {
			ret[i]=arr[i];
		}
		return ret;
	}
	public static Factor[] removeNull(Factor[] arr) {
		int count=0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i]!=null) count++;
		}
		Factor[] ret = new Factor[count];
		for (int i = 0; i < ret.length; i++) {
			ret[i]=arr[i];
		}
		return ret;
	}
	public static String concatBooleans(String[][] vals, int fromWhere) {
		String concat = "";
		for (int i = fromWhere+1; i < vals[0].length; i++) {
			concat+=vals[0][i];
		}
		return concat;
	}
	public static String concatBooleans(String[] vals, int fromWhere) {
		String concat = "";
		for (int i = fromWhere+1; i < vals.length; i++) {
			concat+=vals[i];
		}
		return concat;
	}
	public static String concatBooleans(String[] vals, int fromWhere, int toWhere) {
		String concat = "";
		toWhere = vals.length==toWhere? toWhere-1 : toWhere;
		for (int i = fromWhere; i < toWhere; i++) {
			concat+=vals[i];
		}
		return concat;
	}
	public static void printBooleans(String[][] vals) {
		for (int i = 0; i < vals.length; i++) {
			for (int j = 0; j < vals[0].length; j++) {
				System.out.print(vals[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void printDoubleArr(double[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
	}
	public static void printTogether(String[][] vals, double[] arr) {
		for (int i = 0; i < vals.length; i++) {
			for (int j = 0; j < vals[0].length; j++) {
				System.out.print(vals[i][j]+" ");
			}
			System.out.print(arr[i]);
			System.out.print("\n");
		}
	}
	
	public static int getIndexOfVariable(Factor x, Variable v) {
		Variable[] tmp = x.getVariables();
		for (int i = 0; i < tmp.length; i++) {
			if(tmp[i].getName()==v.getName())
				return i;
		}
		return -1;
	}
	public static BNode varToBNode(Network n, Variable v) {
		BNode b = n.getNode(v.getName());
		return b;
	}
	public static String[][] CPTBooleanTable(BNode node){
		BNode[] tmp = removeNull(node.getFathers());
		int[] valNum = new int[tmp.length];
		int numOfOptions = 1;
		String[][] booleans;
		
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
		return booleans;
	}
	public static String[][] CPTBooleanTable(Variable[] variables){
		Variable[] tmp = removeNull(variables);
		int[] valNum = new int[tmp.length];
		int numOfOptions = 1;
		String[][] booleans;
		
		for (int i = 0; i < valNum.length; i++) {
			valNum[i]=tmp[i].getValues().length;
			numOfOptions*=valNum[i];
		}
		booleans = new String[numOfOptions][variables.length];
		// Initialize last column.
		int init = variables[variables.length-1].getValues().length;
		for (int i = 0; i < booleans.length; i++) {
			booleans[i][booleans[i].length-1] = variables[variables.length-1].getValues()[i%init];
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
		return booleans;
	}	
	public static void printHashMapArray(ArrayList<HashMap<String,String>> table) {
		for (int i = 0; i < table.size(); i++) {
			System.out.println(table.get(i)+"\n");
		}
	}
	public static boolean isVarInTable(Factor x, Variable v) {
		for (int i = 0; i < x.getVariables().length; i++) {
			if(x.getVariables()[i].getName().equals(v.getName()))
				return true;
		}
		return false;
	}
}
