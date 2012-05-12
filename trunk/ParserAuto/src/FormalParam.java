public class FormalParam
{
	public Type type;
	public String idLexeme;
	public static final int ID = 1; // for id
	
	
	public FormalParam()
	{
	}
	
	public FormalParam(String idLexeme, Type type)
	{
		this.idLexeme = idLexeme;
		this.type = type;
	}
	
	public String toString()
	{
		return "Formal Param " + idLexeme + "\n";
	}
	
//	public void check() throws SemanticException{
//		SymbolTable.getInstance().add(new Entry(idLexeme,type));
//	}
	
	
}