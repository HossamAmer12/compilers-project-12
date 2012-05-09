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
	
	
}