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
		String ret = "then Return\n";
		
		 String s = "";
		 
		if(expression !=null)
		{
			s += expression.toString();
		}

		 for(String st: s.split("\n"))
		 	ret += "| " + st + "\n";
		 
		return ret;
	
	}

//	public void check()  throws SemanticException{
//	
//		if(expression!=null){
//			expression.check();
//		}
//	}
}