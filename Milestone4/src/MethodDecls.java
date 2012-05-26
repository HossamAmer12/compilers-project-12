import java.util.ArrayList;

public class MethodDecls extends ArrayList<MethodDecl>
{
	public int lineNo;
	public String line;
	public MethodDecls(int lineNo,String line)
	{

		super();
		this.lineNo=lineNo;
		this.line=line;
	}
	
	public String toString()
	{
			String ret = "MethodsDecl\n";

			String s = "";

			for(MethodDecl x: this)
			{

				s += x.toString() + "\n";
			}

			for(String st: s.split("\n"))
				ret += "| " + st + "\n";

			return ret;		

	}
	public void check() throws SemanticException {
	
		for (MethodDecl m: this)
		{
			if(!SymbolTable.getInstance().contains(m.methodID))
			{
				SymbolTable.getInstance().add(new Entry(m.methodID,m.type,true,m.formalParams));
			}
			else{
				ClassDecl.error=true;
				Report.semanticError(lineNo, line.toString().length(), "Duplicate method declaration "+m.methodID, line.toString());
			}
		}
		
		for(MethodDecl m:this){
			m.check();
		}
		
	}
	
}