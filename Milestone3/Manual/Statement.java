public class Statement
{
	public Block block;
	public LocalVarDecl localVarDecl;
	public AssignStmt assignStmt;
	public IfStmt ifStmt;
	public WhileStmt whileStmt;
	public ReturnStmt returnStmt;
	
	
	public Statement()
	{}
	
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
	
	public String toString()
	{
		return block.toString() + ", " + localVarDecl.toString() 
			+ ", " + 	assignStmt.toString()
			+ ", " + ifStmt.toString() + ", "
			+ ", " + whileStmt.toString()
			+ ", " + returnStmt.toString();
	}
	
}