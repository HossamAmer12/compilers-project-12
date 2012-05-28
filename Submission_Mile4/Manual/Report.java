
public class Report {

	public static void syntaxErrorToken(int line,int at,String expected,String found,String in)throws SyntaxException{
		throw new SyntaxException("Syntax Error at line "+line+" char "+at+":\nExpected '"+expected+"'\nFound '"+found+"'\nIn: "+in.trim()+"\n");
	}
	
	public static void syntaxErrorGrammer(int line,int at,String message,String in) throws SyntaxException{
		throw new SyntaxException("Syntax Error at line "+line+" char "+at+":\n"+message+"\n"+in.trim()+"\n");
	}
	
	public static void semanticError(int line,int at,String message,String in) throws SemanticException{
		throw new SemanticException("Semantic Error at line "+line+" char "+at+"\n"+message+"\nIn: "+in.trim()+"\n");
	}
	public static void displayWarning(int line,int at,String message,String in){
			System.out.println("Warning at line "+line+" char "+at+":\n"+message+"\nIn: "+in.trim()+"\n");
			}
}
