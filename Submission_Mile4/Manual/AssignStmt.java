public class AssignStmt
{
	String varId;
	Expression expr;
	Token token;
	String line;
	public AssignStmt()
	{}
	
	public AssignStmt(String idLexeme, Expression expr,Token t,String line)
	{
		this.varId = idLexeme;
		this.expr = expr;
		this.token=t;
		this.line=line;
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

		return ret;
	}

	public void check() throws SemanticException{

		if(SymbolTable.getInstance().contains(varId))
		{
			Entry e=SymbolTable.getInstance().get(varId);
			if(e.type!=null){
			if(!e.type.type.toString().equals(expr.check()) || expr.check()==null)
				Report.semanticError(token.line, token.at, String.format("Type mismatch for variable %s.",varId),line);
				
			}
		}else Report.semanticError(token.line, token.at, varId+" variable must be declared first.",line);
		
		
		
	}
}