public class ConditionalAndExpr extends Expression
{
	public EqualityExpr equalityExpr;
	// public int op;
	public ConditionalAndExpr expr;
	
	public ConditionalAndExpr()
	{}
	
	// public Expression(String idLexeme, Expression expr)
	// {
	// 	this.varId = idLexeme;
	// 	this.expr = expr;
	// }
	
	public ConditionalAndExpr(EqualityExpr t, ConditionalAndExpr e) {
		equalityExpr = t;
		// op = o;
		expr = e;
	}
	
	public String toString()
	{
		String ret = "\n";

		String s = "";

		if(expr !=null) {
			s += expr.toString();
		}

		s += equalityExpr.toString();

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";


		return ret;

	}
}