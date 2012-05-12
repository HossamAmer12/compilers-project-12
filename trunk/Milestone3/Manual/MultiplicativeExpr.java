public class MultiplicativeExpr extends AdditiveExpr
{
	public PrimaryExpr primaryExpr;
	public int op;
	public MultiplicativeExpr expr;
	
			
	public static final int TO = 1;// *
	public static final int DO = 2;	// /	
	public static final int MD = 3;// %		
	
	public MultiplicativeExpr()
	{}

	
	public MultiplicativeExpr(PrimaryExpr t, int o, MultiplicativeExpr e) {
		primaryExpr = t;
		op = o;
		expr = e;
	}
	
	public String toString()
	{
		String ret = "MultiplicativeExpr\n";
		String s = "";

		
		
		if(expr !=null) {
			s += expr.toString();
			
			if(op == TO)
				s += "*\n";
			else if (op == DO)
				s += "/\n";
			else
				s += "%\n";
			
		}
			if(primaryExpr != null)
				s += primaryExpr.toString();


	

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";

		return ret;	
	}
	public Boolean isBoolean() {
		Boolean result=true;
		if(primaryExpr!=null)
			result&=primaryExpr.isBoolean();
		
		if(expr!=null)
			result&=false;
		
		return result ;
	}
	public String check() throws SemanticException{
		String expr1="";
		String expr2="";
		if(primaryExpr!=null)
			expr1=primaryExpr.check();
		
		if(expr!=null)
			expr2= expr.check();
		
		if(expr1.equals(expr2))
			return expr1;
		else
			return null;
		

	}
}