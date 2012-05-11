import java.util.ArrayList;

public class MethodDecls extends ArrayList<MethodDecl>
{
	
	public MethodDecls()
	{
		super();
		// this();
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
			else
				throw new SemanticException(m.methodID,SemanticException.DUPLICATE_METHOD);
			
		}
		
		for(MethodDecl m:this){
			m.check();
		}
		
	}
	
}