public class Statement
{
	public Block block;
	public LocalVarDecl localVarDecl;
	public AssignStmt assignStmt;
	public IfStmt ifStmt;
	public WhileStmt whileStmt;
	public ReturnStmt returnStmt;
	
	int empty;// 0 for full, 1 empty
	
	
	public Statement()
	{}
	
	public Statement(int empty)
	{
		this.empty = empty;
	}
	
	public Statement(Block block)
	{
		this.block = block;
	}

	public Statement(LocalVarDecl localVarDecl)
	{
		this.localVarDecl = localVarDecl;
	}
	
	public Statement(AssignStmt assignStmt)
	{
		this.assignStmt = assignStmt;
	}
	
	public Statement(IfStmt ifStmt)
	{
		this.ifStmt = ifStmt;
	}
	
	public Statement(WhileStmt whileStmt)
	{
		this.whileStmt = whileStmt;
	}
	
	public Statement(ReturnStmt returnStmt)
	{
		this.returnStmt = returnStmt;
	}
	
	public String getType(){
		if(block !=null)
		{
			return "Block";
		}
		else if(localVarDecl != null)
		{
			return "LocalVarDecl";
		}
		else if(assignStmt != null)
		{
			return "AssignStmt";
		}
		else if(ifStmt != null)
		{
			return "IfStmt";
		}
		else if(whileStmt != null)
		{
			return "WhileStmt";
		}
		else{return "ReturnStmt";}
	}
	public String toString()
	{
		 String ret = "";
		
		 String s = "";
		 
		if(block !=null)
		{
			s += block.toString();
		}
		else if(localVarDecl != null)
		{
			s += localVarDecl.toString();
		}
		else if(assignStmt != null)
		{
			s += assignStmt.toString();
		}
		else if(ifStmt != null)
		{
			s += ifStmt.toString();
		}
		else if(whileStmt != null)
		{
			s += whileStmt.toString();
		}
		else{
			s += returnStmt.toString();}
		 
		 for(String st: s.split("\n"))
		 	ret += "| " + st + "\n";
		 

		// return ret;
		return ret;
		
	}

//	public void check() throws SemanticException  {
//		
//		if(getType().equals("LocalVarDecl")){
//			localVarDecl.check();
//			return;
//		} 
//		
//		if(getType().equals("WhileStmt")){
//			whileStmt.check();
//			return;
//		}
//		
//		if(getType().equals("AssignStmt")){
//			assignStmt.check();
//			return;
//		}
//		
//		if(getType().equals("ReturnStmt")){
//			returnStmt.check();
//			return;
//		}
//		
//		if(getType().equals("Block")){
//			block.check();
//			return;
//		}
//		
//		if(getType().equals("IfStmt")){
//			ifStmt.check();
//			return;
//			
//		}
//		
//		
//	}
	
}