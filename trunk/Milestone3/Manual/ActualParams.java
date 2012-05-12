import java.util.ArrayList;

public class ActualParams // extends ArrayList<ProperActualParams>
{
	
	public ProperActualParams actualParmas;
	
	public ActualParams()
	{
	}
	
	public ActualParams(ProperActualParams actualParmas)
	{
		this.actualParmas = actualParmas;
	}
	
		public String toString()
		{
			String ret = "\n";

			String s = "";

			if(actualParmas != null) 
				s += actualParmas.toString();

	//		System.out.println("Actual Parasms: " + actualParmas);

			for(String st: s.split("\n"))
				ret += "| " + st + "\n";


			return ret;	
		}
}