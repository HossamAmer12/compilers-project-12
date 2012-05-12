
import java.util.ArrayList;

public class ProperActualParams extends ArrayList<Expression>
{
	public ProperActualParams()
	{
		super();
	}
	
	public String toString()
	{
		String ret = "ProperActualParams\n";

		String s = "";

		for(Expression x: this)
		{

			s += x.toString() + "\n";
		}

		for(String st: s.split("\n"))
			ret += "| " + st + "\n";

		return ret;		

	}
}