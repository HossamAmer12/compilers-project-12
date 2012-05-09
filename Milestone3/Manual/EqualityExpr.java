public class EqualityExpr extends ConditionalAndExpr
{
	public AdditiveExpr addExpr;
	public int op;
	public EqualityExpr expr;

		
	public static final int EQ = 1;// =
	public static final int NE = 2;// != 
	
	
	
	public EqualityExpr()
	{}
	
	// public Expression(String idLexeme, Expression expr)
	// {
	// 	this.varId = idLexeme;
	// 	this.expr = expr;
	// }
	
	
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
				s += "==";
			else
				s += "!=";
			
		}

		s += addExpr.toString();

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";

		return ret;
	}
}