public class MethodDecl
{
	public Type type;
	public FormalParams formalParams;
	public Block block;
	String methodID;
	
	public static final int LB = 1;
	public static final int RB = 2;
	public static final int ID = 3; // for id
	public static final int KW = 4; // for static
	
	public MethodDecl()
	{	
	}
	
	public MethodDecl(Type type, FormalParams formalParams, Block block, String methodID)
	{
		this.type = type;
		this.formalParams = formalParams;
		this.block = block;
		this.methodID = methodID;
	}
	
	
	public String toString() {
		 String ret = "MethodDecl " + methodID + "\n";
		
		 String s = "";
		 
		 // s += formalParams.toString() + block.toString();
		if(formalParams != null)
		{
			s += formalParams.toString();
		}	
		
		if(block !=null)
		{
			s += block.toString();
		}
		 
		 for(String st: s.split("\n"))
		 	ret += "| " + st + "\n";
		 
		// return ret;
		return ret;
	}

	public void check() throws SemanticException {
		// TODO Auto-generated method stub
		if(formalParams!=null)
		{
			SymbolTable.getInstance().add(new Entry(methodID, formalParams, type));
			if(block!=null)
				block.check();
		}
		
	}
	
}