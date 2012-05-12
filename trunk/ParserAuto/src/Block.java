public class Block
{
	public Statements statements;
	
	public Block()
	{
	
	}
	
	public Block(Statements statements)
	{
		this.statements = statements;

	}
	
	public String toString()
	{
		
		String ret;
		if(statements.size() != 0)
		 	ret = "Block\n";
		else
			ret = "Block";
		
		 String s = "";
		 
		if(statements.size() != 0)
		{
			s += statements.toString();
			
			for(String st: s.split("\n"))
			 	ret += "| " + st + "\n";
		}	
		
		// return ret;
		return ret;

	}

//	public void check() throws SemanticException {
//
//		if(statements!=null)
//			statements.check();
//		
//	}
	
}