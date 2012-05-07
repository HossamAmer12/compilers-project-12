import java.util.ArrayList;


public class FormalParams extends ArrayList<FormalParam>
{
	
	// public ProperFormalParams properFormalParams;
	
	// public ArrayList<FormalParam> properFormalParams;
	
	public FormalParams()
	{
		super();
	}
	
	// public FormalParams(ArrayList<FormalParam> properFormalParams)
	// {
	// 	this.properFormalParams = properFormalParams;
	// }
	
	public String toString()
	{
		String ret = "FormalParams\n";
		
		String s = "";
		
		for(FormalParam x: this)
		{
			s += x.toString();
		}
		
		for(String st: s.split("\n"))
			ret += "| " + st + "\n";


			return ret;
	}
	
}