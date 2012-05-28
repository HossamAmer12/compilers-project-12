public class LocalVarDecl
{
	String varId;
	Type type;
	int lineNo;
	int at;
	String line;
	
	public LocalVarDecl()
	{
		
	}
	
	public LocalVarDecl(String idLexeme, Type type,int lineNo,int at,String line)
	{
		this.varId = idLexeme;
		this.type = type;
		this.lineNo=lineNo;
		this.at=at;
		this.line=line;
	}
	
	public String toString()
	{
		return "LocalVarDecl " + this.varId;
	}
	
	public void check() throws SemanticException{
		
		if(SymbolTable.getInstance().contains(varId)){
			Report.semanticError(lineNo, at, String.format("Duplicate local variable %s.", varId), line);
		} else {
			SymbolTable.getInstance().add(new Entry(varId,type));
			
		}
	}
}