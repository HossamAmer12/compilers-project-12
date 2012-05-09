public class WhileStmt
{
	
	public Expression expr;
	public Statement stmt;
	
	public WhileStmt()
	{
		
	}
	
	public WhileStmt(Expression expr, Statement stmt)
	{
		this.expr = expr;
		this.stmt = stmt;
	}
	
	public String toString()
	{
		return "";
	}
}