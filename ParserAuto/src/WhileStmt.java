public class WhileStmt
{
	
	public Expression expr;
	public Statement stmt;
	public int lineNo;
	public int at;
	public String line;
	public WhileStmt()
	{
		
	}
	
	public WhileStmt(Expression expr, Statement stmt,int lineNo,int at,String line)
	{
		this.expr = expr;
		this.lineNo=lineNo;
		this.line=line;
		this.at=at;
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

	public void check(MethodDecl method) throws SemanticException {
		
		if(stmt!=null)
			stmt.check(method);
		
		if(expr!=null)
		{
			expr.check();

			// Semantic Check: For Condition Mismatch
				if(!expr.isBoolean()){
						Report.semanticError(lineNo, at, String.format("Condition type mismatch for %s: Must be boolean.","If Statement"), line);
	
					}
				
		}
		
	}
}