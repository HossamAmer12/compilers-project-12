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
		 String ret = "AssignStmt " + this.varId + "\n"; 
		
		 String s = "";
		 
		if(expr !=null)
		{
			s += expr.toString();
		}

		 for(String st: s.split("\n"))
		 	ret += "| " + st + "\n";
		 
		// return ret;
		return ret;
	}
}