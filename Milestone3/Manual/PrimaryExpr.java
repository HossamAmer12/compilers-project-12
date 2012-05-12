public class PrimaryExpr extends MultiplicativeExpr
{

	public String bool; // true or false
	public String string;// string value
	public String idLexeme;// identifier name
	public String number;
	
	public Expression expr;
	public CallExpr callExpr;// XX Unused
	public ActualParams actualParams;
	public String callerMethodName;
	
	public int type;
	
	
	
	public PrimaryExpr()
	{
	}
	
	public PrimaryExpr(String num)
	{
		this.number=num;
	}

	
	public PrimaryExpr(String bool, String string, String idLexeme)
	{
		this.bool = bool;
		this.string = string;
		this.idLexeme = idLexeme;
		
	}
	
	public PrimaryExpr(Expression expr)
	{
		this.expr = expr;
				

		
	}


	public PrimaryExpr(CallExpr callExpr)
	{
		this.callExpr = callExpr;
		
		
	}

	public PrimaryExpr(ActualParams actualParams, String callerMethodName)
	{
		this.actualParams = actualParams;
		this.callerMethodName = callerMethodName;
		
	}
	
	public String toString() {
		
		 String ret = "";
		
		 String s = "";

		if(expr !=null)
		{
			s += expr.toString();
		}
		else if(actualParams != null)
		{
			s += callerMethodName  + " " + actualParams.toString();
		}
		else if(number != null)
			s +=number;
		else if(bool !=null)
			s += bool;
		else if(string !=null)
			s += string;
		else if(idLexeme !=null)
		{
			s += idLexeme;
		}
	 	
		 for(String st: s.split("\n"))
		 	ret += "| " + st + "\n";
		 
		return ret;
			
		}
	
	private String getTypeOfExpr() {
		// Get Type of Expression
		String result="";
		if(expr !=null)
			result= "Expression";
		
		else if(actualParams != null)
		{

			Entry e=SymbolTable.getInstance().get(callerMethodName);

			if(e!=null)
			if(e.type.type!=null){
				return e.type.type;
			}
		}
		
		else if(number != null)
		{
			if(!number.contains("."))
				result= "int";
			else
				result="float";
		}
		
		else if(bool !=null)
			result= "boolean";
		
		else if(string !=null)
			result= "String";
		
		else if(idLexeme !=null)
		{
			Entry en=SymbolTable.getInstance().get(idLexeme);
			if(en!=null)
			if(en.type.type!=null)
				return en.type.type;
		}
		return result;
	}
	public Boolean isBoolean(){

		Boolean result=false;
		if(getTypeOfExpr().equals("boolean"))
			result=true;
		
		if(getTypeOfExpr().equals("Expression"))
			result=expr.isBoolean();
	
		return result ;
	}
	public String check() throws SemanticException{

		// Semantic Check: Variable must be declared first.
		if(idLexeme!=null){
		if(!SymbolTable.getInstance().contains(idLexeme)){
		
			throw new SemanticException(idLexeme,SemanticException.VAR_DECLARATION);
		}
		}
		
		// Semantic Check: Check for Calling methods
		if(callerMethodName!=null){
	
			if(!SymbolTable.getInstance().contains(callerMethodName))
			{
				throw new SemanticException(callerMethodName,SemanticException.METHOD_UNDEFINED);
			}else{
				
				Entry e=SymbolTable.getInstance().get(callerMethodName);

				if(e!=null)
					
					// Make sure it's a method
				if(!e.isMethod)
					throw new SemanticException(callerMethodName,SemanticException.METHOD_UNDEFINED);
				else
				{
				
					// Check that parameters
					if(e.params!=null){
						if(actualParams!=null)
						{
							if(e.params.size()!=actualParams.actualParmas.size())
								throw new SemanticException(callerMethodName,SemanticException.METHOD_UNDEFINED);
						
							for(int i=0;i<e.params.size();i++){
								Expression actual=actualParams.actualParmas.get(i);
								if(!e.params.get(i).type.type.equals(actual.check()))
									throw new SemanticException(callerMethodName,SemanticException.METHOD_MISSING_ARGUMENTS);
								
							}
						}else throw new SemanticException(callerMethodName,SemanticException.METHOD_MISSING_ARGUMENTS);
					}
				}
				
			}
		}
		
	
		if(getTypeOfExpr().equals("Expression")){
				return expr.check();}
		else
			return getTypeOfExpr();
	}
	
}