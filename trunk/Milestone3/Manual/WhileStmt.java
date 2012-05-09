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
                    String ret = "WhileStmt\n";

                    String s = "";

                    if(expr !=null)
                    {
                            s += expr.toString();
                    }

                    if(stmt != null)
                    {
                            s += stmt.toString();
                    }

                     for(String st: s.split("\n"))
                            ret += "| " + st + "\n";

                    return ret;
    }
}