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

//	public void check() throws SemanticException{
//	
//		if(stmt!=null)
//			stmt.check();
//		
//		if(expr!=null)
//		{
//
//			expr.check();
//			
//			// Semantic Check: For Condition Mismatch
//				if(!expr.isBoolean()){
//						throw new SemanticException("If Statement",SemanticException.CONDITION_MISMATCH);
//					}
//				
//		}
//		
//		if(elseStmt!=null)
//			elseStmt.check();
//	
//		
//	}

	public boolean HasReachableReturns() {
		boolean ifStmtReturn=false;
		boolean elseStmtReturn=false;
		
		// Contains a Block
		if(stmt!=null)
		if(stmt.block!=null){
			
			if(stmt.block.statements!=null){
				for(Statement st:stmt.block.statements){
					
					if(st.returnStmt!=null)
						ifStmtReturn=true;
					
					if(st.ifStmt!=null)
						ifStmtReturn=st.ifStmt.HasReachableReturns();
						
				}
			}
			
		}else{
			if(stmt.returnStmt!=null)
				ifStmtReturn=true;
			if(stmt.ifStmt!=null)
				ifStmtReturn=stmt.ifStmt.HasReachableReturns();
		}
		
		
		
		// Contains a Block
		if(elseStmt!=null)
		if(elseStmt.block!=null){
			
			if(elseStmt.block.statements!=null){
				for(Statement st:elseStmt.block.statements){
					
					if(st.returnStmt!=null)
						elseStmtReturn=true;
					
					if(st.ifStmt!=null)
						elseStmtReturn=st.ifStmt.HasReachableReturns();
						
				}
			}
			
		}else{
			if(elseStmt.returnStmt!=null)
				elseStmtReturn=true;
			if(elseStmt.ifStmt!=null)
				elseStmtReturn=elseStmt.ifStmt.HasReachableReturns();
		}
		
		
		return ifStmtReturn && elseStmtReturn;
	}
}