public class AssignStmt
{
	String varId;
	Expression expr;
	
	public AssignStmt()
	{}
	
	public AssignStmt(String idLexeme, Expression expr)
	{
		this.varId = idLexeme;
		this.expr = expr;
	}
	
	public String toString()
	{
		return "AssignStmt " + this.varId;
	}
}