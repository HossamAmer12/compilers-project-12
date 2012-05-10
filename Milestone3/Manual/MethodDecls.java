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
		
		if(this!=null)
			SymbolTable.getInstance().add(new Entry("MethodsDecl\n"));
		
		for (MethodDecl m: this)
		{
			SymbolTable.getInstance().openScope();
			
			if(!SymbolTable.getInstance().contains(m.methodID))
			{
				SymbolTable.getInstance().add(new Entry(m.methodID));
				m.check();
			}
			else
				throw new SemanticException("Method identifier cannot be a duplicate.");
		}
		
	}
	
}