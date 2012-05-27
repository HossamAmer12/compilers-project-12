public class IfStmt
{
	
	public Expression expr;
	public Statement stmt;
	public Statement elseStmt;
	
	public int lineNo;
	public int at;
	public String line;
	public IfStmt()
	{}

	public IfStmt(Expression expr, Statement stmt,int lineNo,int at,String line)
	{
		this.expr = expr;
		this.stmt = stmt;
		this.line=line;;
		this.lineNo=lineNo;
		this.at=at;
	}

	
	public IfStmt(Expression expr, Statement stmt, Statement elseStmt,int lineNo,int at,String line)
	{
		this.expr = expr;
		this.stmt = stmt;
		this.elseStmt = elseStmt;
		this.line=line;
		this.lineNo=lineNo;
		this.at=at;

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

	public void check(MethodDecl method) throws SemanticException{
	
		if(expr!=null)
		{

			expr.check();
			
			// Semantic Check: For Condition Mismatch
				if(!expr.isBoolean()){
						Report.semanticError(lineNo, at,String.format("Condition type mismatch for %s: Must be boolean.","If Statement"), line);
				
					}
				
		}

		SymbolTable.getInstance().openScope();
		if(stmt!=null)
			stmt.check(method);
		SymbolTable.getInstance().closeScope();
		
		SymbolTable.getInstance().openScope();
		if(elseStmt!=null)
			elseStmt.check(method);
		SymbolTable.getInstance().closeScope();
	
		
	}

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