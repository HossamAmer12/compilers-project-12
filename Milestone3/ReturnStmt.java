public class ReturnStmt
{
	Expression expression;
	public ReturnStmt()
	{}
	
	public ReturnStmt(Expression expression)
	{
		this.expression = expression;
	}
	
	
	public String toString()
	{
		return "Return";
	}
}