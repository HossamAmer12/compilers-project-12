public class PrimaryExpr extends MultiplicativeExpr
{
	
	public int num;
	public float number;
	public String bool; // true or false
	public String string;// string value
	public String idLexeme;// identifier name
	
	public Expression expr;
	public CallExpr callExpr;// XX Unused
	public ActualParams actualParams;
	public String callerMethodName;
	
	
	
	public PrimaryExpr()
	{
		num = -1;
		number = -1;
		bool = "";
		string = "";
		idLexeme = "";
		callerMethodName = "";
	}
	
	public PrimaryExpr(int num)
	{
		this.num = num;
			number = -1;
			bool = "";
			string = "";
			idLexeme = "";
			callerMethodName = "";
	}
	
	public PrimaryExpr(float num)
	{
		this.number = num;
			num = -1;
			bool = "";
			string = "";
			idLexeme = "";
			callerMethodName = "";
	}
	
	public PrimaryExpr(String bool, String string, String idLexeme)
	{
		this.bool = bool;
		this.string = string;
		this.idLexeme = idLexeme;
		num = -1;
		number = -1;
		callerMethodName = "";
		
	}
	
	public PrimaryExpr(Expression expr)
	{
		this.expr = expr;
				
		num = -1;
		number = -1;
		bool = "";
		string = "";
		idLexeme = "";
		callerMethodName = "";
		
	}

	//XX Unused constructor with //XX Unused var
	public PrimaryExpr(CallExpr callExpr)
	{
		this.callExpr = callExpr;
		
		num = -1;
		number = -1;
		bool = "";
		string = "";
		idLexeme = "";
		callerMethodName = "";
		
	}
	
	public PrimaryExpr(ActualParams actualParams, String callerMethodName)
	{
		this.actualParams = actualParams;
		this.callerMethodName = callerMethodName;
		
		num = -1;
		number = -1;
		bool = "";
		string = "";
		idLexeme = "";
		
		
	}
	
	public String toString() {
		 String ret = "\n";
		
		 String s = "";
		
		if(expr !=null)
		{
			s += expr.toString();
		}
		else if(actualParams != null)
		{
			s += callerMethodName  + " " + actualParams.toString();
		}
		
		if(num != -1)
			s += callerMethodName + bool + string + idLexeme + num;
		else if(number != -1)
			s += callerMethodName + bool + string + idLexeme + number;
		else
			s += callerMethodName + bool + string + idLexeme;
	 	
		 for(String st: s.split("\n"))
		 	ret += "| " + st + "\n";
		 
		// return ret;
		return ret;
			
		}
	
}