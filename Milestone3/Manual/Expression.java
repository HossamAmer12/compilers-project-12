public class Expression
{
	public ConditionalAndExpr andExpr;
	// public int op;
	public Expression expr;
	
	
	public Expression()
	{}
	
	// public Expression(String idLexeme, Expression expr)
	// {
	// 	this.varId = idLexeme;
	// 	this.expr = expr;
	// }
	
	public Expression(ConditionalAndExpr t, Expression e) {
		andExpr = t;
		// op = o;
		expr = e;
	}
	
	
	public String toString()
	{
		String ret = "";

		String s = "";
		
		if(expr !=null) {
			s += expr.toString();
		}
		
		if(andExpr != null)
			s += andExpr.toString();


		for(String st: s.split("\n"))
			ret += "| " + st + "\n";


		return ret;
					
	}
}