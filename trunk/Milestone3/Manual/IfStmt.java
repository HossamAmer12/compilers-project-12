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
		 String ret = "IfStmt\n";
		
		 String s = "";
		 
		if(expr !=null)
		{
			s +=  expr.toString();
		}
	 	
		if(stmt != null)
		{
			s += stmt.toString();
		}
	
		if(elseStmt != null)
		{
			s += elseStmt.toString();
		}
		 
		 for(String st: s.split("\n"))
		 	ret += "| " + st + "\n";
		 
			 
		 
		return ret;

	}
	
	public Boolean isBooleanStatement(Expression expr){
		boolean result=true;
	
		if(expr!=null){
			if(expr.andExpr!=null){
				if(expr.andExpr.equalityExpr!=null){
					if(expr.andExpr.equalityExpr.addExpr!=null)
						result=false;
				}
			}
		}
		
		return result;

	}

	public void check() throws SemanticException{
	
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
		
		if(elseStmt!=null)
			elseStmt.check();
	
		
	}
}