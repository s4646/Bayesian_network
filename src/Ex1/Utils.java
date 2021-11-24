package Ex1;

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
}
