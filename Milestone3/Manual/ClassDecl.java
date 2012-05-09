/*
 * This class should be the root of your parse tree.
 * 
 * Update the class with the variables and constructors
 * you see required.
 * 
 * Update the toString method to print the tree.
 * 
 * Update the check method such that it checks the
 * tree for semantic errors.
 */

import java.util.ArrayList;

public class ClassDecl {
	
	public MethodDecls methodDelcs;
	
	// public ArrayList<MethodDecl> mDecls;
	
	public static final int LB = 1;
	public static final int RB = 2;
	public static final int ID = 3;
	public static final int KW = 4;

	
	public ClassDecl() {
		
		// this.mDecls = new ArrayList<MethodDecl> ();
	}
	
	public ClassDecl(MethodDecls mDecls)
	{
			this.methodDelcs = mDecls;
	}
		
	public String toString() {
		

		
		String ret = "ClassDecl\n";
		
		String s = "";
		// if(methodDelcs != null) {
			

			s += methodDelcs.toString();
				// if(op == PO)
				// 	s += "+\n";
				// 	else
					// s += "-\n";
			// }

				// s += tr.toString();

				for(String st: s.split("\n"))
					ret += "| " + st + "\n";


				return ret;
	
	}
	
	public void check() throws SemanticException {
		throw new SemanticException("");
	}
}
