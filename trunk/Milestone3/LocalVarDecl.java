public class LocalVarDecl
{
	String varId;
	
	public LocalVarDecl()
	{
		
	}
	
	public LocalVarDecl(String idLexeme)
	{
		this.varId = idLexeme;
	}
	
	public String toString()
	{
		return "LocalVarDecl " + this.varId;
	}
}