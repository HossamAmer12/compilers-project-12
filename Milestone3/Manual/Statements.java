import java.util.ArrayList;

public class Statements extends ArrayList<Statement>
{
	
	public Statements()
	{
		// super();
	}
	
	public String toString()
	{
		
		String ret = "";

		String s = "";

		for(Statement x: this)
		{

			s += x.toString() + "\n";
		}

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";

		return ret;		

	}
}