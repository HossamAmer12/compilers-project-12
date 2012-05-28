public class ReturnStmt
{
	Expression expression;
	int lineNo;
	String line;
	int at;
	public ReturnStmt()
	{}
	
	public ReturnStmt(Expression expression,int lineNo,int at,String line)
	{
		this.expression = expression;
		this.line=line;
		this.lineNo=lineNo;
		this.line=line;
	}
	
	
	public String toString()
	{	 
		String ret = "Return\n";
		
		 String s = "";
		 
		if(expression !=null)
		{
			s += expression.toString();
		}

		 for(String st: s.split("\n"))
		 	ret += "| " + st + "\n";
		 
		return ret;
	
	}

	public void check(MethodDecl method)  throws SemanticException{
	

		if(!expression.check().equals(method.type.type))
			Report.semanticError(lineNo, at, "Type mismatch in return statement for method "+method.methodID,line);
		
		
		if(expression!=null){
			expression.check();
		}
	}
}