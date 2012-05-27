public class AdditiveExpr extends EqualityExpr
{
	
	public MultiplicativeExpr multiplyExpr;
	public int op;
	public AdditiveExpr expr;
	
	
	public static final int PO = 1;// +
	public static final int MO = 2;// -
	public int lineNo;
	public int at;
	public String line;
	
	public AdditiveExpr()
	{}
	
	public AdditiveExpr(MultiplicativeExpr t, int o, AdditiveExpr e,int lineNo,int at,String line) {
		multiplyExpr = t;
		op = o;
		expr = e;
		this.line=line;
		this.lineNo=lineNo;
		this.at=at;
	}
	
	
	
	public String toString()
	{
		String ret = "AdditiveExpr\n";

		String s = "";

		if(expr !=null) {
			s += expr.toString();
			
			if(op == PO)
				s += "+\n";
			else
				s += "-\n";
			
		}
		
		if(multiplyExpr != null)
			s += multiplyExpr.toString();

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";

		return ret;

	}
	public Boolean isBoolean(){
		Boolean result=true;
		if(multiplyExpr!=null)
			result&=multiplyExpr.isBoolean();
		
		if(expr!=null)
			result&=false;
		
		return result ;
	}
	
	public String check() throws SemanticException {
		String expr1="";
		String expr2="";
		
		// Check for children types
		
		
		if(multiplyExpr!=null)
			expr1=multiplyExpr.check();
		
		if(expr!=null)
			expr2=expr.check();
		
		if(expr1.equals(expr2) && (expr1.equals("int") || expr1.equals("float") || (expr1.equals("String") && op==PO )))
			return expr1;
		else
			// Case where an expression contains expressions with different types
			{Report.semanticError(lineNo, at,"Type mismatch in Expression", line);
		return null;}

		
	

	}
}