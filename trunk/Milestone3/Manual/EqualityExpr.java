public class EqualityExpr extends ConditionalAndExpr
{
	public AdditiveExpr addExpr;
	public int op;
	public EqualityExpr expr;

		
	public static final int EQ = 1;// =
	public static final int NE = 2;// != 
	
	
	
	public EqualityExpr()
	{}
	
	
	
	public EqualityExpr(AdditiveExpr t, int o, EqualityExpr e) {
		addExpr = t;
		op = o;
		expr = e;
	}
	
	
	public String toString()
	{
		String ret = "EqualityExpression\n";

		String s = "";

		if(expr !=null) {
			s += expr.toString();
			
			if(op == EQ)
				s += "==\n";
			else
				s += "!=\n";
			
		}

		if(addExpr != null)
			s += addExpr.toString();

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";

		return ret;
	}
	public Boolean isBoolean(){
		Boolean result=true;
		if(addExpr!=null)
			result&=addExpr.isBoolean();
		
		if(expr!=null)
			result=true;
		
		return result ;
	}
	public String check() throws SemanticException{

		String expr1="";
		String expr2="";

		if(addExpr!=null)
			expr1=addExpr.check();
		if(expr!=null)
			expr2=expr.check();
		
	if(expr1!=null)
	{
		if(expr2!=null)
		if(expr1.equals(expr2))
			return expr1;
	}
	if(expr2!=null)
	{
		if(expr1!=null)
		if(expr2.equals(expr1))
			return expr1;
	}
			throw new SemanticException("Type mismatch in Expression.");

	}
}