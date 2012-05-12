public class ConditionalAndExpr extends Expression
{
	public EqualityExpr equalityExpr;
	public ConditionalAndExpr expr;
	
	public ConditionalAndExpr()
	{}
	
	
	public ConditionalAndExpr(EqualityExpr t, ConditionalAndExpr e) {
		equalityExpr = t;
		expr = e;
	}
	
	public String toString()
	{
		String ret = "";

		String s = "";

		if(expr !=null) {
			s += expr.toString();
		}

		s += equalityExpr.toString();

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";


		return ret;

	}
	public Boolean isBoolean(){
		Boolean result=true;
		if(equalityExpr!=null)
			result&=equalityExpr.isBoolean();
		
		if(expr!=null)
			result&=expr.isBoolean();

		return result ;
	}
//	public String check() throws SemanticException{
//		String expr1="";
//		String expr2="";
//		if(equalityExpr!=null)
//			expr1=equalityExpr.check();
//		if(expr!=null)
//			expr2=expr.check();
//
//
//		if(expr1.equals(expr2) && (expr1.equals("boolean") || expr2.equals("boolean")))
//			return expr1;
//		else
//			return null;
//		
//
//	}
}