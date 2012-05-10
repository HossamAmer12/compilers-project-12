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

	public void check() throws SemanticException  {
		// TODO Auto-generated method stub
		
		if(this!=null)
			SymbolTable.getInstance().add(new Entry("Statements\n"));
		
		for (Statement stmt: this)
		{
			Entry en;
			
			if(stmt.getType().equals("LocalVarDecl"))
			{
				en = new Entry(stmt.localVarDecl.varId);
			}
			else
			{
				 en = new Entry(stmt.getType());
			}
			
//			if(stmt.getType() == "ReturnStmt")
//			{
//				//String returnVar = stmt.returnStmt.toString();
//				
//				/*if(SymbolTable.getInstance().contains(id))
//				{
//					
//				}*/
//			}
			if(stmt.getType().equals("IfStmt"))
			{
				SymbolTable.getInstance().openScope();
				SymbolTable.getInstance().add(en);
				stmt.check();
				
			}
			
			else if(stmt.getType().equals("WhileStmt"))
			{
				SymbolTable.getInstance().openScope();
				SymbolTable.getInstance().add(en);
				stmt.check();
			}			
			else if(stmt.getType()=="AssignStmt" && SymbolTable.getInstance().contains(stmt.assignStmt.varId))
			{
				SymbolTable.getInstance().add(en);
				stmt.check();
			}
			else if(stmt.getType() != "AssignStmt")
			{
				SymbolTable.getInstance().add(en);
				stmt.check();
			}
			
			else
				throw new SemanticException("Statements are not valid");
			
			
			System.out.println(SymbolTable.getInstance().toString());
		}
		
	}
}