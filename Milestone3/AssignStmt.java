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
		System.out.println("Hello from here");
		return "AssignStmt " + this.varId 
		 + ", Expression " + expr.toString();
	}
}