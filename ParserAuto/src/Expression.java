public class Expression
{
	public ConditionalAndExpr andExpr;
	public Expression expr;
	
	
	public Expression()
	{}
	
	
	public Expression(ConditionalAndExpr t, Expression e) {
		this.andExpr = t;
		this.expr = e;
	}
	
	
	public String toString()
	{
		String ret = "";

		String s = "";
		
		if(this.expr !=null) {
			s += this.expr.toString();
		}
		
		if(this.andExpr != null)
			s += this.andExpr.toString();


		for(String st: s.split("\n"))
			ret += "| " + st + "\n";


		return ret;
					
	}
	
	public Boolean isBoolean(){
		Boolean result=true;
		if(andExpr!=null)
			result&=andExpr.isBoolean();
		
		if(expr!=null)
			result&=expr.isBoolean();
		
		return result ;
	}

	
//	public String check() throws SemanticException{
//		String expr1="";
//		String expr2="";
//
//
//		if(andExpr!=null)
//			expr1= andExpr.check();
//		
//		if(expr!=null)
//			expr2= expr.check();
//
//
//		if(expr1==null && expr2!=null)
//			return expr2;
//		
//		if(expr2==null && expr1!=null)
//			return expr1;
//		
//		if(expr1.equals(expr2))
//			return expr1;
//		else
//			return null;
//		
//		
//
//	}
}