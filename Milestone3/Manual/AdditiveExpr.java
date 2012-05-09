public class AdditiveExpr extends EqualityExpr
{
	
	public MultiplicativeExpr multiplyExpr;
	public int op;
	public AdditiveExpr expr;
	
	
	public static final int PO = 1;// +
	public static final int MO = 2;// -
		
	
	public AdditiveExpr()
	{}
	
	// public Expression(String idLexeme, Expression expr)
	// {
	// 	this.varId = idLexeme;
	// 	this.expr = expr;
	// }
	
	
	public AdditiveExpr(MultiplicativeExpr t, int o, AdditiveExpr e) {
		multiplyExpr = t;
		op = o;
		expr = e;
	}
	
	
	
	public String toString()
	{
		String ret = "\nAdditiveExpr";

		String s = "";

		if(expr !=null) {
			s += expr.toString();
			
			if(op == PO)
				s += "+\n";
			else
				s += "-\n";
			
		}

		s += multiplyExpr.toString();

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";

		return ret;

	}
}