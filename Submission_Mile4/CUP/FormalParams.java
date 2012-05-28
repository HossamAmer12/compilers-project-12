import java.util.ArrayList;


public class FormalParams extends ArrayList<FormalParam>
{
	
	
	public FormalParams()
	{
		super();
	}
	
	
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
	
	public void check() throws SemanticException{
		for(FormalParam f:this){
			f.check();
		}
	}
}