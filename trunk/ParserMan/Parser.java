/*
 * class Parser
 * 
 * Parses the tokens returned from the lexical analyzer.
 * 
 * Update the parser implementation to parse the Decaf
 * language according to the grammar provided. 
 */
public class Parser {

	private Lexer lexer; // lexical analyzer
	private Token token; // look ahead token
	
	public Parser(Lexer lex) {
		lexer = lex;
	}
	
	public boolean parse() {
		
		token = lexer.nextToken();
		
		boolean value;
		
		while(token.getTokenType() != Token.EOF) {
			value = ClassDecl();
			//value &= match(Token.SM);
			
			if(!value)
				return false;
		}
		
		return true;
	}
	private boolean ClassDecl()
	{
		boolean value=match("class");
		value&=match(Token.ID);
		value&=match(Token.LB);
		value&=MethodDecls();
		value&=match(Token.RB);

		// System.out.println(value + " > Class Decl"); // class{ case!
		return value;
	}
	
	private boolean MethodDecls()
	{
		//Handle Epsilon case
		if(token.getTokenType()==Token.RB)
			return true;
		
		boolean value=true;
		while(true)
		{
			if(token.getLexeme().equals("static"))
			{
				value&=MethodDecl();
			}else break;
		}
		return value;
	}
	private boolean MethodDecl()
	{
		boolean value=match("static");
		value&=Type();
		value&=match(Token.ID);
		value&=match(Token.LP);
		value&=FormalParams();
		value&=match(Token.RP);
		value&=Block();
		//TODO match block value&=Block();
		return value;
	}
	private boolean FormalParams(){
	
		//Handle Epsilon case
		if(token.getTokenType()==Token.RP)
			return true;
		
		return ProperFormalParams();
	
	}
	private boolean ProperFormalParams(){
		
		boolean value=FormalParam();
		value&=ProperFormalParamsPRIME();
		return value;
	}
	private boolean ProperFormalParamsPRIME(){
		
		//Handle Epsilon case
		if(token.getTokenType()==Token.RP)
			return true;
		
		boolean value=true;
		
		while(true)
		{
			if(token.getLexeme().equals(","))
			{
				value&=match(",");
				value&=FormalParam();
			}else break;
				
		}
		return value;
		}
		
	
	private boolean FormalParam(){
		
		boolean value=Type();
		value&=match(Token.ID);

		return value;
	}
	private boolean Type()
	{
		boolean value=false;
		
		if(token.getLexeme().equals("int") || token.getLexeme().equals("float")
			|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
		{
			value=match(token.getTokenType());
		}
		return value;
		
	}
	private boolean Block(){
		
		boolean value = match(Token.LB);
		
		System.out.println("Matching LB in block: " + value + ", " + token.getLexeme());		
		value&=Statements();
		
		 System.out.println("Matching Statements in block: " + value + ", " + token.getLexeme());		
		value&=match(Token.RB);
		
		System.out.println("Matching RB in Block: " + value + ", " + token.getLexeme());		
		return value;
		
	}
	
	/**
	* Checks for the statements grammar
	* @author Hossam_Amer
	**/
	
	public boolean Statements()
	{
		// System.out.println("Hello I am from statemetns");
		//Handle Epsilon case
		if(token.getTokenType()==Token.RB)
			return true;
			
			return StatementsPrime();

	}
	
	public boolean StatementsPrime()
	{
		boolean value = Statement();
		
		return value;
	}
	
	public boolean Statement()
	{
		
		boolean value = true;
	
		while(true)
		{
		
			if(token.getLexeme().equals("int") || token.getLexeme().equals("float")
				|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
					value &= LocalVarDecl();
				else if (token.getTokenType() == Token.ID)		
					value &= AssignStmt();
				else if(token.getLexeme().equals("if"))	
					value &= IfStmt();
				else if(token.getLexeme().equals("while"))
					value &= WhileStmt();
				else if (token.getLexeme().equals("return"))
					value &= ReturnStmt();
				else if(token.getTokenType() == Token.LB)
				 		value &= Block();
					else if (token.getTokenType() == Token.RB)
						break;
					else
						return false; // To avoid infinite loop when having a statement
	
		}		
				return value;
	}
	
	// TODO
	public boolean IfStmt()
	{
		
		System.out.println("\nHello From IF Statment! :D");
		boolean value = match("if");
		System.out.println("IfStmt: if: " + value + ", " + token.getLexeme());
		value &= match(Token.LP);
		System.out.println("IfStmt: LP: " + value + ", " + token.getLexeme());		
		value &= Expression();
		System.out.println("IfStmt: Expression: " + value + ", " + token.getLexeme());		
		// token = lexer.nextToken();// Expression not implemented

		value &= match(Token.RP);
		System.out.println("IfStmt: RP: " + value + ", " + token.getLexeme());		

		value &= Statement();
		System.out.println("IfStmt: Statement: " + value + ", " + token.getLexeme());				
		value &= IfStmtHelper();
		System.out.println("IfStmt: IfStmtHelper: " + value + ", " + token.getLexeme());		
				
		return value;
	}
	
	
	public boolean IfStmtHelper()
	{
		if(token.getLexeme().equals("else"))
		{
			boolean value = match("else");
			System.out.println("IfStmtHelper: else: " + value + ", " + token.getLexeme());
			
			value &= Statement();// XXX not sure for dangling else
			
			return value;
		}
		
		return true;

	}
	
	// TODO
	public boolean WhileStmt()
	{
		System.out.println("Hello from While! ");
		boolean value = match("while");
		System.out.println("I matched the while: " + value + ", " + token.getLexeme());
		value &= match(Token.LP);
		System.out.println("I matched the LP: " + value+ ", " + token.getLexeme());
		value &= Expression();
		System.out.println("I matched the Expression: " + value + ", " + token.getLexeme());
		value &= match(Token.RP);
		System.out.println("I matched the RP: " + value + ", " + token.getLexeme());
		value &= Statement();
		// token = lexer.nextToken();
		System.out.println("I matched the Statement: " + value + ", " + token.getLexeme());
		
		return value;
	}
	
	public boolean ReturnStmt()
	{
		boolean value = match("return");
		value &= Expression();
		
		return value;
	}
	
	public boolean LocalVarDecl()
	{
		boolean value = Type();
		
		System.out.println("LocalVarDecl(1): Type: " + value+ ", " + token.getLexeme());		
		value &= match(Token.ID);
		System.out.println("LocalVarDecl(2): ID: " + value+ ", " + token.getLexeme());		
		value &= match(Token.SM);
		System.out.println("LocalVarDecl(3): SM: " + value + ", " + token.getLexeme());		
		

		return value;
	}

	public boolean AssignStmt()
	{
		boolean value = match(Token.ID);
		
		System.out.println("AssignStmt: Matching ID: " + value+ ", " + token.getLexeme());		
		value &= match(Token.AO);
		System.out.println("AssignStmt: Matching AO: " + value + ", " + token.getLexeme());		
		value &= Expression();
		
		System.out.println("AssignStmt: Matching Expression: " + value + ", " + token.getLexeme());		
		token = lexer.nextToken(); // XXX Done since Expression is not done
		value &= match(Token.SM);
		System.out.println("AssignStmt: Matching SM: " + value + ", " + token.getLexeme());		
		
		return value;
	}
	
	// TODO
	public boolean Expression()
	{
		return true;
	}
		
	private boolean expr(){
		boolean value = term();

		while (true) {
			switch (token.getTokenType()) {
			case Token.MO:
				value &= match(token.getTokenType());
				value &= term();
				break;

			case Token.PO:
				value &= match(token.getTokenType());
				value &= term();
				break;

			default:
				return value;
			}
		}
	}

	private boolean term(){
		boolean value = match(Token.NM);

		while (true) {
			switch (token.getTokenType()) {
			
			case Token.DO:
				value &= match(token.getTokenType());
				value &= match(Token.NM);
				break;

			case Token.TO:
				value &= match(token.getTokenType());
				value &= match(Token.NM);
				break;

			default:
				return value;
			}
		}
	}

	// matches token against lexeme and advances to next token.
	private boolean match(String lexeme){
		if(token.getLexeme().equals(lexeme)){
			token=lexer.nextToken();
			return true;
		}else 
			return false;
	}
	// matches token against type and advances to next token.
	private boolean match(int t) {
		if (token.getTokenType() == t) {
			token = lexer.nextToken();
			return true;
		} else {
			return false;
		}
	}
}
