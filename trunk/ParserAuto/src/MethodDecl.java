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
	
	public int lineNo;
	public String line;
	public int at;

	public MethodDecl()
	{	
	}
	
	public MethodDecl(Type type, FormalParams formalParams, Block block, String methodID,int lineNo,int at,String line)
	{
		this.type = type;
		this.at=at;
		this.lineNo=lineNo;
		this.line=line;
		this.formalParams = formalParams;
		this.block = block;
		this.methodID = methodID;
	}
	
	
	public String toString() {
		 String ret = "MethodDecl " + methodID + "\n";
		
		 String s = "";
		 

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
		 
		return ret;
	}

	public boolean hasDirectReturn(){
		boolean result=false;
		for(Statement stmt:block.statements){
			if(stmt.getType()=="ReturnStmt"){
				result=true;
				break;
			}
		}
		return result;
	}
	public void check() throws SemanticException {

		SymbolTable.getInstance().openScope();
	
		if(formalParams!=null)
			formalParams.check();
		
			if(block!=null){
			block.check(this);
			
			boolean containsReturnStmt=hasDirectReturn();
			
			if(!containsReturnStmt){
				if(block.statements!=null){
					boolean containsIfStmt=false;
					for(Statement stmt:block.statements){
						if(stmt.getType()=="IfStmt"){
							containsIfStmt=true;
							if(!stmt.ifStmt.HasReachableReturns())
								Report.semanticError(lineNo, at,String.format("Method %s must have a reachable return statement.",methodID), line);
								
							
						}
					}
					if(!containsIfStmt)
						Report.semanticError(lineNo, at,String.format("Method %s must have a reachable return statement.",methodID), line);
				}
			
			}
			}
			
		SymbolTable.getInstance().closeScope();
		
	}
	
}