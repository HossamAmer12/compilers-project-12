public class IfStmt
{
	
	public Expression expr;
	public Statement stmt;
	public Statement elseStmt;
	
	public IfStmt()
	{}

	public IfStmt(Expression expr, Statement stmt)
	{
		this.expr = expr;
		this.stmt = stmt;
	}

	
	public IfStmt(Expression expr, Statement stmt, Statement elseStmt)
	{
		this.expr = expr;
		this.stmt = stmt;
		this.elseStmt = elseStmt;
	}
	
	
	public String toString()
	{
		return "";
	}
}