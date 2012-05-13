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

		return ret;
	}

	public void check() throws SemanticException{

		if(SymbolTable.getInstance().contains(varId))
		{
			Entry e=SymbolTable.getInstance().get(varId);
			if(e.type!=null){
			if(!e.type.type.toString().equals(expr.check()) || expr.check()==null)
					throw new SemanticException(varId,SemanticException.TYPE_MISMATCH);
				
			}
		}else throw new SemanticException(varId,SemanticException.VAR_DECLARATION);
		
	}
}