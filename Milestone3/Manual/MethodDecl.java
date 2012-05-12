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

	public void check() throws SemanticException {

		SymbolTable.getInstance().openScope();
	
		if(formalParams!=null)
			formalParams.check();
		
			if(block!=null){
			block.check();
			
			boolean containsReturnStmt=false;
		// Check for Return Stmts
			if(block.statements!=null){
				for(Statement stmt:block.statements){
					if(stmt.getType().equals("ReturnStmt")){
						containsReturnStmt=true;
						if(!stmt.returnStmt.expression.check().equals(type.type))
							throw new SemanticException("Type mismatch in return statement for method "+methodID);
					}
				}
			}
			
		// Check for Method Return Stmt exists
			if(!containsReturnStmt){
				if(block.statements!=null){
					boolean containsIfStmt=false;
					for(Statement stmt:block.statements){
						if(stmt.getType()=="IfStmt"){
							containsIfStmt=true;
							if(!stmt.ifStmt.HasReachableReturns())
								throw new SemanticException(String.format("Method %s must have a return statement.",methodID));
							
								
						}
					}
					if(!containsIfStmt)
						throw new SemanticException(String.format("Method %s must have a return statement.",methodID));
				}
			
			}
			}
			
		SymbolTable.getInstance().closeScope();
		
	}
	
}