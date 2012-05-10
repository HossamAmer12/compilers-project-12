public class LocalVarDecl
{
	String varId;
	Type type;
	
	public LocalVarDecl()
	{
		
	}
	
	public LocalVarDecl(String idLexeme, Type type)
	{
		this.varId = idLexeme;
		this.type = type;
	}
	
	public String toString()
	{
		return "LocalVarDecl " + this.varId;
	}
}