package otc.drivers;

public class MyError {
	public static boolean debug = false;
	public static boolean pretty_print = false;
	public static boolean sym_pretty_print = false;
	private static int nb_errors = 0;
	
	public static void debugln(String phase, String msg) {
		System.out.println(""+phase+": "+msg+"");
	}
	public static void debugln(String msg) {
		System.out.println(""+msg+"");
	}
	public static void debug(String phase, String msg) {
		System.out.print(""+phase+": "+msg+"");
	}
	public static void debug(String msg) {
		System.out.print(""+msg+"");
	}

	public static void error(String file, String msg, int lineno) {
		nb_errors++;
		System.err.println(file+":"+lineno+": "+msg);
	}
	public static void error(String file, String msg, int lineno, int pos) {
		nb_errors++;
		System.err.println(file+":"+lineno+","+pos+": "+msg);
	}

	public static void globalError(String msg) {
		nb_errors++;
		System.err.println(msg);
	}
	
	public static void fatalError(String file, String phase, 
	                              String msg, int lineno) {
		System.err.println("Fatal error in "+phase+
		                   " at line "+lineno+": "+msg);
		System.err.println(
			"This should never happen! Please submit a bug report.");
		System.exit(1);
	}
	public static void fatalError(String phase, String msg) {
		System.err.println("Fatal error in "+phase+": "+msg);
		System.err.println(
			"This should never happen! Please submit a bug report.");
		System.exit(1);
	}
	
	public static void noErrors() {
		if (nb_errors > 0) {
			System.out.println(nb_errors+" error"+(nb_errors>1 ? "s" : ""));
			System.exit(1);
		}
	}
}   