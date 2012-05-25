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
	
	public void check() throws SemanticException{
		
		if(SymbolTable.getInstance().contains(varId)){
			
			throw new SemanticException(varId,SemanticException.DUPLICATE_VAR);
		} else {
			SymbolTable.getInstance().add(new Entry(varId,type));
			
		}
	}
}