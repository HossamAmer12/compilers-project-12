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

	public void check() throws SemanticException {
		
		if(stmt!=null)
			stmt.check();
		
		if(expr!=null)
		{
			expr.check();

			// Semantic Check: For Condition Mismatch
				if(!expr.isBoolean()){
						throw new SemanticException("If Statement",SemanticException.CONDITION_MISMATCH);
					}
				
		}
		
	}
}