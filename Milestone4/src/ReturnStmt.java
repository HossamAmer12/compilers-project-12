public class ReturnStmt
{
	Expression expression;
	public ReturnStmt()
	{}
	
	public ReturnStmt(Expression expression)
	{
		this.expression = expression;
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
			throw new SemanticException("Type mismatch in return statement for method "+method.methodID);
		
		if(expression!=null){
			expression.check();
		}
	}
}