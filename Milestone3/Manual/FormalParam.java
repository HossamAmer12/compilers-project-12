public class FormalParam
{
	public Type type;
	public String idLexeme;
	public static final int ID = 1; // for id
	
	
	public FormalParam()
	{
	}
	
	public FormalParam(String idLexeme)
	{
		this.idLexeme = idLexeme;
	}
	
	public String toString()
	{
		return "Formal Param " + idLexeme;
	}
	
	
	
}