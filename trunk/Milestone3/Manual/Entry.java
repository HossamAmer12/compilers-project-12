/*
 * An entry in the symbol table.
 * 
 * Update this class with the information
 * you need to keep in the symbol table
 * for each entry.
 */

public class Entry {
	
	/*
	 * level is a variable used to know in switch
	 * scope level this entry was created.
	 * 
	 * This variable is used only by the Symbol Table
	 * and should remain public
	 */
	public int level;
	
	/*
	 * The identifier of the entry. The variable should
	 * remain public as it is used by the Symbol Table
	 * as the key for the entry in the Hash Table.
	 */
	public String id;
	
	
	public Type type;
	
	public boolean isMethod;
	
	public FormalParams params;
	
	public MethodDecl method;
	

	public Entry(String s,Type type){
		id=s;
		this.type = type;
	}
	
	public Entry(String s,Type type,MethodDecl method){
		id=s;
		this.type=type;
		this.method=method;
		
	}
	public Entry(String s,Type type,boolean isMethod,FormalParams params){
		id=s;
		this.type=type;
		this.isMethod=isMethod;
		this.params=params;
	}

}
