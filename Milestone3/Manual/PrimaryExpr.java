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
		//System.out.println("CONSSTTTT"+idLexeme);
		
	}
	
	public PrimaryExpr(Expression expr)
	{
		this.expr = expr;
				

		
	}

	//XX Unused constructor with //XX Unused var
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
	
}